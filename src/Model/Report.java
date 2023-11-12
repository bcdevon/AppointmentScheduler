package Model;

public class Report {
    private String month;
    private String type;
    private long customerCount;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCount() {
        return customerCount;
    }

    public void setCount(long customerCount) {
        this.customerCount = customerCount;
    }

    public Report(String month, String type, long customerCount) {
        this.month = month;
        this.type = type;
        this.customerCount = customerCount;
    }
}

