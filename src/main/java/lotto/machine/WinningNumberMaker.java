package lotto.machine;

import java.util.ArrayList;
import java.util.List;

import lotto.message.ErrorMessage;
import camp.nextstep.edu.missionutils.Console;
import lotto.message.Message;

public class WinningNumberMaker {
    private final int START_NUM = 1;
    private final int END_NUM = 45;

    private String winningNumberInput;
    private String bonusNumberInput;
    private String[] inputSplitByComma;
    private List<Integer> winningNumbers;
    private int bonusNumber;

    public void make() throws IllegalArgumentException{
        makeWinningNumbers();
        makeBonusNumber();
    }

    public void makeWinningNumbers() throws IllegalArgumentException{
        requestWinningNumber();
        getWinningNumberInput();
        divideByComma();
        validateInputSplitByComma();
        saveWinningNumbers();
    }

    public void requestWinningNumber() {
        System.out.println(Message.ENTER_WINNING_NUMBER.message);
    }

    public void getWinningNumberInput() {
        this.winningNumberInput = Console.readLine();
    }

    public void divideByComma() {
        this.inputSplitByComma = this.winningNumberInput.split(",");
    }

    public String[] showInputSplitByComma() {
        return this.inputSplitByComma;
    }

    public void validateInputSplitByComma() {
        if (this.inputSplitByComma.length != 6) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_NUM.message);
        }
        for (String winningNumberStr : inputSplitByComma) {
            validateWinningNumberStr(winningNumberStr);
        }
    }

    public void validateWinningNumberStr(String winningNumberStr) {
        for (int i = 0; i < winningNumberStr.length(); i++) {
            if (!Character.isDigit(winningNumberStr.charAt(i))) {
                throw new IllegalArgumentException(ErrorMessage.WINNING_NUM_NOT_A_NUMBER.message);
            }
        }
        int winningNumber = Integer.parseInt(winningNumberStr);
        if (winningNumber < 1 || winningNumber > 45) {
            throw new IllegalArgumentException(ErrorMessage.NUMBER_OUT_OF_RANGE.message);
        }
    }

    public void saveWinningNumbers() {
        this.winningNumbers = new ArrayList<>();
        for (String winningNumberStr : inputSplitByComma) {
            this.winningNumbers.add(Integer.parseInt(winningNumberStr));
        }
    }

    public List<Integer> getWinningNumbers() {
        return new ArrayList<>(this.winningNumbers);
    }

    public void makeBonusNumber() throws IllegalArgumentException {
        requestBonusNumber();
        getBonusNumberInput();
        validateBonusNumberInput();
        saveBonusNumber();
        validateBonusNumber();
    }

    public void requestBonusNumber() {
        System.out.println(Message.ENTER_BONUS_NUMBER.message);
    }

    public void getBonusNumberInput() {
        this.bonusNumberInput = Console.readLine();
    }

    public void validateBonusNumberInput() {
        this.bonusNumberInput.chars().forEach(c -> {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException(ErrorMessage.BONUS_NUM_NOT_A_NUMBER.message);
            }
        });
    }

    public void saveBonusNumber() {
        this.bonusNumber = Integer.parseInt(this.bonusNumberInput);
    }

    public void validateBonusNumber() {
        if (!(START_NUM <= this.bonusNumber && this.bonusNumber <= END_NUM)) {
            throw new IllegalArgumentException(ErrorMessage.NUMBER_OUT_OF_RANGE.message);
        }
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.WINNING_NUM_CONTAINS_BONUS_NUM.message);
        }
    }

    public int getBonusNumber(){
        return bonusNumber;
    }
}