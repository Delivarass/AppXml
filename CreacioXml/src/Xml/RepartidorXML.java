package Xml;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RepartidorXML {
	
	public static final String xmlFilePath = "F:\\AppMobil\\Deliverass\\www\\xml\\repartidors.xml";

	public static void CrearXmlRepartidor(){
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.20:5432/Deliverass",
				"postgres", "Fat/3232")) {

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM public.repartidors");

			DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = df.newDocumentBuilder();
			Document document = db.newDocument();

			// Arrel
			Element arrel = document.createElement("deliverass");
			document.appendChild(arrel);
			
			while (rs.next()) {
					// Pare
					Element pare = document.createElement("repartidor");
					arrel.appendChild(pare);

					// Declarar id a l'element pare

					Attr id = document.createAttribute("id");
					id.setValue(rs.getString("id_treballador"));
					pare.setAttributeNode(id);

					// id_treballador
					Element id_treballador = document.createElement("id_treballador");
					id_treballador.appendChild(document.createTextNode(rs.getString("id_treballador")));
					pare.appendChild(id_treballador);

					// nom
					Element nom = document.createElement("nom");
					nom.appendChild(document.createTextNode(rs.getString("nom")));
					pare.appendChild(nom);

					// contrasenya
					Element contrasenya = document.createElement("contrasenya");
					contrasenya.appendChild(document.createTextNode(rs.getString("contrasenya")));
					pare.appendChild(contrasenya);
			}
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource ds = new DOMSource(document);
			StreamResult sr = new StreamResult(new File(xmlFilePath));

			t.transform(ds, sr);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error de connexió.");
			e.printStackTrace();
		}
	}
	
}
