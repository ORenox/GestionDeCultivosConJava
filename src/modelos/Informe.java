package modelos;

public class Informe {
    private String id;
    private String tipo;
    private String rangoFechas;

    // Constructor
    public Informe(String id, String tipo, String rangoFechas) {
        this.id = id;
        this.tipo = tipo;
        this.rangoFechas = rangoFechas;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getRangoFechas() { return rangoFechas; }
    public void setRangoFechas(String rangoFechas) { this.rangoFechas = rangoFechas; }
}
