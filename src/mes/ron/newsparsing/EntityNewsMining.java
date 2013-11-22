package mes.ron.newsparsing;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;



/** This is a demo of calling CRFClassifier programmatically.
 *  <p>
 *  Usage: <code> java -mx400m -cp "stanford-ner.jar:." NERDemo [serializedClassifier [fileName]]</code>
 *  <p>
 *  If arguments aren't specified, they default to
 *  ner-eng-ie.crf-3-all2006.ser.gz and some hardcoded sample text.
 *  <p>
 *  To use CRFClassifier from the command line:
 *  java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier
 *      [classifier] -textFile [file]
 *  Or if the file is already tokenized and one word per line, perhaps in
 *  a tab-separated value format with extra columns for part-of-speech tag,
 *  etc., use the version below (note the 's' instead of the 'x'):
 *  java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier
 *      [classifier] -testFile [file]
 *
 *  @author Jenny Finkel
 *  @author Christopher Manning
 */

public class EntityNewsMining {

    public static ArrayList<Result> tag(String str,String classifierpath, AbstractSequenceClassifier<CoreLabel> classifier) throws IOException 
    {
    	      
      	
      	
        List<List<CoreLabel>> out = classifier.classify(str);
        ArrayList<Result> result = new ArrayList<Result>();
        for (List<CoreLabel> sentence : out) 
        {
          for (CoreLabel word : sentence) 
          {
        	 if(word.get(CoreAnnotations.AnswerAnnotation.class).equals("ORGANIZATION") || word.get(CoreAnnotations.AnswerAnnotation.class).equals("PERSON")||word.get(CoreAnnotations.AnswerAnnotation.class).equals("LOCATION"))
        	 {
        		 Result r = new Result();
        		 if(word.get(CoreAnnotations.AnswerAnnotation.class).equals("LOCATION"))
        			 r.type="location";
        		 else
        			 r.type="NOLOCATION";
        		 r.word=word.word();
        		 System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
        		 result.add(r);
        	 }
          }
          
        }
        
        return result;
    }
    

}