package modelos;

import org.bson.Document;

public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contraseña;
    private String rol; // Ejemplo: "Admin", "Usuario"

    // Constructor
    public Usuario(String id, String nombre, String correo, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Document toDocument() {
        return new Document("id", id)
                .append("nombre", nombre)
                .append("correo", correo)
                .append("contraseña", contraseña)
                .append("rol", rol);}
    
    
}