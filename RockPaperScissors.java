import java.util.*;

public class RockPaperScissors {

    static String playRound(String playerMove, String computerMove) {

        if (playerMove.equals(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
            (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
            (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }

        return "Computer Wins";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String[] moves = {"Rock", "Paper", "Scissors"};

        int wins = 0, losses = 0, draws = 0;
        int n = 5;

        String[] playerMoves = new String[n];
        String[] computerMoves = new String[n];
        String[] results = new String[n];

        for (int i = 0; i < n; i++) {

            System.out.print("Enter your move (Rock/Paper/Scissors): ");
            String playerMove = sc.next();

            playerMove = playerMove.substring(0, 1).toUpperCase()
                    + playerMove.substring(1).toLowerCase();

            String computerMove = moves[random.nextInt(3)];

            String result = playRound(playerMove, computerMove);

            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;

            if (result.equals("Player Wins"))
                wins++;
            else if (result.equals("Computer Wins"))
                losses++;
            else
                draws++;
        }

        System.out.println("\nFinal Summary");
        System.out.println("---------------------------------------------");
        System.out.println("Round\tPlayer Move\tComputer Move\tResult");

        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t" +
                    playerMoves[i] + "\t\t" +
                    computerMoves[i] + "\t\t" +
                    results[i]);
        }

        double winPercentage = (wins * 100.0) / n;

        System.out.println("---------------------------------------------");
        System.out.println("Wins   : " + wins);
        System.out.println("Losses : " + losses);
        System.out.println("Draws  : " + draws);
        System.out.println("Win %  : " + winPercentage + "%");

        sc.close();
    }
}
