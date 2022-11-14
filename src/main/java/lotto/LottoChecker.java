package lotto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoChecker {
    private final List<Integer> WINNING_NUMBERS;
    private final int BONUS_NUMBER;
    private final int LOTTO_PRICE = 1000;

    private List<Lotto> lottos = new ArrayList<>();
    private Map<Prize, Integer> result = new HashMap<>();

    LottoChecker(List<Integer> winningNumbers, int bonusNumber) {
        this.WINNING_NUMBERS = winningNumbers;
        this.BONUS_NUMBER = bonusNumber;
        initResult();
    }

    private void initResult() {
        this.result.put(Prize.FIRST, 0);
        this.result.put(Prize.SECOND, 0);
        this.result.put(Prize.THIRD, 0);
        this.result.put(Prize.FOURTH, 0);
        this.result.put(Prize.FIFTH, 0);
        this.result.put(Prize.NONE, 0);
    }

    Map<Prize, Integer> showResult() {
        return new HashMap<>(result);
    }

    void printResult(){
        saveAllResult();
        printEachPrizeWinCount();
        printProfitRate();
    }

    void insertLottos(List<Lotto> lottos) {
        this.lottos = lottos;
    }

    void saveAllResult() {
        for (Lotto lotto : lottos) {
            int matches = howManyMatches(lotto);
            boolean isContainBonus = false;
            if (matches == 5) {
                isContainBonus = checkBonusNumber(lotto);
            }
            Prize prize = getPrize(matches, isContainBonus);
            this.result.put(prize, this.result.get(prize) + 1);
        }
    }

    int howManyMatches(Lotto lotto) {
        int matches = 0;
        for (Integer number : lotto.showNumbers()) {
            if (this.WINNING_NUMBERS.contains(number)) {
                matches++;
            }
        }
        return matches;
    }

    boolean checkBonusNumber(Lotto lotto) {
        return lotto.showNumbers().contains(BONUS_NUMBER);
    }

    Prize getPrize(int matches, boolean isContainBonus) {
        if (matches == 6) {
            return Prize.FIRST;
        }
        if (isContainBonus) {
            return Prize.SECOND;
        }
        if (matches == 5) {
            return Prize.THIRD;
        }
        if (matches == 4) {
            return Prize.FOURTH;
        }
        if (matches == 3) {
            return Prize.FIFTH;
        }
        return Prize.NONE;
    }

    void printEachPrizeWinCount() {
        System.out.println(Messages.PRIZE_RESULT.message);
        printPrizeWinCount(Prize.FIFTH);
        printPrizeWinCount(Prize.FOURTH);
        printPrizeWinCount(Prize.THIRD);
        printPrizeWinCount(Prize.SECOND);
        printPrizeWinCount(Prize.FIRST);
    }

    void printPrizeWinCount(Prize prize) {
        System.out.println(Prize.prizeInfo(prize) + " - " + result.get(prize) + "개");
    }

    long getTotalPrizeMoney() {
        long totalPrizeMoney = 0;
        for (Prize prize : result.keySet()) {
            totalPrizeMoney += prize.money * result.get(prize);
        }
        return totalPrizeMoney;
    }

    String getProfitRate(long totalPrizeMoney) {
        if (totalPrizeMoney == 0) {
            return "0.0";
        }
        DecimalFormat df = new DecimalFormat("#.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double moneyUsed = (double) lottos.size() * LOTTO_PRICE;
        double profitRate = totalPrizeMoney / moneyUsed * 100;
        return df.format(profitRate);
    }

    void printProfitRate() {
        System.out.println("총 수익률은 " + getProfitRate(getTotalPrizeMoney()) + "%입니다.");
    }
}
