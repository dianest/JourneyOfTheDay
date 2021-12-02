package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import ru.netology.data.Card;
import ru.netology.data.DataGenerator;
import ru.netology.db.DbUtils;
import ru.netology.page.CreditPage;
import ru.netology.page.PaymentPage;
import ru.netology.page.RequestPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.db.DbUtils.clearTables;

public class BankTest {
    private StartPage startPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setup() {
        clearTables();
        open("http://localhost:8080/");

        startPage = new StartPage();
    }

    /**
     * Checks scenario when all data is correct and API returns APPROVED
     * Result: pass
     */
    @Test
    public void testSuccessfulPayment() {
        test(DataGenerator.generateValidCard(), RequestType.PAYMENT, true);
    }

    /**
     * Checks scenario when all data is correct and API returns DECLINED
     * Result: fail. UI shows successful notification and error notification is expected
     */
    @Test
    public void testInvalidCardPayment() {
        test(DataGenerator.generateInvalidCard(), RequestType.PAYMENT, false);
    }

    /**
     * Checks scenario when year is in the past
     * Result: pass. No call to service
     */
    @Test
    public void testExpiredCardPayment() {
        final PaymentPage paymentPage = startPage.openPaymentPage();
        paymentPage.makeRequest(DataGenerator.generateExpiredYearCard());
        paymentPage.assertCardExpiredError();
    }

    /**
     * Checks scenario when month is in the past and year is the same
     * Result: pass. No call to service
     */
    @Test
    public void testWrongCardDataPayment() {
        final PaymentPage paymentPage = startPage.openPaymentPage();
        paymentPage.makeRequest(DataGenerator.generateExpiredMonthCard());
        paymentPage.assertWrongCardDateError();
    }

    /**
     * Checks scenario when code is empty
     * Result: fail. Error for holder field is also visible
     */
    @Test
    public void testInvalidCodeCardPayment() {
        final PaymentPage paymentPage = startPage.openPaymentPage();
        paymentPage.makeRequest(DataGenerator.generateInvalidCodeCard());
        paymentPage.assertEmptyCodeError();
    }

    /**
     * Checks scenario when holder name is not in ASCII symbols
     * Result: fail. Expected to be an error because of unsupported symbols
     */
    @Test
    public void testInvalidHolderCardPayment() {
        test(DataGenerator.generateInvalidHolderCard(), RequestType.PAYMENT, false);
    }

    /**
     * Checks scenario when holder name is empty
     * Result: pass. No call to service
     */
    @Test
    public void testEmptyHolderCardPayment() {
        final PaymentPage paymentPage = startPage.openPaymentPage();
        paymentPage.makeRequest(DataGenerator.generateEmptyHolderCard());
        paymentPage.assertEmptyHolderError();
    }

    /**
     * Checks scenario when card number is invalid
     * Result: pass. Fails as expected
     */
    @Test
    public void testInvalidNumberCardPayment() {
        test(DataGenerator.generateInvalidNumberCard(), RequestType.PAYMENT, false);
    }

    /**
     * Checks scenario when all data is correct and API returns APPROVED
     * Result: pass
     */
    @Test
    public void testSuccessfulCredit() {
        test(DataGenerator.generateValidCard(), RequestType.CREDIT, true);
    }

    /**
     * Checks scenario when all data is correct and API returns DECLINED
     * Result: fail. UI shows successful notification and error notification is expected
     */
    @Test
    public void testInvalidCardCredit() {
        test(DataGenerator.generateInvalidCard(), RequestType.CREDIT, false);
    }

    /**
     * Checks scenario when year is in the past
     * Result: pass. No call to service
     */
    @Test
    public void testExpiredCardCredit() {
        final CreditPage creditPage = startPage.openCreditPage();
        creditPage.makeRequest(DataGenerator.generateExpiredYearCard());
        creditPage.assertCardExpiredError();
    }

    /**
     * Checks scenario when month is in the past and year is the same
     * Result: pass. No call to service
     */
    @Test
    public void testWrongCardDataCredit() {
        final CreditPage creditPage = startPage.openCreditPage();
        creditPage.makeRequest(DataGenerator.generateExpiredMonthCard());
        creditPage.assertWrongCardDateError();
    }

    /**
     * Checks scenario when code is empty
     * Result: fail. Error for holder field is also visible
     */
    @Test
    public void testInvalidCodeCardCredit() {
        final CreditPage creditPage = startPage.openCreditPage();
        creditPage.makeRequest(DataGenerator.generateInvalidCodeCard());
        creditPage.assertEmptyCodeError();
    }

    /**
     * Checks scenario when holder name is not in ASCII symbols
     * Result: fail. Expected to be an error because of unsupported symbols
     */
    @Test
    public void testInvalidHolderCardCredit() {
        test(DataGenerator.generateInvalidHolderCard(), RequestType.CREDIT, false);
    }

    /**
     * Checks scenario when holder name is empty
     * Result: pass. No call to service
     */
    @Test
    public void testEmptyHolderCardCredit() {
        final CreditPage creditPage = startPage.openCreditPage();
        creditPage.makeRequest(DataGenerator.generateEmptyHolderCard());
        creditPage.assertEmptyHolderError();
    }

    /**
     * Checks scenario when card number is invalid
     * Result: pass. Fails as expected
     */
    @Test
    public void testInvalidNumberCardCredit() {
        test(DataGenerator.generateInvalidNumberCard(), RequestType.CREDIT, false);
    }

    private void test(Card card, RequestType requestType, boolean expectedSuccessful) {
        if(requestType == RequestType.PAYMENT) {
            final PaymentPage paymentPage = startPage.openPaymentPage();
            paymentPage.makeRequest(card);
            if(expectedSuccessful) {
                paymentPage.assertOperationSuccessful(
                        new RequestPage.ExternalCondition(unused -> DbUtils.checkPayment()));
            } else {
                paymentPage.assertOperationUnsuccessful(
                        new RequestPage.ExternalCondition(unused -> !DbUtils.checkPayment()));
            }
        } else if(requestType == RequestType.CREDIT) {
            final CreditPage creditPage = startPage.openCreditPage();
            creditPage.makeRequest(card);
            if(expectedSuccessful) {
                creditPage.assertOperationSuccessful(
                        new RequestPage.ExternalCondition(unused -> DbUtils.checkCredit()));
            } else {
                creditPage.assertOperationUnsuccessful(
                        new RequestPage.ExternalCondition(unused -> !DbUtils.checkCredit()));
            }
        } else {
            throw new IllegalArgumentException("Unexpected request type");
        }
    }

    private enum RequestType {
        PAYMENT, CREDIT
    }
}
