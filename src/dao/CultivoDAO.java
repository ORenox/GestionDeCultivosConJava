package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import java.text.SimpleDateFormat;
import modelos.Cultivo;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelos.MongoDBConexionSingleton;
import org.bson.conversions.Bson;

public class CultivoDAO {
    private final MongoCollection<Document> coleccionCultivo;
    private final MongoCollection<Document> coleccionLogeado;
    private final MongoCollection<Document> recursosAgua;

    public CultivoDAO() {
        this.coleccionCultivo = MongoDBConexionSingleton.getCollection("cultivos");
        this.coleccionLogeado = MongoDBConexionSingleton.getCollection("usuarioLogeado");
        this.recursosAgua = MongoDBConexionSingleton.getCollection("consumoAgua");
        }
    
    public void guardarConsumo(Document nuevoConsumo){
        recursosAgua.insertOne(nuevoConsumo);
    }
    public FindIterable<Document>  getConsumoPorFecha(Date fecha){
        Document usuarioLogeado = coleccionLogeado.find().first();
        String correo = usuarioLogeado.getString("correo");
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date inicioDia = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date finDia = cal.getTime();

        Bson filtro = Filters.and(
            Filters.eq("id", correo),
            Filters.gte("fecha", inicioDia),
            Filters.lt("fecha", finDia) // Para incluir todo el día
        ); 
        return recursosAgua.find(filtro);
        
    }
    public int verConsumoTotal(DefaultTableModel mt, Date fecha){
        Document usuarioLogeado = coleccionLogeado.find().first();
        String correo = usuarioLogeado.getString("correo");
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date inicioDia = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date finDia = cal.getTime();

        Bson filtro = Filters.and(
            Filters.eq("id", correo),
            Filters.gte("fecha", inicioDia),
            Filters.lt("fecha", finDia) // Para incluir todo el día
        ); 
        
       
        int consumoNumerico = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Document recurso : recursosAgua.find(filtro)) {
                    Date fechaBD = recurso.getDate("fecha");
                    String fechaBDStr = sdf.format(fechaBD); 
                    consumoNumerico += Integer.parseInt(recurso.getString("consumo"));
                        mt.addRow(new Object[]{
                                recurso.getString("consumo"), // Cantidad de agua consumida
                                recurso.getString("sector"),
                                fechaBDStr
                                
                        });
                    
            
        }
       
        return consumoNumerico;
        
    }
    
    
    //función para agregar cultivos y subirlos a la base de datos
    public void agregarCultivo(Cultivo cultivo) throws Exception {
        
        Document usuarioLogeado = coleccionLogeado.find().first();
        String id = usuarioLogeado.getString("correo");
        
        // Crear el documento para MongoDB
        Document documentoUsuario = new Document()
                .append("nombre", cultivo.getNombre())
                .append("sector", cultivo.getSector())
                .append("area", cultivo.getArea())
                .append("fechaSiembra", cultivo.getFechaSiembra())
                .append("fechaCosecha", cultivo.getFechaCosecha())
                .append("id", id);

        // Insertar el documento en la colección
        coleccionCultivo.insertOne(documentoUsuario);
        
        System.out.println("Cultivo registrado con éxito en MongoDB.");
    }
    

   
    // Obtener todos los cultivos
    public List<Cultivo> obtenerCultivos() {
        Document usuarioLogeado = coleccionLogeado.find().first();
        String correo = usuarioLogeado.getString("correo");
        
        Bson filtro = Filters.eq("id",correo);
        List<Cultivo> listaCultivos = new ArrayList<>();
        
        for (Document doc : coleccionCultivo.find(filtro)) {
            Cultivo cultivo = new Cultivo(
                doc.getString("nombre"),
                doc.getString("sector"),
                doc.getDouble("area").intValue(),
                doc.getString("fechaSiembra"),
                doc.getString("fechaCosecha"),
                doc.getString("id")
            );
            listaCultivos.add(cultivo);
        }
        return listaCultivos;
    }
    
    // Obtener un cultivo por ID
    public Cultivo obtenerPorId(String id) {
        try {
            Document doc = coleccionCultivo.find(new Document("id", id)).first();
            if (doc != null) {
                return convertirDocumentoACultivo(doc);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener cultivo por ID: " + e.getMessage());
        }
        return null;
    }

    // Actualizar un cultivo
    public void editarCultivo(Cultivo cultivo, String nombreOriginal) throws Exception {
        Document filtro = new Document("nombre", nombreOriginal);
        // Crear el documento con los nuevos datos
        Document nuevosDatos = new Document("$set", new Document()
            .append("nombre", cultivo.getNombre())
            .append("sector", cultivo.getSector())
            .append("area", cultivo.getArea())
            .append("fechaSiembra", cultivo.getFechaSiembra())
            .append("fechaCosecha", cultivo.getFechaCosecha()));

        UpdateResult resultado = coleccionCultivo.updateOne(filtro, nuevosDatos);
        if (resultado.getMatchedCount() > 0) {
            System.out.println("Cultivo actualizado correctamente.");
        } else {
            throw new Exception("No se encontró el cultivo en la base de datos.");
        }
    }
    
    // Eliminar un cultivo
    public boolean eliminarCultivo(String nombre, String sector) {
        Document filtro = new Document("nombre", nombre)
                            .append("sector", sector);
        try {
            coleccionCultivo.deleteOne(filtro);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Método auxiliar para convertir un Document a un objeto Cultivo
    private Cultivo convertirDocumentoACultivo(Document doc) {
        return new Cultivo(
                doc.getString("nombre"),
                doc.getString("sector"),
                doc.containsKey("area") ? doc.getDouble("area").floatValue() : 0.0f,
                doc.getString("fechaSiembra"),
                doc.getString("fechaCosecha"),
                doc.getString("id")
        );
    }
    
    
    
    
}
