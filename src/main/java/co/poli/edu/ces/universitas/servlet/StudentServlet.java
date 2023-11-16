package co.poli.edu.ces.universitas.servlet;

import co.poli.edu.ces.universitas.controller.CtrStudent;
import co.poli.edu.ces.universitas.dto.DtoStudent;
import co.poli.edu.ces.universitas.model.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "studentServlet", value = "/student")
public class StudentServlet extends MyServlet {
    private String message;

    private GsonBuilder gsonBuilder;

    private Gson gson;

    private ArrayList<DtoStudent> students;

    CtrStudent ctr = new CtrStudent();

    public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        students = new ArrayList<>();

        DtoStudent student1 = new DtoStudent();
        student1.id = 10;
        student1.setName("pedro");
        student1.setDocument("454543545");

        students.add(student1);

        for (int i = 0; i < students.size(); i++)
        {
            System.out.println(students.get(i));
        }
        message = "Hello Poli!!!";
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        DtoStudent std = new DtoStudent(
                body.get("document").getAsString(),
                body.get("name").getAsString()
        );

        DtoStudent newStudent = ctr.addStudent(std);

        out.print(gson.toJson(newStudent));
        out.flush();


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        String studentIdParam = req.getParameter("id");

        if (studentIdParam != null && !studentIdParam.isEmpty()) {
            int studentId = Integer.parseInt(studentIdParam);
            DtoStudent student = ctr.getStudentById(studentId);
            out.print(gson.toJson(student));
        } else {
            ArrayList<DtoStudent> students = ctr.getAllStudents();
            out.print(gson.toJson(students));
        }

        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JsonObject body = gson.fromJson(stringBuilder.toString(), JsonObject.class);
        int studentId = body.get("id").getAsInt();

        DtoStudent updatedStudent = new DtoStudent(
                body.get("document").getAsString(),
                body.get("name").getAsString()
        );

        DtoStudent result = ctr.updateStudent(studentId, updatedStudent);

        out.print(gson.toJson(result));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");

        int studentId = Integer.parseInt(req.getParameter("id"));

        ctr.deleteStudent(studentId);

        out.print(gson.toJson("Estudiante eliminado"));
        out.flush();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method){
            case "PATCH":
                this.doPatch(req, resp);
                break;
            default:
                super.service(req, resp);
        }

    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("************");
        System.out.println("Entro al metodo patch!!!");
        System.out.println("************");
    }

    public void destroy() {
    }
}