import java.util.List;

public class People {
    private String name;
    private String pass;
    private String goss_number;
    private String mark;
    private String date_techn_inspect;
    private List<Fine> fines;
    private int key;

    public People(String name, String pass, String goss_number, String mark, String date_techn_inspect, List<Fine> fines, int key) {
        this.name = name;
        this.pass = pass;
        this.goss_number = goss_number;
        this.mark = mark;
        this.date_techn_inspect = date_techn_inspect;
        this.fines = fines;
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setGoss_number(String goss_number) {
        this.goss_number = goss_number;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setDate_techn_inspect(String date_techn_inspect) {
        this.date_techn_inspect = date_techn_inspect;
    }

    public void setFines(List<Fine> fines) {
        this.fines = fines;
    }

    public int getKey() {
        return key;
    }

    public List<Fine> getFines() {
        return fines;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getGoss_number() {
        return goss_number;
    }

    public String getMark() {
        return mark;
    }

    public String getDate_techn_inspect() {
        return date_techn_inspect;
    }




}
