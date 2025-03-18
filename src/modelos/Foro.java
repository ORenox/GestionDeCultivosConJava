package modelos;

public class Foro {
    private String id;
    private String tema;
    private String contenido;
    private String fecha;

    // Constructor
    public Foro(String id, String tema, String contenido, String fecha) {
        this.id = id;
        this.tema = tema;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
