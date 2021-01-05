import java.util.Arrays;

public class ZombieSurvivor {

    private static final int ENDGAME_SCORE = 7;

    private final int[] playerScores;

    private int currentPlayer;

    public ZombieSurvivor(int numPlayers) {
        System.out.println("Let the slaughter of zombies begin. The first player to kill " + ENDGAME_SCORE + " walking dead will live to see another day in Zombieland.");
        this.playerScores = new int[numPlayers];
        this.currentPlayer = 0;
    }

    public boolean isGameOver() {
        int index = Util.findIndexGreaterThanOrEqualTo(playerScores, ENDGAME_SCORE);
        if (index >= 0) {
            System.out.println("Player " + getCurrentPlayer() + " gets another chance in Zombieland. He/She was the first to kill " + ENDGAME_SCORE + " zombies.");
        }
        return index >= 0;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Turn startPlayerTurn() {
        return new Turn(currentPlayer);
    }

    public void scorePlayerTurn(Turn turn) {
        assert turn.getCurrentPlayer() == currentPlayer :
                "Turn is for the wrong player";
        playerScores[currentPlayer] += turn.getCurrentScore();
    }

    public void nextPlayer() {
        currentPlayer++;
        currentPlayer %= playerScores.length;
    }

    public int getWinningPlayer() {
        int index = Util.findIndexGreaterThanOrEqualTo(playerScores, ENDGAME_SCORE);
        return index;
    }

    public String toString() {
        return Arrays.toString(playerScores);
    }


}
