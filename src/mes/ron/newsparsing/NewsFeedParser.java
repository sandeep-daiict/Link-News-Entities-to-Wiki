package mes.ron.newsparsing;

import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedItem;
import it.sauronsoftware.feed4j.FeedException;
import it.sauronsoftware.feed4j.FeedParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import mes.ron.wikiparsing.EntityMining;
import mes.ron.wikiparsing.Result;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class NewsFeedParser 
{	public static long count;
	public static String serializedClassifier = "F:\\sandeep\\stanford-ner-2013-06-20\\classifiers\\english.all.3class.distsim.crf.ser.gz";
	public static AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
	public static void main(String []args)
	{
		count=0;
		File data = new File("F:\\sandeep\\assignment6\\news");
		
		File folder[] = data.listFiles();
		File f = new File("newsentity");
		FileWriter fw = null;
		BufferedWriter bw = null;
		try 
		{
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		for(File fi: folder)
		
		{
			if(fi.isDirectory())
			
			{
				File files[] = fi.listFiles();
				
				for(File fii : files)
				{
					parseFeedFile(fii,bw);
				}
			}
		}
		
		System.out.println("Total Entity:"+count);
		
		
		
		
		
	}
	
	
	private static void parseFeedFile(File f, BufferedWriter bw)
	{
		try 
		{
			
			URL url = f.toURI().toURL();			
			Feed feedParser = FeedParser.parse(url);
				
			int l = feedParser.getItemCount();
			
			String agency = feedParser.getHeader().getTitle();
			if(agency.isEmpty())
			{
				agency="NOAGENCY";
			}
			Date ts = feedParser.getHeader().getPubDate();
			
			String timestamp = "";
			if(ts != null)
				timestamp = ts.toString();
			else
				timestamp="NOTIMESTAMP";
			for(int i = 0 ; i < l; i++)
			{
				FeedItem item = feedParser.getItem(i);
				String title = item.getTitle();
				ArrayList<mes.ron.newsparsing.Result> arr = new ArrayList<mes.ron.newsparsing.Result>();
				try 
				{
					if(!title.isEmpty())
						arr  = EntityNewsMining.tag(title, serializedClassifier,classifier);
					String filename = f.getName();
					String link = item.getLink().toString();
					if(link.isEmpty())
					{
						link="NOLINK";
					}
					bw.write(filename+"|"+title+"|");
					System.out.println(arr.size());
					String location = "NOLOCATION";
					boolean flag=false;
					if(arr.size()==0)
					{
						bw.write("NOENTITY|");
					}
					for(int k=0;k<arr.size();k++)
					{
						count++;
						if(k!=arr.size()-1)
							bw.write(arr.get(k).word+" ");
						else
							bw.write(arr.get(k).word+" ");
						if(!arr.get(k).type.equals("NOLOCATION"))
						{
							if(!flag)
								location="";
							flag=true;
							location+=arr.get(k).word+" ";
							
						}
					}
					 
					bw.write("|"+timestamp+"|"+location+"|"+agency+"|"+link+"\n");
					
				} 
				catch (IOException e) 
				{
					System.out.println("error in title:"+title);
					e.printStackTrace();
				}				
				

			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (FeedException e) 
		{
			e.printStackTrace();
		} 
		catch (NullPointerException e) 
		{
			e.printStackTrace();
			System.out.println("error in"+f);
		}
		
	}
}
