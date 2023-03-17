package mx.edu.utez.biblioteca.biblioteca.model;

public class BeanAutor {
    private long id;
    private String name;
    private String midname;
    private String lastname;

    public BeanAutor(){}
    public BeanAutor(long id,String name,String midname,String lastname){
        this.id=id;
        this.name=name;
        this.midname=midname;
        this.lastname=lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
