package com.dekses.jersey.docker.demo;
import  java.nio.ByteBuffer;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SwiftMessage {
    private long amountCredit;
    private long accountNumber;
    private String accountName;

    public SwiftMessage() {

    }

    public void setAmountCredit(long amount) {
        this.amountCredit = amount;
    }
    public long getAmountCredit() {
        return this.amountCredit;
    }

    public void setAccountNumber(long number) {
        this.accountNumber = number;
    }
    public long getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountName(String name) {
        this.accountName = name;
    }
    public String getAccountName() {
        return this.accountName;
    }

    public byte [] getData() {
        ByteBuffer buff = ByteBuffer.allocate(100);
        buff.putLong(0, this.accountNumber);
        buff.putLong(4, this.amountCredit);
        buff.put(this.accountName.getBytes());
        return buff.array();
    }
}
