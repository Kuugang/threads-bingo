public class BingoColChecker extends BingoChecker {
    int col;

    public BingoColChecker(BingoCard card, int col) {
        super(card);
        this.col = col;
    }

    @Override
    public void run() {
        for (int row = 0; row < 5; row++) {
            int num = card.nums[row][col];
            while (BingoGame.result[num] == false) {
                synchronized (BingoGame.result) {
                    try {
                        BingoGame.result.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }
}
