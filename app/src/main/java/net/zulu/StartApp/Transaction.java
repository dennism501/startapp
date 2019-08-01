package net.zulu.StartApp;

public class Transaction {

    private int id;
    private String cname2, type, amount;
    //private int rating;

    public Transaction(int id, String cname2, String type,
                       String amount) {
        this.id = id;
        this.cname2 = cname2;
        this.type = type;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getCname2() {
        return cname2;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }
}
