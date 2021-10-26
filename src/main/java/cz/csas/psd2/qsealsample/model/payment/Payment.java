package cz.csas.psd2.qsealsample.model.payment;

import java.time.LocalDate;

public class Payment {
    private PaymentIdentification paymentIdentification;
    private PaymentTypeInformation paymentTypeInformation;
    private Amount amount;
    private LocalDate requestedExecutionDate;
    private Account debtorAccount;
    private Account creditorAccount;
    private RemittanceInformation remittanceInformation;

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static class PaymentBuilder {
        private PaymentIdentification paymentIdentification;
        private PaymentTypeInformation paymentTypeInformation;
        private Amount amount;
        private LocalDate requestedExecutionDate;
        private Account debtorAccount;
        private Account creditorAccount;
        private RemittanceInformation remittanceInformation;

        public PaymentBuilder() {
        }

        public PaymentBuilder paymentIdentification(PaymentIdentification paymentIdentification) {
            this.paymentIdentification = paymentIdentification;
            return this;
        }

        public PaymentBuilder paymentTypeInformation(PaymentTypeInformation paymentTypeInformation) {
            this.paymentTypeInformation = paymentTypeInformation;
            return this;
        }

        public PaymentBuilder amount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBuilder requestedExecutionDate(LocalDate requestedExecutionDate) {
            this.requestedExecutionDate = requestedExecutionDate;
            return this;
        }

        public PaymentBuilder debtorAccount(Account debtorAccount) {
            this.debtorAccount = debtorAccount;
            return this;
        }

        public PaymentBuilder creditorAccount(Account creditorAccount) {
            this.creditorAccount = creditorAccount;
            return this;
        }

        public PaymentBuilder remittanceInformation(RemittanceInformation remittanceInformation) {
            this.remittanceInformation = remittanceInformation;
            return this;
        }

        public Payment build() {
            Payment payment = new Payment();
            payment.amount = amount;
            payment.creditorAccount = creditorAccount;
            payment.debtorAccount = debtorAccount;
            payment.paymentIdentification = paymentIdentification;
            payment.paymentTypeInformation = paymentTypeInformation;
            payment.remittanceInformation = remittanceInformation;
            payment.requestedExecutionDate = requestedExecutionDate;
            return payment;
        }
    }
}
