package bluebankapp.swe443.bluebankappandroid;

public class Transaction
{
    public String type = ""; //Undo?[Deposit|Withdraw|Transfer|Creation] - string type of transaction
    public String acc1 = null; //primary account involved in transaction
    public String acc2 = null; //secondary account involved in transaction
    public Double fee; //fee of the transaction
    public Double amount;

    private String DELIM = "%";

    public Transaction(){
        amount = 0.0;
    }

    public Transaction(String t, String a1, String a2, Double f, Double a){
        type = t;
        acc1 = a1;
        acc2 = a2;
        fee = f;
        amount = a;
    }

    public Transaction(String s){
        String[] fields = s.split(DELIM);
        type = fields[0];
        acc1 = fields[1];
        acc2 = fields[2];
        fee = Double.parseDouble(fields[3]);
        amount = Double.parseDouble(fields[4]);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(type + DELIM);
        s.append(acc1 + DELIM);
        s.append(acc2 + DELIM);
        s.append(fee.toString() + DELIM);
        s.append(amount.toString());
        return s.toString();
    }

    public boolean equals(Object o){
        Transaction t = (Transaction) o;
        if (!this.type.equals(t.type)){ return false; }
        if (!this.acc1.equals(t.acc1)){ return false; }
        if (!this.acc2.equals(t.acc2)){ return false; }
        if (this.amount.equals(t.amount)){ return false; }
        return true;
    }
}