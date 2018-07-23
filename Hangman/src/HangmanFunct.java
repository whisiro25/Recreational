import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class HangmanFunct {
	
	public static boolean checkValid(String guess){
		if(guess.matches("^.*[^a-zA-Z0-9 ].*$")){
			System.out.println("This is not valid input. The guess must be alphanumeric.");
			return false;
		}if(guess.length() >1){
			System.out.println("This is not valid input. There are too many characters.");
			return false;
		}return true;
	}
	
	public static boolean checkNotUsed(String guess, String guessedLets){
		if(guessedLets.contains(guess)){
			System.out.println("You already guessed " + guess + ".");
			guessedLets = guessedLets.replace("", " ").trim();
			System.out.println("Guessed Letters: " + guessedLets);
			return false;
		}return true;
	}
	
	public static String updateKnown(String word,String str,String let){
	    String ret = "";
	    for(int i=0;i<str.length();i++){
	        if(word.charAt(i) == let.charAt(0)){ret += let;}
	        else{ret += str.charAt(i);}
	    }return ret;
	}
	
	public static void main(String[] args) throws IOException{
		Scanner scanner = new Scanner(System.in);
		int guesses = 8;
		Random rand = new Random();
		int lineNum = rand.nextInt(1529);
		String word = Files.readAllLines(Paths.get("words2.txt")).get(lineNum).toUpperCase();
		String known = "";
		String guessedLets = "";
		for(char let: word.toCharArray()){
			known += "-";
		}
		while(guesses > 0){
			if(word.equals(known)){
				System.out.println("You guessed the word: " + word + ". \nYou win!");
				return;
			}System.out.println("The word now looks like: " + known);
			if(guesses > 1){System.out.println("You have " + guesses + " guesses left.");}
			else{System.out.println("You have " + guesses + " guess left.");}
			System.out.println("Enter your next guess:");
			String guess = scanner.nextLine().toUpperCase();
			while(!checkValid(guess) || !checkNotUsed(guess,guessedLets)){
				System.out.println("Please guess again:");
				guess = scanner.nextLine().toUpperCase();
			}guessedLets += guess;
			if(word.contains(guess)){
				System.out.println("That guess is correct.");
				known = updateKnown(word,known,guess);
			}else{
				System.out.println("There are no " + guess + "'s in the word.");
				guesses--;
			}
		}System.out.println("You're out of guesses! You Lost!\nThe word was: " + word);	
	}
}
