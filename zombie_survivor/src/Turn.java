public class Turn {

    private static final int NUM_EASY_ZOMBIES = 20;
    private static final int NUM_MEDIUM_ZOMBIES = 20;
    private static final int NUM_HARD_ZOMBIES = 10;

    private static final int MAX_NUM_ZOMBIES = NUM_EASY_ZOMBIES
            + NUM_MEDIUM_ZOMBIES + NUM_HARD_ZOMBIES;

    private static final int BITE_LIMIT =10;
    private static final int HAND_SIZE = 5;

    private final int player;

    private final Zombies zombiePopulation = new Zombies(MAX_NUM_ZOMBIES);
    private final Zombies destroyed = new Zombies(MAX_NUM_ZOMBIES);
    private final Zombies hit = new Zombies(HAND_SIZE);
    private final Zombies bitten = new Zombies(MAX_NUM_ZOMBIES);

    public Turn(int player) {
        this.player = player;
        for (int i = 0; i < NUM_EASY_ZOMBIES; ++i) {
            zombiePopulation.addZombie(Zombie.EASY);
        }

        for (int i = NUM_EASY_ZOMBIES; i < NUM_EASY_ZOMBIES + NUM_MEDIUM_ZOMBIES; ++i) {
            zombiePopulation.addZombie(Zombie.MEDIUM);
        }

        for (int i = NUM_EASY_ZOMBIES + NUM_MEDIUM_ZOMBIES; i < MAX_NUM_ZOMBIES; ++i) {
            zombiePopulation.addZombie(Zombie.HARD);
        }

    }

    public int getCurrentPlayer() {

        return this.player;

    }

    public boolean hasBeenBittenTooManyTimes() {

        return bitten.getNumberOfZombies() >= 6;

    }

    public int getCurrentScore() {

        if (hasBeenBittenTooManyTimes()) {
            return 0;
        }

        return destroyed.getNumberOfZombies();
    }

    private void addZombies(Zombies hand, int extraZombiesNeeded) {
        if (zombiePopulation.getNumberOfZombies() <= extraZombiesNeeded) {
            hand.takeAllZombies(zombiePopulation);
            return;
        }

        for (int i = 0; i <= extraZombiesNeeded; ++i) {
            final double chance = Math.random();
            final int randomIndex = (int) chance * MAX_NUM_ZOMBIES;
            final Zombie randomZombie = zombiePopulation.removeZombie(randomIndex);
            hand.addZombie(randomZombie);
        }

    }

    private Outcome[] getOutcomesForHand(Zombies hand) {

        Outcome[] outcome = new Outcome[hand.getNumberOfZombies()];
        int i = 0;
        while (hand.getNumberOfZombies() > 0) {
            Zombie removedZombie = hand.removeZombie(0);
            Outcome currentOutcome = removedZombie.randomOutcome();

            if (currentOutcome == Outcome.BITTEN) {
                bitten.addZombie(removedZombie);
            }

            if (currentOutcome == Outcome.HIT) {
                hit.addZombie(removedZombie);
            }

            if (currentOutcome == Outcome.DESTROYED) {
                destroyed.addZombie(removedZombie);
            }

            outcome[i] = currentOutcome;
        }

        return outcome;

    }

    public Outcome[] drawAndGetOutcomes() {
        Zombies hand = new Zombies(6);
        while (hit.getNumberOfZombies() > 0) {
            Zombie removedZombie = hit.removeZombie(0);
            hand.addZombie(removedZombie);
        }
        addZombies(hand, HAND_SIZE - hand.getNumberOfZombies());

        return getOutcomesForHand(hand);

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Destroyed: ");
        sb.append(destroyed);
        sb.append("\n");
        sb.append("Hits: ");
        sb.append(hit);
        sb.append("\n");
        sb.append("Bites: ");
        sb.append(bitten);
        return sb.toString();
    }

}
