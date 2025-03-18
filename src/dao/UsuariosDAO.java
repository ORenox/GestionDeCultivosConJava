package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import modelos.Usuario;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.MongoDBConexionSingleton;

public class UsuariosDAO {
    private final MongoCollection<Document> coleccionUsuarios;
    private final MongoCollection<Document> coleccionLogeado;
    

    // Constructor para inicializar la colección
    public UsuariosDAO() {
        this.coleccionUsuarios = MongoDBConexionSingleton.getCollection("usuarios");
        this.coleccionLogeado = MongoDBConexionSingleton.getCollection("usuarioLogeado");
    }

    /**
     * Inserta un usuario en la colección.
     *
     * @param usuario El usuario a insertar.
     */
    
    
    public boolean validarCredenciales(String correo, String contraseña) {
        Document query = new Document("correo", correo).append("contraseña", contraseña);
        Document usuarioEncontrado = coleccionUsuarios.find(query).first();
        return usuarioEncontrado != null;
    }
    
    public String obtenerUsuarioLog(){
        Document usuarioLogeado = coleccionLogeado.find().first();
        String correo = usuarioLogeado.getString("correo");
        return correo;
    }
    public void usuarioLogeado(String correo) {

        Document usuarioEncontrado = coleccionUsuarios.find(Filters.eq("correo", correo)).first();
        if (usuarioEncontrado != null) {
            String nom = usuarioEncontrado.getString("nombre");
            String corr = usuarioEncontrado.getString("correo");

            Document usuario = new Document("nombre", nom)
                .append("correo", corr);

            coleccionLogeado.replaceOne(new Document(), usuario, new ReplaceOptions().upsert(true));
            System.out.println("Usuario Logeado");
        } else {
            System.out.println("Usuario no encontrado");
        }
    }

    /**
     * Obtiene todos los usuarios almacenados en la colección.
     *
     * @return Una lista de objetos de tipo Usuario.
     */
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (MongoCursor<Document> cursor = coleccionUsuarios.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Usuario usuario = new Usuario(
                        doc.getString("id"),
                        doc.getString("nombre"),
                        doc.getString("correo"),
                        doc.getString("contraseña"),
                        doc.getString("rol")
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
    
    public Document obtenerUsuario() {
    Document usuario = coleccionUsuarios.find().first();
    if (usuario == null) {
        System.out.println("No se encontró ningún usuario en la colección.");
    }
    return usuario;
}
    public Document obtenerUsuarioLogeado() {
    Document usuario = coleccionLogeado.find().first();
    if (usuario == null) {
        System.out.println("No se encontró ningún usuario en la colección.");
    }
    return usuario;
}
    
    public void agregarUsuario(Usuario usuario) throws Exception {
        if (usuario == null || usuario.getNombre().isEmpty() || usuario.getCorreo().isEmpty() || usuario.getContraseña().isEmpty()) {
            throw new Exception("El usuario no tiene todos los datos requeridos.");
        }
        if (!usuario.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
        throw new Exception("El correo no tiene un formato válido.");
        }
        

// Crear el documento para MongoDB
        Document documentoUsuario = new Document()
                .append("nombre", usuario.getNombre())
                .append("correo", usuario.getCorreo())
                .append("contraseña", usuario.getContraseña())
                .append("rol", usuario.getRol());

        // Insertar el documento en la colección
        coleccionUsuarios.insertOne(documentoUsuario);
        System.out.println("Usuario registrado con éxito en MongoDB.");
    }
    
    
    public boolean isCorreoExistente(String correo) {
        
    try{
        // Crear una consulta para buscar el correo
        Document query = new Document("correo", correo);
        Document usuarioEncontrado = coleccionUsuarios.find(query).first();
        return usuarioEncontrado != null; // Devuelve true si ya existe
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Error al verificar el correo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    }
    
    public boolean guardarUsuario(String nombre, String correo, String password, String rol){
    
        try {
            // Verificar si el correo ya está registrado
            if (isCorreoExistente(correo)) {
                JOptionPane.showMessageDialog(null, "El correo ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Salir del método si el correo ya existe
            }
            
            String id = ""; // MongoDB generará el ID automáticamente

            Usuario usuario = new Usuario(id, nombre, correo, password, rol);

            this.agregarUsuario(usuario);

            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
