import java.util.List;
public class Root {
    private List<People> people;
    private List<Fine> fine;

    public void setFine(List<Fine> fine) {
        this.fine = fine;
    }

    public List<People> getPeople() {
        return people;
    }

    public List<Fine> getFine() {
        return fine;
    }

    public void setPeople(List<People> people) {
        this.people = people;
    }
}
