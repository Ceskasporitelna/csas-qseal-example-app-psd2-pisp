package cz.csas.psd2.qsealsample.model.payment;

public class PaymentIdentification {

    private String instructionIdentification;

    public PaymentIdentification(String instructionIdentification) {
        this.instructionIdentification = instructionIdentification;
    }

    public String getInstructionIdentification() {
        return instructionIdentification;
    }

    public void setInstructionIdentification(String instructionIdentification) {
        this.instructionIdentification = instructionIdentification;
    }
}
