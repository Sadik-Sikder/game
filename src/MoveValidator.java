import java.util.List;

public class MoveValidator {
    public static String determineWinner(List<String> moves, String userMove, String computerMove) {
        int userIndex = moves.indexOf(userMove);
        int computerIndex = moves.indexOf(computerMove);

        int halfSize = moves.size() / 2;
        if (userIndex == computerIndex) {
            return "Draw!";
        } else if ((userIndex > computerIndex && userIndex - computerIndex <= halfSize) ||
                (userIndex < computerIndex && computerIndex - userIndex > halfSize)) {
            return "You win!";
        } else {
            return "You lose!";
        }
    }
}
