package bullscows;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

class BullsAndCows {
    String[] symbols = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    int bulls;
    int cows;
    String guessMe = "";
    public BullsAndCows(int length, int possibleSymbolCount) {

        boolean flag = false;
        do {
            flag = false;
            guessMe = createSecret(length, possibleSymbolCount);
            for (int i = 0; i < guessMe.length(); i++)
                for (int j = i + 1; j < guessMe.length(); j++)
                    if (guessMe.charAt(i) == guessMe.charAt(j)) {
                        flag = true;
                        break;
                    }
        } while(flag);

        String range = "";
        if (possibleSymbolCount <= 9) {
            range = "(0-" + String.valueOf(possibleSymbolCount) + ")";
        } else if (possibleSymbolCount == 10) {
            range = "(0-9, a)";

        } else if (possibleSymbolCount <= 36) {
            range = "(0-9, a-" + symbols[possibleSymbolCount - 1] + ")";
        }

        System.out.printf("The secret is prepared: %s %s\n", printStars(length), range);
    }

    private String printStars(int i) {
        if (i == 0) {
            return "";
        }
        if (i == 1) {
            return "*";
        }
        return "*" + printStars(--i);
    }

    public String createSecret(int length, int possibleSymbolCount) {
        Random random = new Random();
        String tempGuess = "";
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(possibleSymbolCount);
            tempGuess += symbols[index];
        }
        return tempGuess;
    }
    public void findBullsAndCows(String guess, int length) {
        bulls = 0;
        cows = 0;
        for(int i = 0; i < length; i++) {
            if(guessMe.charAt(i) == guess.charAt(i)) {
                bulls++;
            } else if (guessMe.contains(guess.substring(i,i+1))) {
                cows++;
            }
        }
    }
    public boolean findGrade(int length) {
        if (bulls == length) {
            System.out.printf("Grade: %d bulls\n", length);
            System.out.println("Congratulations! You guessed the secret code.");
            return true;
        }
        if (bulls > 0 && cows > 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bulls, cows);
        } else if (bulls > 0) {
            System.out.printf("Grade: %d bull(s).\n", bulls);
        } else if (cows > 0) {
            System.out.printf("Grade: %d cow(s)\n", cows);
        } else {
            System.out.print("Grade: None.\n");
        }
        return false;
    }
}
public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String guess;
        String validatingLength = null;
        try {
            System.out.println("Input the length of the secret code: ");
            validatingLength = scan.nextLine();
            int length = Integer.parseInt(validatingLength);
            if (length == 0) {
                System.out.println("Error: Length can't be 0");
                System.exit(0);
            }

            System.out.println("Input the number of possible symbols in the code:");
            validatingLength = scan.nextLine();
            int possibleSymbolCount = Integer.parseInt(validatingLength);

            if (possibleSymbolCount < length) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", length, possibleSymbolCount);
                System.exit(0);
            }
            if (possibleSymbolCount > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            } else {
                BullsAndCows bc = new BullsAndCows(length, possibleSymbolCount);
                System.out.println("Okay, let's start a game!");
                for(int turn = 1; true; turn++) {
                    System.out.printf("Turn %d:\n", turn);
                    guess = scan.next();
                    bc.findBullsAndCows(guess, length);
                    if (bc.findGrade(length)) {
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", validatingLength);
            System.exit(0);
        }
    }
}
