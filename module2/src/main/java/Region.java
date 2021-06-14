public class Region {
    int id;
    int citizensId;
    String name;
    int square;

    public Region(int id, int citizensId, String name, int square) {
        this.id = id;
        this.citizensId = citizensId;
        this.name = name;
        this.square = square;
    }

    public Region() {
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", citizensId=" + citizensId +
                ", name='" + name + "'" +
                ", square=" + square +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getCitizensId() {
        return citizensId;
    }

    public String getName() {
        return name;
    }

    public int getSquare() {
        return square;
    }
}
