/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author renat
 */


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConexionSingleton {
    
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    // Constructor privado para evitar la creación de instancias
    private MongoDBConexionSingleton() {}


    // Método para obtener la conexión a la base de datos
    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                // Establecer la conexión a MongoDB
                mongoClient = MongoClients.create("mongodb://localhost:27017"); 
                database = mongoClient.getDatabase("gestion_agricola");
            } catch (Exception e) {
                System.err.println("Error al conectar a la base de datos MongoDB: " + e.getMessage());
            }
        }
        return database;
    }
    // Obtiene una colección específica
    public static MongoCollection<Document> getCollection(String nombreColeccion) {
        if (database == null) {
            conectar();
        }
        return database.getCollection(nombreColeccion);
    }
    // Método para cerrar la conexión (opcional)
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
    
    // Inicializa la conexión con MongoDB
    public static void conectar() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017"); 
            database = mongoClient.getDatabase("gestion_agricola");
            System.out.println("Conexión a MongoDB establecida.");

            // Asegúrate de que la base y una colección inicial existan
            verificarOCrearColeccion("coleccion_inicial");
        }
    }
    // Verifica si la colección existe, si no, la crea
    private static void verificarOCrearColeccion(String nombreColeccion) {
        if (database.getCollection(nombreColeccion) == null) {
            System.out.println("La colección '" + nombreColeccion + "' no existe. Creándola...");
            MongoCollection<Document> coleccion = database.getCollection(nombreColeccion);
            coleccion.insertOne(new Document("mensaje", "Colección creada automáticamente"));
            System.out.println("Colección '" + nombreColeccion + "' creada.");
        }
        else{
            System.out.println("Ingreso a Colección '" + nombreColeccion + "' exitoso");
        }
    }
}











