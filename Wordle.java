
import java.util.Dictionary;

public class Wordle {

    // Reads all words from dictionary filename into a String array.
    public static String[] readDictionary(String filename) {

        In in = new In(filename);
   

        int counter = 0;


        while (in.isEmpty() == false){
            in.readString();
            counter++;

        }

        String[] arr = new String[counter];
        In in2 = new In(filename);
        
        int counter2 =0;

        while (in2.isEmpty() == false){
            arr[counter2] = in2.readString();
            counter2++;


            
        }

    
 


        return arr;
    }

    // Choose a random secret word from the dictionary. 
    // Hint: Pick a random index between 0 and dict.length (not including) using Math.random()
    public static String chooseSecretWord(String[] dict) {



        double ranNum = Math.random()*dict.length;  // generates random number 

        String secretWord = dict[(int)ranNum];

        return secretWord;
    }


    // Simple helper: check if letter c appears anywhere in secret (true), otherwise
    // return false.
    public static boolean containsChar(String secret, char c) {
		
        boolean isCharIn = false;
        
        
        for (int i = 0; i < 5; i++) {
            if (secret.charAt(i) == c){
                isCharIn = true;
                break;
  
            }
            else isCharIn = false;
            
        }


        return isCharIn;
    }

    // Compute feedback for a single guess into resultRow.
    // G for exact match, Y if letter appears anywhere else, _ otherwise.
    public static void computeFeedback(String secret, String guess, char[] resultRow) {
		
        


        for (int i = 0; i < 5; i++) {
            if (containsChar(secret, guess.charAt(i)) && secret.charAt(i) == guess.charAt(i)){
                resultRow[i] = 'G';
                System.out.print(guess.charAt(i));
            
            }
            else if (containsChar(secret, guess.charAt(i))){
                resultRow[i] = 'Y';
                System.out.print(guess.charAt(i));

            }
            else{

                resultRow[i] = '_';
                System.out.print(guess.charAt(i));
            }
        }


        System.out.println();
		
    }

    // Store guess string (chars) into the given row of guesses 2D array.
    // For example, of guess is HELLO, and row is 2, then after this function 
    // guesses should look like:
    // guesses[2][0] // 'H'
	// guesses[2][1] // 'E'
	// guesses[2][2] // 'L'
	// guesses[2][3] // 'L'
	// guesses[2][4] // 'O'
    public static void storeGuess(String guess, char[][] guesses, int row) {
		
        for (int i = 0; i < 5; i++){
            guesses[row][i] = guess.charAt(i);
        }
    }

    // Prints the game board up to currentRow (inclusive).
    public static void printBoard(char[][] guesses, char[][] results, int currentRow) {
        System.out.println("Current board:");
        for (int row = 0; row <= currentRow; row++) {
            System.out.print("Guess " + (row + 1) + ": ");
            for (int col = 0; col < guesses[row].length; col++) {
                System.out.print(guesses[row][col]);
            }
            System.out.print("   Result: ");
            for (int col = 0; col < results[row].length; col++) {
                System.out.print(results[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns true if all entries in resultRow are 'G'.
    public static boolean isAllGreen(char[] resultRow) {


        boolean isGreen = false;
        for (int i = 0; i < 5; i++) {
            if (resultRow[i] == 'G'){
                isGreen = true;
            }
            else{
                isGreen = false;
                break;
            } 

        }
        
        return isGreen;
    }

    public static void main(String[] args) {

        int WORD_LENGTH = 5;
        int MAX_ATTEMPTS = 6;
        
        // Read dictionary
        String[] dict = readDictionary("dictionary.txt");

        // Choose secret word
        String secret = chooseSecretWord(dict);

        // Prepare 2D arrays for guesses and results
        char[][] guesses = new char[MAX_ATTEMPTS][WORD_LENGTH];
        char[][] results = new char[MAX_ATTEMPTS][WORD_LENGTH];

        // Prepare to read from the standart input 
        In inp = new In("dictionary.txt");

        int attempt = 0;
        boolean won = false;

        while (attempt < MAX_ATTEMPTS && !won) {

            String guess = "";
            guess = guess.toUpperCase();
            boolean valid = false;

            // Loop until you read a valid guess
            while (!valid) {
                System.out.print("Enter your guess (5-letter word): ");
                guess = chooseSecretWord(dict);
                
                if (guess.length() != 5) {
                    System.out.println("Invalid word. Please try again.");
                } else {
                    valid = true;
                }
            }

            // Store guess and compute feedback
            storeGuess(guess, guesses, attempt);
            computeFeedback(secret, guess, results[attempt]);
            // ... use storeGuess and computeFeedback

            // Print board
            printBoard(guesses, results, attempt);

            // Check win
            if (isAllGreen(results[attempt])) {
                System.out.println("Congratulations! You guessed the word in " + (attempt + 1) + " attempts.");
                won = true;
            }

            attempt++;
        }

        if (!won) {
            // ... follow the assignment examples for how the printing should look like
            System.out.println("Sorry, you did not guess the word.");
            System.out.println("The secret word was: " + secret);
        }

        inp.close();
    }
}
