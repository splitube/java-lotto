package lotto.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import lotto.Customer;
import lotto.Lotto;
import lotto.message.ErrorMessage;
import lotto.message.Message;

public class LottoKiosk {
    private final int LOTTO_PRICE = 1000;

    private String moneyInput;
    private long money;
    private long howMany;
    private List<Lotto> lottos;

    public void getMoney() throws IllegalArgumentException {
        askHowMuch();
        moneyInserted();
        validateMoneyInput();
        chargeMoney();
        validateMoney();
    }

    public void askHowMuch() {
        System.out.println(Message.REQUEST_MONEY.message);
    }

    public void moneyInserted() {
        this.moneyInput = Console.readLine();
    }

    public void validateMoneyInput() {
        this.moneyInput.chars().forEach(o -> {
            if (!Character.isDigit(o)) {
                throw new IllegalArgumentException(ErrorMessage.MONEY_NOT_A_NUMBER.message);
            }
        });
    }

    public void chargeMoney() {
        money = Long.parseLong(moneyInput);
    }

    public void validateMoney() {
        if (this.money < 1000) {
            throw new IllegalArgumentException(ErrorMessage.LESS_THAN_THOUSAND.message);
        }
        if (this.money % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(ErrorMessage.DIVIDE_DISABLE.message);
        }
    }

    public long showInsertedMoney() {
        return this.money;
    }

    public void sellLotto(Customer customer) {
        calculateLottoAmount();
        showHowManyLotto();
        makeAllLotto();
        printAllLottoNumber();
        customer.getLotto(lottos);
    }

    public void calculateLottoAmount() {
        this.howMany = money / LOTTO_PRICE;
    }

    public void showHowManyLotto() {
        System.out.println("\n" + howMany + Message.HOW_MANY_SOLD.message);
    }

    public void makeAllLotto() {
        lottos = new ArrayList<>();
        for (int i = 0; i < this.howMany; i++) {
            lottos.add(makeLotto(makeLottoNumbers()));
        }
    }

    public Lotto makeLotto(List<Integer> numbers) {
        return new Lotto(numbers);
    }

    public List<Integer> makeLottoNumbers() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6).stream().sorted().collect(Collectors.toList());
    }

    public void printAllLottoNumber() {
        for (Lotto lotto : lottos) {
            System.out.println(lotto.showNumbers());
        }
    }

    public List<Lotto> showAllLotto() {
        return new ArrayList<>(lottos);
    }

    public long showHowMany() {
        return this.howMany;
    }
}
