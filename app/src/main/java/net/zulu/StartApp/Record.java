package net.zulu.StartApp;


class Record {
    private int id;
    private String cname, fname, number;

    public Record(int id, String cname, String fname,
                  String number) {
        this.id = id;
        this.cname = cname;
        this.fname = fname;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getCname() {
        return cname;
    }

    public String getFname() {
        return fname;
    }

    public String getNumber() {
        return number;
    }
}
