package co.poli.edu.ces.universitas.model;

import co.poli.edu.ces.universitas.dto.DtoStudent;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD {
    Student create(DtoStudent student) throws SQLException;

    public ArrayList<Student> all();

    public Student findById(int id) throws SQLException;

    Student update(Student student) throws SQLException;

    void delete(int studentId) throws SQLException;
}