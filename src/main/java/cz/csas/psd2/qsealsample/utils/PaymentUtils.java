package cz.csas.psd2.qsealsample.utils;

import cz.csas.psd2.qsealsample.model.payment.Account;
import cz.csas.psd2.qsealsample.model.payment.AccountIdentification;
import cz.csas.psd2.qsealsample.model.payment.Amount;
import cz.csas.psd2.qsealsample.model.payment.AmountDetail;
import cz.csas.psd2.qsealsample.model.payment.Payment;
import cz.csas.psd2.qsealsample.model.payment.PaymentIdentification;
import cz.csas.psd2.qsealsample.model.payment.PaymentTypeInformation;
import cz.csas.psd2.qsealsample.model.payment.ReferenceInformation;
import cz.csas.psd2.qsealsample.model.payment.RemittanceInformation;
import cz.csas.psd2.qsealsample.model.payment.RemittanceInformationStructure;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentUtils {

    private static final String INSTRUCTION_PRIORITY = "NORM";
    private static final String CURRENCY = "CZK";

    /**
     *  Create demo request
     * @return Payment demoRequest
     */
    public Payment createDemoRequest() {
        LocalDate requestExecutionDate = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return createPaymentRequest(
                new BigDecimal("1.1"),
                "TPP1234567890",
                "CZ1208000000000259459101", // CSAS demo iban
                "CZ9108000000000111111117", // CSAS demo iban
                "1234",
                "9",
                "5678",
                requestExecutionDate
        );
    }

    /**
     * Create single payment request
     *
     * @param value                     Amount value
     * @param instructionIdentification Payment instruction reference identification
     * @param debtorAccountIban         Debtor's account identification in the IBAN
     * @param creditorAccountIban       Creditor's account identification in the IBAN
     * @param variableSymbol            Variable symbol
     * @param constantSymbol            Constant symbol
     * @param specificSymbol            Specific symbol
     * @param requestedExecutionDate    Requested execution date with format YYYY-MM-DD
     * @return Payment request
     */
    public Payment createPaymentRequest(
            BigDecimal value,
            String instructionIdentification,
            String debtorAccountIban,
            String creditorAccountIban,
            String variableSymbol,
            String constantSymbol,
            String specificSymbol,
            LocalDate requestedExecutionDate
    ) {
        PaymentIdentification paymentIdentification = new PaymentIdentification(instructionIdentification);
        PaymentTypeInformation paymentTypeInformation = new PaymentTypeInformation(INSTRUCTION_PRIORITY);
        Amount amount = new Amount(new AmountDetail(value, CURRENCY));
        Account debtorAccount = new Account(new AccountIdentification(debtorAccountIban));
        Account creditorAccount = new Account(new AccountIdentification(creditorAccountIban));
        String[] references = {variableSymbol, constantSymbol, specificSymbol};
        RemittanceInformation remittanceInformation = new RemittanceInformation(new RemittanceInformationStructure(new ReferenceInformation(references)), "Test");

        return Payment.builder()
                .amount(amount)
                .creditorAccount(creditorAccount)
                .debtorAccount(debtorAccount)
                .paymentIdentification(paymentIdentification)
                .paymentTypeInformation(paymentTypeInformation)
                .remittanceInformation(remittanceInformation)
                .requestedExecutionDate(requestedExecutionDate)
                .build();
    }
}
