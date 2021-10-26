package cz.csas.psd2.qsealsample.model;

public enum DigestAlgorithm {

    SHA256("SHA-256");

    private final String algorithm;

    DigestAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getDigestAlgorithm() {
        return algorithm;
    }
}
