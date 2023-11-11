package Model;

public class Report {
    private String month;
    private String type;
    private long count;

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
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Report(String month, String type, long count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }
}

