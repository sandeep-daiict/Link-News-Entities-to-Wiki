package mes.ron.wikiparsing;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;


public class ReadXMLFile {

	public String titlename;
	long count=0;
	int num=0;
	public BufferedWriter bw = null;
	String serializedClassifier = "F:\\sandeep\\stanford-ner-2013-06-20\\classifiers\\english.all.3class.distsim.crf.ser.gz";
	AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	public  void read(String file) {
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() 
			{

				boolean title = false;				
				public void startElement(String uri, String localName,String qName, Attributes attributes)throws SAXException 
				{			

					if (qName.equalsIgnoreCase("title")) 
					{
						title = true;
					}

				}
				public void characters(char ch[], int start, int length)throws SAXException 
				{

					if (title) 
					{						
						titlename=new String(ch, start, length);						
						
					}				

				}
				@Override
			   	public void endElement(String s, String s1, String element) throws SAXException 
			    	{
			        
			        	if (element.equals("title")) 
			        	{ 	
			        		title = false;	
			        		if(!titlename.equals(""))
			        			extractEntities(titlename);	
			        		titlename="";
			        	}
			        	
			        	if (element.equals("page")) 
			        	{
			        	        		
			        		
			        		//doc++;
			        		//System.out.println(doc);
			        		
			        		count++;
			        		System.out.println(count);
			        	}
			    	}
				private void extractEntities(String content) 
				{
					try {
						System.out.println("Titlename:"+content);
						if(EntityMining.tag(content, serializedClassifier,classifier))
						{
							bw.write(content+"\n");
							bw.flush();
						}
					} catch (IOException e) {
						System.out.println("error in "+content+"number "+count);
						//e.printStackTrace();
					}
				}
			    
				
				
				
			};

			saxParser.parse(file, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void writetofile(int num) 
	{
		
	        
		num++;
		
	}
	public static void main(String args[])
	{
		
		ReadXMLFile r = new ReadXMLFile();
		try {
			r.bw=new BufferedWriter(new FileWriter(new File("wikiEntity")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.read("G:\\dataset\\enwiki-latest-pages-articles.xml");
	}

}