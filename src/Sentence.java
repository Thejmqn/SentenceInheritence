import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

public class Sentence {

    private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
    private static ArrayList<String> posAdjectives = new ArrayList<String>();
    private static ArrayList<String> negAdjectives = new ArrayList<String>();
    private static ArrayList<String> verbs = new ArrayList<String>();
    private static ArrayList<String> names = new ArrayList<String>();
    private static ArrayList<String> adverbs = new ArrayList<String>();
    
    private String sentence;
    private String noun;
    private String verb;
    private String adjective;
    private String adverb;

    public Sentence(String n, String v, String a) {
    	 try {
             Scanner input = new Scanner(new File("cleanSentiment.csv"));
             while (input.hasNextLine()) {
                 String[] temp = input.nextLine().split(",");
                 sentiment.put(temp[0], Double.parseDouble(temp[1]));
             }
             input.close();
         } catch (Exception e) {
             System.out.println("Error reading or parsing cleanSentiment.csv");
         }

         // read in the positive adjectives in postiveAdjectives.txt
         try {
             Scanner input = new Scanner(new File("positiveAdjectives.txt"));
             while (input.hasNextLine()) {
                 posAdjectives.add(input.nextLine().trim());
             }
             input.close();
         } catch (Exception e) {
             System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
         }
         // read in the negative adjectives in negativeAdjectives.txt
         try {
             Scanner input = new Scanner(new File("negativeAdjectives.txt"));
             while (input.hasNextLine()) {
                 negAdjectives.add(input.nextLine().trim());
             }
             input.close();
         } catch (Exception e) {
             System.out.println("Error reading or parsing negativeAdjectives.txt");
         }
         // read in the positive adjectives in verbs.txt
         try {
             Scanner input = new Scanner(new File("verbs.txt"));
             while (input.hasNextLine()) {
                 verbs.add(input.nextLine().trim());
             }
             input.close();
         } catch (Exception e) {
             System.out.println("Error reading or parsing verbs.txt\n" + e);
         }
         // read in the negative adjectives in names.txt
         try {
             Scanner input = new Scanner(new File("names.txt"));
             while (input.hasNextLine()) {
                 names.add(input.nextLine().trim());
             }
             input.close();
         } catch (Exception e) {
             System.out.println("Error reading or parsing names.txt");
         }
         // read in the negative adjectives in adverbs.txt
         try {
             Scanner input = new Scanner(new File("adverbs.txt"));
             while (input.hasNextLine()) {
            	 adverbs.add(input.nextLine().trim());
             }
             input.close();
         } catch (Exception e) {
             System.out.println("Error reading or parsing adverbs.txt");
         }
         
         sentence = "";
         noun = n.isBlank() ? randomName() : n;
         verb = v.isBlank() ? randomVerb() : v;
         adverb = a.isBlank() ? randomAdverb() : a;
         adjective = randomAdjective();
    }
    
    public Sentence() {
        this("", "", "");
    }
        
    public String getSentence() {return sentence;}
    public String getNoun() {return noun;}
    public String getVerb() {return verb;}
    public String getAdjective() {return adjective;}
    public String getAdverb() {return adverb;}
    
    public void setSentence(String s) {sentence = s;}
    public void setNoun(String n) {noun = n;}
    public void setVerb(String v) {verb = v;}
    public void setAdjective(String a) {adjective = a;}
    public void setAdverb(String a) {adverb = a;}
    
    public void clear() {
    	sentence = "";
    	noun = "";
    	verb = "";
    	adjective = "";
    	adverb = "";
    }
    
    public double sentimentVal(String word) {
    	try {
            return sentiment.get(word.toLowerCase());
        } catch (Exception e) {
            return 0;
        }
    }

    public double totalSentiment(String review) {
        int space = review.indexOf(" ");
        double sentiment = 0.0;
        while (space >= 0) {
            sentiment += sentimentVal(review.substring(0, space));
            review = review.substring(space + 1);
            space = review.indexOf(" ");
        }
        return sentiment;
    }

    public String randomVerb() {
        return verbs.get((int) (Math.random() * verbs.size()));
    }

    public String randomName() {
        return names.get((int) (Math.random() * names.size()));
    }

    public String randomAdjective() {
    	return Math.random() < 0.5 ? posAdjectives.get((int) (Math.random() * posAdjectives.size())) : negAdjectives.get((int) (Math.random() * negAdjectives.size()));
    }
    
    public String randomAdverb() {
    	return adverbs.get((int) (Math.random() * adverbs.size()));
    }

    public String sentimentAdjective(double sentiment, double range) {
        String random = "";
        do {
            random = randomAdjective();
        } while (sentimentVal(random) > sentiment + range || sentimentVal(random) < sentiment - range);
        return random;
    }
    
    public boolean isVowel(String s) {
    	if(s.length() > 1)
    		return false;
    	return "AEIOUaeiou".indexOf(s) != -1;
    }
    
    public String addS(String verb) {
    	String lastLetter = verb.substring(verb.length()-1);
    	String lastTwoLetters = verb.substring(verb.length()-2);
    	if(lastLetter.equals("o") || lastLetter.equals("s") || lastLetter.equals("z") || lastLetter.equals("x") || lastTwoLetters.equals("sh") || lastTwoLetters.equals("ch"))
    		return verb + "es";
    	else if(lastLetter.equals("y") && !isVowel(verb.substring(verb.length()-2, verb.length()-1)))
    		return verb.substring(0, verb.length()-1) + "ies";
    	else
    		return verb + "s";
    }
    
    public String generateSentence() {
        sentence = "";
        sentence += noun + " ";
        if(!noun.equals("I") && !noun.equals("You"))
        	sentence += addS(verb);
        else
        	sentence += verb;
        sentence += " " + adverb + ".";
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
    	if(!(other instanceof Sentence))
    		return false;
    	Sentence that = (Sentence) other;
    	if(that.getSentence().equals(this.getSentence()))
    		return true;
    	return false;
    }
} 