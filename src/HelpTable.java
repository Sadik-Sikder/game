import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;

public class HelpTable {

    public static void printHelpTable(List<String> moves) {
        AnsiConsole.systemInstall();
        int size = moves.size();
        String[][] table = new String[size + 1][size + 1];

        System.out.println("The following table shows the results from the user's point of view:");
        System.out.printf("Example: If the user chooses '%s' and the PC chooses '%s', the result will be 'Win'.\n",moves.get(1), moves.get(0));

        table[0][0] = "v PC\\User >";
        for (int i = 0; i < size; i++) {
            table[0][i + 1] = moves.get(i);
            table[i + 1][0] = moves.get(i);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    table[i + 1][j + 1] = "Draw";
                } else {
                    table[i + 1][j + 1] = MoveValidator.determineWinner(moves, moves.get(j), moves.get(i)).equals("You win!") ? "Win" : "Lose";
                }
            }
        }
        printBorder(size);
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                if (i == 0 || j == 0) {
                    System.out.print(Ansi.ansi().bg(Ansi.Color.GREEN).fg(Ansi.Color.BLACK).a(String.format("| %-12s ", table[i][j])).reset());
                }else {
                    String result = table[i][j];
                    switch (result) {
                        case "Win":
                            System.out.print(Ansi.ansi().fg(Ansi.Color.GREEN).a(String.format("| %-12s ", result)).reset());
                            break;
                        case "Lose":
                            System.out.print(Ansi.ansi().fg(Ansi.Color.RED).a(String.format("| %-12s ", result)).reset());
                            break;
                        case "Draw":
                            System.out.print(Ansi.ansi().fg(Ansi.Color.BLUE).a(String.format("| %-12s ", result)).reset());
                            break;
                        default:
                            System.out.print(String.format("| %-12s ", result));
                            break;
                    }
                }
            }
            System.out.println("|");
            printBorder(size);
        }
        AnsiConsole.systemUninstall();
    }

    private static void printBorder(int size) {
        for (int i = 0; i <= size; i++) {
            System.out.print("+--------------");
        }
        System.out.println("+");
    }
}
