package com.intuit.profilevalidationsystem.constants;

public enum ProductType {

    QUICKBOOKS("Accounting"),
    QB_PAYROLL("To pay employees"),
    QB_PAYMENTS("To receive payments"),
    TSHEETS("Time-tracking");

    private String purpose;

    ProductType(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
