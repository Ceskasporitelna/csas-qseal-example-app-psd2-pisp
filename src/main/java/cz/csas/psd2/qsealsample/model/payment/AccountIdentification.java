package cz.csas.psd2.qsealsample.model.payment;

public class AccountIdentification {

    private String iban;

    public AccountIdentification(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
