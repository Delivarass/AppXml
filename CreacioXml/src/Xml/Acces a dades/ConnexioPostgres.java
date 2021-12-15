package Xml;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class ConnexioPostgres {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.2.20:5432/Deliverass", "postgres", "Fat/3232")) {
 
            System.out.println("Connectat a la base de dades de Deliverass.");
            Statement statement = connection.createStatement();
            System.out.println("Llegint taula clients...");
            System.out.println("");
            
            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM public.clients");
            ResultSet rs 		= statement.executeQuery("SELECT * FROM public.repartidors");
            while (resultSet1.next() && rs.next()) {
                System.out.println("ID Client: " + resultSet1.getString("id_client") + "   Nom: " + resultSet1.getString("nom") + "   DNI: " + resultSet1.getString("dni") + "   Direccio: " + resultSet1.getString("direccio")  + "   CP: " + resultSet1.getString("cp"));
            }
            System.out.println("");
        } catch (SQLException e) {
            System.out.println("Error de connexió.");
            e.printStackTrace();
        }
        
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.2.96:5432/Deliverass", "postgres", "Fat/3232")) {
        	 
            Statement statement = connection.createStatement();
            
            ResultSet resultSet2 = statement.executeQuery("SELECT * FROM public.repartidors");
            
            while (resultSet2.next()) {
                System.out.println("ID Treballador: " + resultSet2.getString("id_treballador") + "   Nom: " + resultSet2.getString("nom") + "   Contrasenya: " + resultSet2.getString("contrasenya"));
            }
            System.out.println("");
        } catch (SQLException e) {
            System.out.println("Error de connexió.");
            e.printStackTrace();
        }
        
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.2.96:5432/Deliverass", "postgres", "Fat/3232")) {
        	 
            Statement statement = connection.createStatement();
           
            ResultSet resultSet3 = statement.executeQuery("SELECT * FROM public.paquets");
            
            while (resultSet3.next()) {
            	var estat1 = resultSet3.getBoolean("estat");
            	if(estat1 == true) {
            		String entregat = "Entregat";
            		System.out.println("ID Entrega: " + resultSet3.getString("id_enviament") + "   ID Destinatari: " + resultSet3.getString("id_destinatari") + "   ID Treballador: " + resultSet3.getString("id_treballador") + "   Latitud: " + resultSet3.getString("latitud") + "   Longitud: " + resultSet3.getString("longitud") + "   Pes: " + resultSet3.getString("pes") + "   CP: " + resultSet3.getString("cp") + "   Data: " + resultSet3.getString("data") + "   Estat: " + entregat + "   Data Entrega: " + resultSet3.getString("data_entrega"));
            	}else {
            		String entregat = "No entregat";
            		System.out.println("ID Entrega: " + resultSet3.getString("id_enviament") + "   ID Destinatari: " + resultSet3.getString("id_destinatari") + "   ID Treballador: " + resultSet3.getString("id_treballador") + "   Latitud: " + resultSet3.getString("latitud") + "   Longitud: " + resultSet3.getString("longitud") + "   Pes: " + resultSet3.getString("pes") + "   CP: " + resultSet3.getString("cp") + "   Data: " + resultSet3.getString("data") + "   Estat: " + entregat + "   Data Entrega: " + resultSet3.getString("data_entrega"));
            	}      
            }
            System.out.println("");
        } catch (SQLException e) {
            System.out.println("Error de connexió.");
            e.printStackTrace();
        }
    }
}