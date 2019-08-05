package net.zulu.StartApp;

public class Transaction {

    private int id;
    private String cname2, type, amount, cost, date;
    //private int rating;

    public Transaction(int id, String cname2,String cost, String type,
                       String amount, String date) {
        this.id = id;
        this.cname2 = cname2;
        this.cost = cost;
        this.type = type;
        this.amount = amount;
        this.date = date;

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
    public String getCost(){ return cost; }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
