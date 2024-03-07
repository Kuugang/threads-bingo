import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class BingoCard {
    int[][] nums;
    int id;

    public BingoCard(int id) {
        this.id = id;
        nums = new int[5][5];

        Set<Integer> randomNumbers = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int lowerBound = i * 15 + 1;
            int upperBound = (i + 1) * 15;

            while (randomNumbers.size() < 5) {
                int r = random.nextInt(upperBound - lowerBound - 1) + lowerBound;
                randomNumbers.add(r);
            }
            Iterator<Integer> it = randomNumbers.iterator();

            for (int j = 0; j < 5; j++) {
                nums[i][j] = it.next();
            }
            randomNumbers.clear();
        }

        nums[2][2] = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                sb.append(nums[row][col]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}