public class Citizens {
    int id;
    String name;
    String language;

    public Citizens(int id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }

    public Citizens() {
    }

    @Override
    public String toString() {
        return "Citizens{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", language=" + language +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }
}
