public class Fine {
    private String name;
    private String goss;
    private String type;
    private String date;

    public Fine(String name, String goss, String type, String date, int key) {
        this.name = name;
        this.goss = goss;
        this.type = type;
        this.date = date;
        this.key = key;
    }

    private int key;

    public String getName() {
        return name;
    }

    public String getGoss() {
        return goss;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getKey() {
        return key;
    }


}
