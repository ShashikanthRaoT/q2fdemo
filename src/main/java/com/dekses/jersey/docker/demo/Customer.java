package com.dekses.jersey.docker.demo;
import java.time.*;

public class Customer {
    private String name;
    private String accountNumber;
    private long amount;
    private String organization;
    private String bankName;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setOrganization(final String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return this.bankName;
    }

    public String toString() {
        return this.accountNumber + this.name + this.amount + this.bankName + this.organization;
    }

    public String getJson(String curTime) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"customerName\":" + "\"" +this.name + "\",");
        sb.append("\"accountNumber\":" + "\"" +this.accountNumber + "\",");
        sb.append("\"organizationName\":" + "\"" +this.organization + "\",");
        sb.append("\"bankName\":" + "\"" +this.bankName + "\",");
        sb.append("\"amount\":"  + this.amount + ",");
        sb.append("\"transactionTime\":"  + "\"" + curTime + "\"}\n");
        return sb.toString();
    }
}
