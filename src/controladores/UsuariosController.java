package controladores;

import dao.UsuariosDAO;
import modelos.Usuario;
import org.bson.Document;

public class UsuariosController {
    private final UsuariosDAO usuarioDao;
    

    public UsuariosController() {
        this.usuarioDao = new UsuariosDAO();
    }
    
    public boolean validarCredenciales(String correo, String contraseña) {
        return usuarioDao.validarCredenciales(correo,contraseña);
    }
    // Método para registrar al usuario logeado
    public void usuarioLogeado(String correo) {
        usuarioDao.usuarioLogeado(correo);
    }
    
    //obtener el usuarioLogeado
    public Document obtenerUsuarioLogeado() {
        return usuarioDao.obtenerUsuarioLogeado();
    }
    
    public void agregarUsuario(Usuario usuario) throws Exception {
        usuarioDao.agregarUsuario(usuario);
    }
    
    public boolean isCorreoExistente(String correo) { 
        return usuarioDao.isCorreoExistente(correo);
    }
    
    public boolean guardarUsuario(String nombre, String correo, String password, String rol){
        return usuarioDao.guardarUsuario(nombre, correo, password, rol); 
    }
   
}
















