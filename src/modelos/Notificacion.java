package modelos;

public class Notificacion {
    private String id;
    private String mensaje;
    private String fecha;

    // Constructor
    public Notificacion(String id, String mensaje, String fecha) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
