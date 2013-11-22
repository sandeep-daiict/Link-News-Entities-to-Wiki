package mes.ron.linking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class EntityLinking 
{
	public static long count ;
	public static void main(String args[])
	{
		count=0;
		try 
		{
			BufferedReader brwiki = new BufferedReader(new FileReader(new File("wikiEntity")));
			BufferedReader brnews = new BufferedReader(new FileReader(new File("newsentity")));
			BufferedWriter brw = new BufferedWriter(new FileWriter(new File("entityLinkage.txt")));
			HashMap<String,String> map = new HashMap<String,String>();
			String line = new String();
			while((line=brwiki.readLine())!=null)
			{
				System.out.println(line);
				map.put(line,line.replace(" ","_"));
			}
			while((line=brnews.readLine())!=null)
			{try{
				String tokens[] = line.split("\\|");
				System.out.println(line);
				
				
				String filename = new String();
				if(tokens.length>0)
					filename=tokens[0];
				else
					continue;
				System.out.println(filename);
				String title=new String();
				if(tokens.length>1)
					title = tokens[1];
				else
					title="NOTITLE";
				System.out.println(title);
				
				String entity=new String();
				if(tokens.length>2)
					entity = tokens[2];
				else
					entity="NOENTITY";
				
				String entities[] = entity.split(" ");
				String url[] = new String[entities.length];
				for(int k=0;k<entities.length;k++)
				{
					if(map.containsKey(entities[k]))
					{
						count++;
						url[k] ="http://en.wikipedia.org/wiki/"+ map.get(entities[k]);
					}
					else
						url[k]="NOMATCH";
				}
				String timestamp=new String();
				if(tokens.length>3)
					timestamp = tokens[3];
				else
					timestamp="NOtimestamp";
				String location=new String();
				if(tokens.length>4)
					location = tokens[4];
				else
					location="NOlocation";
				String agency=new String();
				if(tokens.length>5)
					agency = tokens[5];
				else
					agency="NOagency";
				String link = tokens[6];
				if(tokens.length>6)
					link = tokens[6];
				else
					link="NOlink";
				;
				brw.write(filename+"\t"+title+"\t");
				for(int k=0;k<entities.length;k++)
				{
					if(k!=entities.length-1)
						brw.write(entities[k]+" ");
					else
						brw.write(entities[k]+"\t");
				}
				if(entities.length==0)
					brw.write("\t");
				for(int k=0;k<url.length;k++)
				{
					if(k!=url.length-1)
						brw.write(url[k]+" ");
					else
						brw.write(url[k]+"\t");
				}
				if(url.length==0)
					brw.write("\t");
				brw.write(timestamp+"\t"+location+"\t"+agency+"\t"+link+"\n");
				brw.flush();
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				continue;
			}
			}
			System.out.println("MATCH COUNT: "+count);
			brw.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
