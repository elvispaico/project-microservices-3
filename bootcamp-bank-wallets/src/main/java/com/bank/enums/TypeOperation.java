package com.bank.enums;

public enum TypeOperation {
    PAY("01"),
    LOAD("02");
    private final String value;

    TypeOperation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
