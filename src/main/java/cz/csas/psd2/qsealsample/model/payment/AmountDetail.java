package cz.csas.psd2.qsealsample.model.payment;

import java.math.BigDecimal;

public class AmountDetail {

    private BigDecimal value;
    private String currency;

    public AmountDetail(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
