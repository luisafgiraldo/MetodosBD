package co.poli.edu.ces.universitas.dto;

public class DtoStudent {
    public int id;

    protected String document;

    private String name;

    public DtoStudent(int id, String document, String name){
        this.id = id;
        this.document = document;
        this.name = name;
    }

    public DtoStudent(String document, String name){
        this.name = name;
        this.document = document;
    }

    public DtoStudent() {

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
}
