package cz.csas.psd2.qsealsample.model.payment;

public class Account {

    private AccountIdentification identification;

    public Account(AccountIdentification identification) {
        this.identification = identification;
    }

    public AccountIdentification getIdentification() {
        return identification;
    }

    public void setIdentification(AccountIdentification identification) {
        this.identification = identification;
    }
}
