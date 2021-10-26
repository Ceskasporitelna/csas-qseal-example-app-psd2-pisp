package cz.csas.psd2.qsealsample.model.payment;

public class RemittanceInformationStructure {

    private ReferenceInformation creditorReferenceInformation;

    public RemittanceInformationStructure(ReferenceInformation creditorReferenceInformation) {
        this.creditorReferenceInformation = creditorReferenceInformation;
    }

    public ReferenceInformation getCreditorReferenceInformation() {
        return creditorReferenceInformation;
    }

    public void setCreditorReferenceInformation(ReferenceInformation creditorReferenceInformation) {
        this.creditorReferenceInformation = creditorReferenceInformation;
    }
}
