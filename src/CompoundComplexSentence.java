import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class CompoundComplexSentence extends CompoundSentence{

   private static ArrayList<String> connectingConjunctions = new ArrayList<String>();
   
   private String sentence;
   private String conjunction2;
   private String verb3;
   private String adverb3;
   
   public CompoundComplexSentence(String n, String v1, String a1, String c, String v2, String a2, String c2, String v3, String a3) {
       super(n, v1, a1, c, v2, a2);
       try {
           Scanner input = new Scanner(new File("connectingConjunctions.txt"));
           while (input.hasNextLine()) {
               connectingConjunctions.add(input.nextLine().trim());
           }
       input.close();
       } catch (Exception e) {
           System.out.println("Error reading or parsing connectingConjunctions.txt\n" + e);
       }
       conjunction2 = c2.isBlank() ? randomConnectingConjunction() : c2;
       verb3 = v3.isBlank() ? randomVerb() : v3;
       adverb3 = a3.isBlank() ? randomAdverb() : a3;
   }
   
    public CompoundComplexSentence(){
        this("", "", "", "", "", "", "", "", "");
    }
   
   public String getSentence() {return sentence;}
   public String getVerb3() {return verb3;}
   public String getAdverb3() {return adverb3;}
   public String getConjunction2() {return conjunction2;}
	
   public void setVerb3(String v3) {verb3 = v3;}
   public void setAdverb3(String a3) {adverb3 = a3;}
   public void setConjunction2(String c2) {conjunction2 = c2;}
   
   public void clear() {
	    super.clear();
	    verb3 = "";
	    adverb3 = "";
	    conjunction2 = "";
	}

	public String randomConnectingConjunction() {
	    return connectingConjunctions.get((int) (Math.random() * connectingConjunctions.size()));
	}

	public String addIng(String verb) {
	    String lastLetter = verb.substring(verb.length()-1);
	    String lastTwoLetters = verb.substring(verb.length()-2);
	    if(lastLetter.equals("x") || lastLetter.equals("y") || lastLetter.equals("w"))
	        return verb + "ing";
	    else if(lastTwoLetters.equals("ie"))
	        return verb.substring(0, verb.length()-2) + "ying";
	    else if(lastLetter.equals("e"))
	        return verb.substring(0, verb.length()-1) + "ing";
	    else if(verb.length() <= 4)
	        return verb + verb.substring(verb.length()-1) + "ing";
	    else
	        return verb + "ing";
	}

	public String frontComplexSentence() {
	    String front = "";
	    front += conjunction2.substring(0, 1).toUpperCase() + conjunction2.substring(1, conjunction2.length()) + " ";
	    front += addIng(verb3) + " ";
	    front += adverb3 + ", ";
	    return front;
	}

	public String backComplexSentence() {
	    String back = "";
	    back += ", " + conjunction2 + " ";
	    back += addIng(verb3) + " ";
	    back += adverb3;
	    return back;
	}

	public String generateSentence() {
	    sentence = super.generateSentence();
	    sentence = sentence.substring(0, sentence.length()-1);
	    sentence = Math.random() < 0.5 ? frontComplexSentence() + sentence : sentence + backComplexSentence();
	    sentence += ".";
	    return sentence;
	}

	public String toString() {
	    if(sentence == "" || sentence == null)
	        sentence = generateSentence();
	    return sentence;
	}

	public boolean equals(Object other) {
	    if(other == this)
	        return true;
	    if(!(other instanceof CompoundComplexSentence))
	        return false;
	    Sentence that = (CompoundComplexSentence) other;
	    if(that.getSentence().equals(this.getSentence()))
	        return true;
	    return false;
	}
}