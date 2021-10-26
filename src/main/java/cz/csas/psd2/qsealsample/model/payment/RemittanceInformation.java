package cz.csas.psd2.qsealsample.model.payment;

public class RemittanceInformation {

    private RemittanceInformationStructure structured;

    private String unstructured;

    public RemittanceInformation(RemittanceInformationStructure structured, String unstructured) {
        this.structured = structured;
        this.unstructured = unstructured;
    }

    public RemittanceInformationStructure getStructured() {
        return structured;
    }

    public void setStructured(RemittanceInformationStructure structured) {
        this.structured = structured;
    }

    public String getUnstructured() {
        return unstructured;
    }

    public void setUnstructured(String unstructured) {
        this.unstructured = unstructured;
    }
}
