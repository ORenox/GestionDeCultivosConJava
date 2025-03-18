package controladores;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import java.util.List;
import modelos.Cultivo;
import dao.CultivoDAO;
import dao.UsuariosDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.MongoDBConexionSingleton;
import org.bson.Document;
import org.bson.conversions.Bson;

public class CultivosController {
    
    private final CultivoDAO cultivoDao;
    private final UsuariosDAO usuariosDao;

    public CultivosController() {
        this.cultivoDao = new CultivoDAO();
        this.usuariosDao = new UsuariosDAO();
    }
    
    //metodos para los consumos
    
    public int verConsumoTotal(DefaultTableModel mt, Date fecha){
        return cultivoDao.verConsumoTotal(mt,fecha);
    }
    
    public FindIterable<Document> getConsumoPorFecha(Date fecha){
        return cultivoDao.getConsumoPorFecha(fecha);
        
    }
    
    public void guardarConsumo( String cantidad, String sector, Date fecha){
       
        
        String idUsuario = usuariosDao.obtenerUsuarioLog();
         Document nuevoConsumo = new Document("consumo", cantidad)
            .append("sector", sector)
            .append("fecha",fecha)
            .append("id",idUsuario);
         cultivoDao.guardarConsumo(nuevoConsumo);

    }
    
    
    public void eliminarElemento(JTable tabla) throws Exception {
        
        MongoCollection<Document> usuarioCollection = MongoDBConexionSingleton.getCollection("usuarioLogeado");
        Document usuarioLogeado = usuarioCollection.find().first();
        String correo = usuarioLogeado.getString("correo");

        MongoCollection<Document> recursosAgua = MongoDBConexionSingleton.getCollection("consumoAgua");

        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            throw new Exception("Debe seleccionar un consumo de agua para eliminar.");
        }

        // Obtener los valores de la fila seleccionada
        String consumo = tabla.getValueAt(filaSeleccionada, 0).toString(); // Cantidad de agua
        String sector = tabla.getValueAt(filaSeleccionada, 1).toString();  // Sector
        String fecha = tabla.getValueAt(filaSeleccionada, 2).toString();   // Fecha

        // Crear el filtro para eliminar en MongoDB
        Document filtro = new Document("id", correo) // Asegurar que pertenece al usuario
                                 .append("consumo", consumo)
                                 .append("sector", sector);

        // Intentar eliminar el documento
        DeleteResult resultado = recursosAgua.deleteOne(filtro);

        if (resultado.getDeletedCount() > 0) {
            System.out.println("Registro de consumo eliminado correctamente.");
            // Eliminar la fila de la tabla
            ((DefaultTableModel) tabla.getModel()).removeRow(filaSeleccionada);
        } else {
            throw new Exception("No se encontró el registro en la base de datos.");
        }
    }
    
    //--------------------------------------------------------------------------------
    
    public void editarCultivo(Cultivo cultivo, String nombreOriginal) throws Exception {
        if (cultivo == null) {
            throw new Exception("El cultivo no tiene todos los datos requeridos.");
        }
        cultivoDao.editarCultivo(cultivo,nombreOriginal);
    }



    public List<Cultivo> obtenerCultivos() {//este ya esta muy bien implementado
        
        return cultivoDao.obtenerCultivos();
    }
    
    public void eliminarCultivo(JTable tabla) throws Exception {
    int filaSeleccionada = tabla.getSelectedRow();
    if (filaSeleccionada == -1) {
        throw new Exception("Debe seleccionar un cultivo para eliminar.");
    }
    // Obtener los valores de la fila seleccionada
    String nombre = tabla.getValueAt(filaSeleccionada, 0).toString();
    String sector = tabla.getValueAt(filaSeleccionada, 1).toString();

    if (cultivoDao.eliminarCultivo(nombre,sector)) {
        System.out.println("Cultivo eliminado correctamente.");
        // actualizar la tabla
        ((DefaultTableModel) tabla.getModel()).removeRow(filaSeleccionada);
    } else {
        throw new Exception("No se encontró el cultivo en la base de datos.");
    }
    
    
}
    public boolean guardarCultivo( String nombre, String sector, String area, String fechaSiembra, String fechaCosecha) throws Exception{
        
        
        if (!fechaSiembra.matches("\\d{4}-\\d{2}-\\d{2}") || !fechaCosecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new Exception("Las fechas deben tener el formato YYYY-MM-DD.");
        }
        try {
            
            float areaFloat;
            try {
                areaFloat = Float.parseFloat(area);
                 if (areaFloat <= 0) {
                    throw new Exception("El área debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                throw new Exception("El área debe ser un número válido.");
            }
            Cultivo cultivo = new Cultivo(nombre, sector, Float.parseFloat(area),fechaSiembra,fechaCosecha);

            cultivoDao.agregarCultivo(cultivo);

            JOptionPane.showMessageDialog(null, "Cultivo registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
    }
    
    
    


    
    

    
   
}
