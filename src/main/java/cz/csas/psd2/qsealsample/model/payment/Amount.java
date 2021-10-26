package cz.csas.psd2.qsealsample.model.payment;

public class Amount {

    private AmountDetail instructedAmount;

    public Amount(AmountDetail instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public AmountDetail getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(AmountDetail instructedAmount) {
        this.instructedAmount = instructedAmount;
    }
}
