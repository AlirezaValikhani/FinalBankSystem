package models;

import java.sql.Date;
import java.sql.Time;

public class Transaction {
    private Long id;
    private String accountNumber;
    private String originCardNumber;
    private String destinationCardNumber;
    private String amount;
    private Date date;
    private Time time;
    private TransactionType transactionType;

    public Transaction(Long id, String accountNumber, String originCardNumber, String destinationCardNumber, String amount, Date date, Time time, TransactionType transactionType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.originCardNumber = originCardNumber;
        this.destinationCardNumber = destinationCardNumber;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.transactionType = transactionType;
    }

    public Transaction(String accountNumber, String originCardNumber, String destinationCardNumber, String amount, Date date, Time time, TransactionType transactionType) {
        this.accountNumber = accountNumber;
        this.originCardNumber = originCardNumber;
        this.destinationCardNumber = destinationCardNumber;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.transactionType = transactionType;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOriginCardNumber() {
        return originCardNumber;
    }

    public void setOriginCardNumber(String originCardNumber) {
        this.originCardNumber = originCardNumber;
    }

    public String getDestinationCardNumber() {
        return destinationCardNumber;
    }

    public void setDestinationCardNumber(String destinationCardNumber) {
        this.destinationCardNumber = destinationCardNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
