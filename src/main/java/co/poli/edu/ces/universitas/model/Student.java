package co.poli.edu.ces.universitas.model;

import co.poli.edu.ces.universitas.dto.DtoStudent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends Conexion implements CRUD{
    public int id;

    protected String document;

    private String name;

    public Student(int id, String document, String name){
        this.id = id;
        this.document = document;
        this.name = name;
    }

    public Student(String document){
        this.document = document;
    }

    public Student() {
    }

    public int getId(){
        return this.id;
    }


    private void setId(int id){
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "El estudiante se llama: " + this.name +
                " su documento es: " + this.document;
    }

    @Override
    public Student create(DtoStudent student) throws SQLException {
        Connection cnn = this.getConexion();
        if(cnn != null) {
            String sql = "INSERT INTO user(document, name) VALUES('"+student.getDocument()+"', '"+student.getName()+"')";
            this.document = student.getDocument();
            this.name = student.getName();
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                this.id = rs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                cnn.close();
            }
            return this;
        }
        return null;
    }

    @Override
    public ArrayList<Student> all() {
        Connection cnn = this.getConexion();
        ArrayList<Student> students = new ArrayList<>();

        if (cnn != null) {
            String sql = "SELECT * FROM user";
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String document = rs.getString("document");
                    Student student = new Student(id, document, name);
                    students.add(student);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (cnn != null) {
                        cnn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return students;
        }
        return null;
    }



@Override
    public Student findById(int studentId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "SELECT * FROM user WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, studentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String document = rs.getString("document");
                        String name = rs.getString("name");
                        return new Student(id, document, name);
                    } else {
                        return null;
                    }
                }
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return null;
    }

    @Override
    public Student update(Student student) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "UPDATE user SET document = ?, name = ? WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, student.getDocument());
                stmt.setString(2, student.getName());
                stmt.setInt(3, student.getId());
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return student;
    }

    @Override
    public void delete(int studentId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "DELETE FROM user WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, studentId);
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
    }


}
