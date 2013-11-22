package mes.ron.wikiparsing;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;

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

public class EntityMining {

    public static boolean tag(String str,String classifierpath, AbstractSequenceClassifier<CoreLabel> classifier) throws IOException 
    {
    	      
      	
      	boolean result = false;
        List<List<CoreLabel>> out = classifier.classify(str);
        for (List<CoreLabel> sentence : out) 
        {
          for (CoreLabel word : sentence) 
          {
        	 if(word.get(CoreAnnotations.AnswerAnnotation.class).equals("ORGANIZATION") || word.get(CoreAnnotations.AnswerAnnotation.class).equals("PERSON")||word.get(CoreAnnotations.AnswerAnnotation.class).equals("LOCATION"))
        	 {
        		 
        		 //System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
        		 result = true;
        	 }
          }
          
        }
        
        return result;
    }
    

}