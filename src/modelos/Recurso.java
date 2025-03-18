package modelos;

public class Recurso {
    private String id;
    private String tipo;
    private int cantidad;
    private String fechaAdquisicion;

    // Constructor
    public Recurso(String id, String tipo, int cantidad, String fechaAdquisicion) {
        this.id = id;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getFechaAdquisicion() { return fechaAdquisicion; }
    public void setFechaAdquisicion(String fechaAdquisicion) { this.fechaAdquisicion = fechaAdquisicion; }
}
