package entities;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private List<Professor> professors = new ArrayList<>();

    private String name;
    private Integer code;

    public Department(String name, Integer code, List<Professor> professors) {
        this.name = name;
        this.code = code;
        this.professors = professors;
    }

    public Professor getProfessor(String name) {
        for (Professor p : professors) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Discipline> getDisciplinesByProfessor(Professor professor) {
        if (professors.contains(professor)) {
            return professors.get(professors.indexOf(professor)).getDisciplines();
        }
        return null;
    }

    public boolean addProfessor(Professor professor) {
        if (!professors.contains(professor)) {
            professors.add(professor);
            return true;
        }
        return false;
    }

    public boolean addDisciplineByProfessor(Professor professor, Discipline discipline){
        if (professors.contains(professor)) {
            professors.get(professors.indexOf(professor)).addDiscipline(discipline);
            return true;
        }
        return false;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name=" + name +
                "code=" + code +
                '}';
    }
}
