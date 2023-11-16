package co.poli.edu.ces.universitas.controller;

import co.poli.edu.ces.universitas.dto.DtoStudent;
import co.poli.edu.ces.universitas.model.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class CtrStudent {

    private Student modelStudent;

    public CtrStudent(){
        modelStudent = new Student();
    }

    public DtoStudent addStudent(DtoStudent student){
        try {
            Student newStudent = modelStudent.create(student);
            return new DtoStudent(newStudent.getId(), newStudent.getDocument(), newStudent.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DtoStudent> getAllStudents() {
        try {
            ArrayList<Student> students = modelStudent.all(); // Llama al método 'all' del modelo
            ArrayList<DtoStudent> dtoStudents = new ArrayList<>();

            for (Student student : students) {
                DtoStudent dtoStudent = new DtoStudent(
                        student.getId(),
                        student.getDocument(),
                        student.getName()
                );
                dtoStudents.add(dtoStudent);
            }

            return dtoStudents;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DtoStudent getStudentById(int studentId) {
        try {
            Student student = modelStudent.findById(studentId);
            if (student != null) {
                return new DtoStudent(student.getId(), student.getDocument(), student.getName());
            } else {
                throw new RuntimeException("Estudiante no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DtoStudent updateStudent(int studentId, DtoStudent updatedStudent) {
        try {
            Student student = new Student(
                    studentId,
                    updatedStudent.getDocument(),
                    updatedStudent.getName()

            );

            Student updated = modelStudent.update(student);
            return new DtoStudent(updated.getId(), updated.getDocument(), updated.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int studentId) {
        try {
            modelStudent.delete(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
