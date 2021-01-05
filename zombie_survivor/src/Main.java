import java.util.Arrays;

public class Main {

    private static void playTurn(Turn turn) {
        System.out.println("It's player " + turn.getCurrentPlayer() + " turn!");
        while (true) {
            Outcome[] outcome = turn.drawAndGetOutcomes();
            if (outcome.length == 0) {
                System.out.println("There are no more zombies");
                return;
            }

            System.out.print("You got : [");
            for (int i = 0; i < 4; ++i) {
                System.out.print(outcome[i] + ", ");
            }
            System.out.println(outcome[4] + "]");

            System.out.println("Current piles: ");
            System.out.println(turn.toString());

            if (turn.hasBeenBittenTooManyTimes()) {
                System.out.print("You scored 0 because you have been bitten by more ");
                System.out.println("than 5 zombies, your turn will be ended!");
                return;
            }

            System.out.println("Enter s to score, or anything else to draw again:");
            String state;
            state = IOUtil.readString();
            if (state.equals("s")) {
                System.out.println("You scored " + turn.getCurrentScore());
                return;
            }

        }

    }

    public static void main(String[] args) {
        System.out.println("Welcome to Zombie Survivor!");
        System.out.println("How many survivors ?");
        var numberOfSurvivors = IOUtil.readInt();

        var zombieSurvivor = new ZombieSurvivor(numberOfSurvivors);

        while (!zombieSurvivor.isGameOver()) {
            var currentTurn = zombieSurvivor.startPlayerTurn();

            playTurn(currentTurn);

            zombieSurvivor.scorePlayerTurn(currentTurn);

            zombieSurvivor.nextPlayer();
        }
    }
}
