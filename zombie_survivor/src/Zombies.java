import java.util.Arrays;

public class Zombies {

    private final Zombie[] zombies;
    private int numberOfZombies;

    public Zombies(int maxZombies) {
        zombies = new Zombie[maxZombies];
        numberOfZombies = 0;
    }

    public int getNumberOfZombies() {
        return this.numberOfZombies;
    }

    public void addZombie(Zombie zombie) {
        ++numberOfZombies;
        zombies[numberOfZombies - 1] = zombie;
    }

    public Zombie removeZombie(int zombieIndex) {
        final Zombie removedZombie = zombies[zombieIndex];

        Util.swap(zombies, zombieIndex, numberOfZombies - 1);
        --numberOfZombies;

        return removedZombie;

    }

    public void takeAllZombies(Zombies other) {
        while (other.getNumberOfZombies() > 0) {
            final Zombie removedZombie = other.removeZombie(0);
            addZombie(removedZombie);
        }

    }

    public String toString() {
        Zombie[] smaller = Arrays.copyOf(zombies, numberOfZombies);
        return Arrays.toString(smaller);
    }

}
