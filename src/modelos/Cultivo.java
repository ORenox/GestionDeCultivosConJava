package modelos;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Cultivo {
    private String nombre;
    private String sector;
    private float area;
    private String fechaSiembra;
    private String fechaCosecha;
    private String id;

    public Cultivo(String nombre, String sector, float area, String fechaSiembra, String fechaCosecha, String id) {
        this.nombre = nombre;
        this.sector = sector;
        this.area = area;
        this.fechaSiembra = fechaSiembra;
        this.fechaCosecha = fechaCosecha;
        this.id = id;
    }
    
    
    
    public Cultivo(String nombre, String sector, float area, String fechaSiembra, String fechaCosecha) {
        MongoCollection<Document> usuario = MongoDBConexionSingleton.getCollection("usuarioLogeado");
        Document usuarioLogeado = usuario.find().first();
    
        String id = usuarioLogeado.getString("correo");
        
        this.nombre = nombre;
        this.sector = sector;
        this.area = area;
        this.fechaSiembra = fechaSiembra;
        this.fechaCosecha = fechaCosecha;
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String nombre) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(String fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public String getFechaCosecha() {
        return fechaCosecha;
    }

    public void setFechaCosecha(String fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }

    public Document toDocument() {
        return new Document("nombre", nombre)
                .append("sector", sector)
                .append("area", area)
                .append("fechaSiembra", fechaSiembra)
                .append("fechaCosecha", fechaCosecha)
                .append("id", id);
    }
}
