
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class GeneralizedRockPaperScissors {
    public static void main(String[] args) {

        if (!validateArguments(args)) {
            System.out.println("Invalid input. Please provide an odd number (≥ 3) of unique moves.");
            System.out.println("Example: java -jar game.jar rock paper scissors");
            return;
        }

        List<String> moves = Arrays.asList(args);
        SecureRandom random = new SecureRandom();
        int computerMoveIndex = random.nextInt(moves.size());
        String computerMove = moves.get(computerMoveIndex);

        byte[] key = new byte[32];
        random.nextBytes(key);

        String hmac = HMACGenerator.generateHMAC(key, computerMove);
        System.out.println("HMAC: " + hmac);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Available moves:");
            for (int i = 0; i < moves.size(); i++) {
                System.out.println((i + 1) + " - " + moves.get(i));
            }
            System.out.println("0 - exit");
            System.out.println("? - help");
            System.out.print("Enter your move: ");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                System.out.println("Exiting the game.");
                break;
            } else if (input.equals("?")) {
                HelpTable.printHelpTable(moves);
                continue;
            }

            int userMoveIndex;
            try {
                userMoveIndex = Integer.parseInt(input) - 1;
                if (userMoveIndex < 0 || userMoveIndex >= moves.size()) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number corresponding to your move.");
                continue;
            }

            String userMove = moves.get(userMoveIndex);
            System.out.println("Your move: " + userMove);
            System.out.println("Computer move: " + computerMove);

            String result = MoveValidator.determineWinner(moves, userMove, computerMove);
            System.out.println(result);
            System.out.println("HMAC key: " + HMACGenerator.bytesToHex(key));
            break;
        }

        scanner.close();
    }
    private static boolean validateArguments(String[] args) {
        if (args.length < 3 || args.length % 2 == 0) {
            return false;
        }
        Set<String> uniqueMoves = new HashSet<>();
        for (String move : args) {
            if (!uniqueMoves.add(move)) {
                return false;
            }
        }
        return true;
    }
}
