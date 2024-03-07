public class BingoRowChecker extends BingoChecker {
    int row;

    public BingoRowChecker(BingoCard card, int row) {
        super(card);
        this.row = row;
    }

    @Override
    public void run() {
        for (int col = 0; col < 5; col++) {
            int num = card.nums[row][col];
            while (BingoGame.result[num] == false) {
                // while (BingoGame.chosenNumbers.contains(num) == false) {
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