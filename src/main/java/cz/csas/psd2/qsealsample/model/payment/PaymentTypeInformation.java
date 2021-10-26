package cz.csas.psd2.qsealsample.model.payment;

public class PaymentTypeInformation {

    private String instructionPriority;

    public PaymentTypeInformation(String instructionPriority) {
        this.instructionPriority = instructionPriority;
    }

    public String getInstructionPriority() {
        return instructionPriority;
    }

    public void setInstructionPriority(String instructionPriority) {
        this.instructionPriority = instructionPriority;
    }
}
