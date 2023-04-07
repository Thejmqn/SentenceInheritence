import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class CompoundSentence extends Sentence{
	
   private static ArrayList<String> conjunctions = new ArrayList<String>();
   
	private String sentence;
	private String verb2;
	private String adverb2;
	private String conjunction;
	
	public CompoundSentence(String n, String v1, String a1, String c, String v2, String a2) {
		super(n, v1, a1);
        try {
            Scanner input = new Scanner(new File("conjunctions.txt"));
            while (input.hasNextLine()) {
                conjunctions.add(input.nextLine().trim());
            }
        input.close();
        } catch (Exception e) {
            System.out.println("Error reading or parsing conjunctions.txt\n" + e);
        }
		  verb2 = v2.isBlank() ? randomVerb() : v2;
		  adverb2 = a2.isBlank() ? randomAdverb() : a2;
		  conjunction = c.isBlank() ? randomConjunction() : c;
	}
	
	public CompoundSentence() {
		this("", "", "", "", "", "");
	}
	
	public String getSentence() {return sentence;}
	public String getVerb2() {return verb2;}
	public String getAdverb2() {return adverb2;}
    public String getConjunction() {return conjunction;}
	
	public void setVerb2(String v2) {verb2 = v2;}
	public void setAdverb2(String a2) {adverb2 = a2;}
    public void setConjunction(String c) {conjunction = c;}
	
    public void clear() {
        super.clear();
        verb2 = "";
        adverb2 = "";
        conjunction = "";
    }
   
    public String randomConjunction() {
        return conjunctions.get((int) (Math.random() * conjunctions.size()));
    }
   
	public String generateSentence() {
		sentence = super.generateSentence();
		sentence = sentence.substring(0, sentence.length()-1);
        sentence += " " + conjunction + " ";
        if(!getNoun().equals("I") && !getNoun().equals("You"))
        	sentence += addS(verb2);
        else
        	sentence += verb2;
        sentence += " " + adverb2 + ".";
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
    	if(!(other instanceof CompoundSentence))
    		return false;
    	Sentence that = (CompoundSentence) other;
    	if(that.getSentence().equals(this.getSentence()))
    		return true;
    	return false;
	}
}