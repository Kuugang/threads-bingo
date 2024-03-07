
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BingoGame implements Runnable {
    List<BingoCard> cards;
    static boolean[] result;
    static boolean isBingo = false;
    public static Set<Integer> chosenNumbers = new HashSet<>();

    public BingoGame() {
        result = new boolean[76];
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        System.out.print("How many players? ");
        int cnt = sc.nextInt();
        cards = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            cards.add(new BingoCard(i + 1));
        }

        List<Thread> checkerThrds = new ArrayList<>();

        System.out.println("Enter pattern: ");
        sc.nextLine();
        String pattern = sc.nextLine();

        for (BingoCard card : cards) {

            System.out.println(card);

            switch (pattern.charAt(0)) {
                case '+':
                    checkerThrds.add(new Thread(new BingoPattern.BingoPlusPattern(card)));
                    break;
                case '#':
                    checkerThrds.add(new Thread(new BingoPattern.BingoHashPattern(card)));
                    break;
                default:
                    System.out.println("Invalid pattern");
                    return;
            }
        }

        for (Thread ct : checkerThrds) {
            ct.start();
        }

        result[0] = true;
        synchronized (result) {
            while (!isBingo) {
                int randomNum = random.nextInt(75) + 1;
                if (result[randomNum] == true)
                    continue;

                chosenNumbers.add(randomNum);
                result[randomNum] = true;

                try {
                    result.notifyAll();
                    result.wait(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int num : chosenNumbers) {
            System.out.print(num + " ");
        }
        System.out.println("\n");

        for (Thread checker : checkerThrds) {
            checker.interrupt();
        }

        return;
    }
}
