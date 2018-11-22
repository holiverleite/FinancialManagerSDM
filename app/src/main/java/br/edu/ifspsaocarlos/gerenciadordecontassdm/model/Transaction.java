package br.edu.ifspsaocarlos.gerenciadordecontassdm.model;

import java.io.Serializable;

public class Transaction implements Serializable {

    private String accountName;
    private String value;
    private Boolean isCredit;
    private String transactionDescription;

    public Transaction() {}

    public Transaction(String value, Boolean isCredit, String transactionDescription) {
        this.value = value;
        this.isCredit = isCredit;
        this.transactionDescription = transactionDescription;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCredit() {
        return isCredit;
    }

    public void setCredit(Boolean credit) {
        isCredit = credit;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
