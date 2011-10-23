package ar.edu.unq.tpi.util.services.jasper;

public class Person {

    private String legajo;

    private String apellido;

    private String nombre;

    private String name;

    private Integer dni;

    private String phoneType;

    private String phoneNumber;

    public Person() {

    }

    public Person(final String nombre, final String apellido, final String legajo, final int dni) {
        this.setNombre(nombre);
        this.setLegajo(legajo);
        this.setApellido(apellido);
        this.setDni(dni);
        name = nombre;
        this.setPhoneNumber(apellido);
        this.setPhoneType(legajo);

    }

    public void setDni(final Integer dni) {
        this.dni = dni;
    }

    public Integer getDni() {
        return dni;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public void setLegajo(final String legajo) {
        this.legajo = legajo;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhoneType(final String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
