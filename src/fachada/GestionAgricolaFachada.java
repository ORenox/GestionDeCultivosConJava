/*
 
esta clase fachada es la encargada de unificar las funciones de los dos controladores que tiene el programa
 */
package fachada;

import com.mongodb.client.FindIterable;

import controladores.CultivosController;
import controladores.UsuariosController;

import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Cultivo;
import modelos.Usuario;
import org.bson.Document;

/**
 *
 * @author 
 */
public class GestionAgricolaFachada {
    
    private CultivosController culcon;
    private UsuariosController usucon;
    
    public GestionAgricolaFachada(){
        this.culcon = new CultivosController();
        this.usucon = new UsuariosController();
    }
    
    //todos los metodos se mostraran simplificados para esta vista fachada
    
    //metodos para los cultivos
    
    //metodos para los consumos
    
    public int verConsumoTotal(DefaultTableModel mt, Date fecha){
        return culcon.verConsumoTotal(mt,fecha);
    }
    
    public FindIterable<Document> getConsumoPorFecha(Date fecha){
        return culcon.getConsumoPorFecha(fecha);  
    }
    
    public void guardarConsumo( String cantidad, String sector, Date fecha){
       
        culcon.guardarConsumo(cantidad,sector,fecha);
    }
    
    
    public void eliminarElemento(JTable tabla) throws Exception {
        
        culcon.eliminarElemento(tabla);
    }
    
    public void editarCultivo(Cultivo cultivo, String nombreOriginal) throws Exception {
        culcon.editarCultivo(cultivo, nombreOriginal);
    }



    public List<Cultivo> obtenerCultivos() {
        
        return culcon.obtenerCultivos();
    }
    
    public void eliminarCultivo(JTable tabla) throws Exception {
    culcon.eliminarCultivo(tabla); 
}
    public boolean guardarCultivo( String nombre, String sector, String area, String fechaSiembra, String fechaCosecha) throws Exception{
        return culcon.guardarCultivo(nombre, sector, area, fechaSiembra, fechaCosecha);
    }
    
    //--------------------Controlador de Usuarios
    public boolean validarCredenciales(String correo, String contraseña) {
        return usucon.validarCredenciales(correo, contraseña);
    }
    // Método para registrar al usuario logeado
    public void usuarioLogeado(String correo) {
        usucon.usuarioLogeado(correo);
    }
    
    //obtener el usuarioLogeado
    public Document obtenerUsuarioLogeado() {
        return usucon.obtenerUsuarioLogeado();
    }
    
    public void agregarUsuario(Usuario usuario) throws Exception {
        usucon.agregarUsuario(usuario);
    }
    
    public boolean isCorreoExistente(String correo) { 
        return usucon.isCorreoExistente(correo);
    }
    
    public boolean guardarUsuario(String nombre, String correo, String password, String rol){
        return usucon.guardarUsuario(nombre, correo, password, rol); 
    }
 }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

