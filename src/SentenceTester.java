import java.util.ArrayList;

public class SentenceTester {
	public static void main(String[] args) {
      final int numSentences = 10;
   
      ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
      for(int i = 0; i < numSentences; i++){
          double ran = Math.random();
          if(ran < 0.333)
              sentenceList.add(new Sentence());
          else if(ran > 0.666)
              sentenceList.add(new CompoundSentence());
          else
              sentenceList.add(new CompoundComplexSentence());
      }
      
      for(Sentence s : sentenceList){
          s.generateSentence();
          System.out.println(s + " " + s.totalSentiment(s.toString()));
      }
   }
}