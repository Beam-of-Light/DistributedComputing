package entities;

import java.util.Objects;

public class Discipline {
    private String name;
    private Integer course;
    private Integer specialtyCode;

    public Discipline(String name, Integer course, Integer specialtyCode) {
        this.name = name;
        this.course = course;
        this.specialtyCode = specialtyCode;
    }

    public String getName() {
        return name;
    }

    public Integer getCourse() {
        return course;
    }

    public Integer getSpecialtyCode() {
        return specialtyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline d1 = (Discipline) o;
        return name.equals(d1.name) && course.equals(d1.course) && specialtyCode.equals(d1.specialtyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, course, specialtyCode);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "name=" + name +
                ", course=" + course +
                ", specialtyCode=" + specialtyCode +
                '}';
    }
}
