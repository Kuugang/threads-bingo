import java.util.ArrayList;
import java.util.List;

public class BingoPattern implements Runnable {
    List<BingoChecker> checkers = new ArrayList<>();
    BingoCard card;

    public BingoPattern(BingoCard card) {
        this.card = card;
    }

    public void run() {
        List<Thread> threads = new ArrayList<>();

        for (BingoChecker checker : checkers) {
            threads.add(new Thread(checker));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Card " + card.id + " loses");
                for (Thread thd : threads) {
                    thd.interrupt();
                }

                return;
            }
        }

        System.out.println("CARD WON: " + card.id);

        synchronized (BingoGame.result) {
            BingoGame.isBingo = true;
        }
    }

    public static class BingoPlusPattern extends BingoPattern {
        public BingoPlusPattern(BingoCard card) {
            super(card);
            this.checkers.add(new BingoRowChecker(card, 2));
            this.checkers.add(new BingoColChecker(card, 2));
        }
    }

    public static class BingoHashPattern extends BingoPattern {
        public BingoHashPattern(BingoCard card) {
            super(card);
            this.checkers.add(new BingoColChecker(card, 1));
            this.checkers.add(new BingoColChecker(card, 4));
            this.checkers.add(new BingoRowChecker(card, 1));
            this.checkers.add(new BingoRowChecker(card, 4));
        }
    }
}