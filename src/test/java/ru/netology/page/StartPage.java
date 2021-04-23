package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class StartPage {
    private final SelenideElement buyButton = $x("//*[text()=\"Купить\"]");
    private final SelenideElement creditButton = $x("//*[contains(text(), \"Купить в кредит\")]");

    public StartPage() {
    }

    public PaymentPage openPaymentPage() {
        buyButton.click();

        return new PaymentPage();
    }

    public CreditPage openCreditPage() {
        creditButton.click();

        return new CreditPage();
    }
}
