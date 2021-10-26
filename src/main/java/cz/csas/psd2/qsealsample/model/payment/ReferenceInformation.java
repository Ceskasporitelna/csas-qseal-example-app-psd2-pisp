package cz.csas.psd2.qsealsample.model.payment;

public class ReferenceInformation {

    private String[] reference;

    public ReferenceInformation(String[] reference) {
        this.reference = reference;
    }

    public String[] getReference() {
        return reference;
    }

    public void setReference(String[] reference) {
        this.reference = reference;
    }
}
