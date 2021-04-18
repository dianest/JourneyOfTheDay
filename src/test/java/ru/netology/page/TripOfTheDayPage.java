package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import ru.netology.data.Card;

import java.util.function.Predicate;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class TripOfTheDayPage {
    private final SelenideElement buyButton = $x("//*[text()=\"Купить\"]");
    private final SelenideElement creditButton = $x("//*[contains(text(), \"Купить в кредит\")]");
    private final SelenideElement continueButton = $x("//button[contains(., \"Продолжить\")]");

    private final SelenideElement cardExpiredError = $x("//*[text()=\"Истёк срок действия карты\"]");
    private final SelenideElement wrongCardDateError = $x("//*[text()=\"Неверно указан срок действия карты\"]");
    private final SelenideElement emptyHolderError = $x("//*[text()=\"Поле обязательно для заполнения\"]");
    private final SelenideElement emptyCodeError = $x("//*[text()=\"Неверный формат\"]");


    private final ElementsCollection inputs = $$(".input__control");
    private final SelenideElement cardNumber = inputs.get(0);
    private final SelenideElement month = inputs.get(1);
    private final SelenideElement year = inputs.get(2);
    private final SelenideElement holder = inputs.get(3);
    private final SelenideElement code = inputs.get(4);

    private final SelenideElement successfulNotification = $x("//*[text()=\"Успешно\"]");
    private final SelenideElement errorNotification = $x("//*[text()=\"Ошибка\"]");

    public TripOfTheDayPage() {
    }

    public TripOfTheDayPage makePaymentRequest(Card card) {
        buyButton.click();

        cardNumber.sendKeys(card.getNumber());
        month.sendKeys(card.getMonth());
        year.sendKeys(card.getYear());
        holder.sendKeys(card.getHolder());
        code.sendKeys(card.getCode());

        continueButton.click();

        return this;
    }

    public TripOfTheDayPage makeCreditRequest(Card card) {
        creditButton.click();

        cardNumber.sendKeys(card.getNumber());
        month.sendKeys(card.getMonth());
        year.sendKeys(card.getYear());
        holder.sendKeys(card.getHolder());
        code.sendKeys(card.getCode());

        continueButton.click();

        return this;
    }

    public void assertCardExpiredError() {
        cardExpiredError.shouldBe(Condition.visible);
        wrongCardDateError.shouldBe(Condition.hidden);
        emptyCodeError.shouldBe(Condition.hidden);
        emptyHolderError.shouldBe(Condition.hidden);
    }

    public void assertWrongCardDateError() {
        cardExpiredError.shouldBe(Condition.hidden);
        wrongCardDateError.shouldBe(Condition.visible);
        emptyCodeError.shouldBe(Condition.hidden);
        emptyHolderError.shouldBe(Condition.hidden);
    }

    public void assertEmptyCodeError() {
        cardExpiredError.shouldBe(Condition.hidden);
        wrongCardDateError.shouldBe(Condition.hidden);
        emptyCodeError.shouldBe(Condition.visible);
        emptyHolderError.shouldBe(Condition.hidden);
    }

    public void assertEmptyHolderError() {
        cardExpiredError.shouldBe(Condition.hidden);
        wrongCardDateError.shouldBe(Condition.hidden);
        emptyCodeError.shouldBe(Condition.hidden);
        emptyHolderError.shouldBe(Condition.visible);
    }

    public void assertOperationSuccessful(ExternalCondition externalCondition) {
        successfulNotification.waitUntil(Condition.visible, 15000)
                .shouldBe(externalCondition);
    }

    public void assertOperationUnsuccessful(ExternalCondition externalCondition) {
        errorNotification.waitUntil(Condition.visible, 15000)
                .shouldBe(externalCondition);
    }

    public static class ExternalCondition extends Condition {
        private final Predicate<Void> predicate;

        public ExternalCondition(Predicate<Void> predicate) {
            super("external");

            this.predicate = predicate;
        }

        @Override
        public boolean apply(Driver driver, WebElement element) {
            return predicate.test(null);
        }
    }
}
