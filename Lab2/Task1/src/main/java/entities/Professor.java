package entities;

import java.util.List;
import java.util.Objects;

public class Professor {
    private String name;
    private List<Discipline> disciplines;

    public Professor(String name, List<Discipline> disciplines) {
        this.name = name;
        this.disciplines = disciplines;
    }

    public String getName() {
        return name;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor p1 = (Professor) o;
        return name.equals(p1.name) && Objects.equals(disciplines, p1.disciplines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, disciplines);
    }

    @Override
    public String toString() {
        return "Professor{" +
                "name=" + name +
                ", disciplines=" + disciplines +
                '}';
    }
}
