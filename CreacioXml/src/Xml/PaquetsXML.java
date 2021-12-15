package Xml;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.text.SimpleDateFormat;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PaquetsXML {
        public static void main(String[] args) {
                CrearCarpeta();
                CrearXmlPaquets();
        }
        // Crear variable de data per crear arxius i carpetes dels pedidos del dia
        public static final Date date = new Date();
        public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        public static final String timeStamp = formatter.format(date);
        public static final String xmlFilePath = ("arxiu" + timeStamp + ".xml");

        public static void CrearXmlPaquets() {
                // COnnexio a la base de dades
                try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.20:5432/Deliverass",
                                "postgres", "Fat/3232")) {

                        // El query de la seleccio de dades
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM public.paquets");

                        // Creacio del document xml
                        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = df.newDocumentBuilder();
                        Document document = db.newDocument();

                        // Arrel
                        Element arrel = document.createElement("deliverass");
                        document.appendChild(arrel);

                        while (rs.next()) {
                        	 var estat1 = rs.getBoolean("estat");

                             if (estat1 == true) {
                                     String entregat = "Entregat";

                             // Si no esta entregat, crear el xml del pedido
                             } else {
                                     String entregat = "No entregat";

                                     // Pare
                                     Element pare = document.createElement("pedido");
                                     arrel.appendChild(pare);

                                     // Declarar id a l'element pare

                                     Attr id = document.createAttribute("id");
                                     id.setValue(rs.getString("id_enviament"));
                                     pare.setAttributeNode(id);

                                     // id_enviament/entrega
                                     Element idEnviament = document.createElement("id_enviament");
                                     idEnviament.appendChild(document.createTextNode(rs.getString("id_enviament")));
                                     pare.appendChild(idEnviament);

                                     // id_destinatari
                                     Element idDestinatari = document.createElement("id_destinatari");
                                     idDestinatari.appendChild(document.createTextNode(rs.getString("id_destinatari")));
                                     pare.appendChild(idDestinatari);

                                     // id_treballador
                                     Element idTreballador = document.createElement("id_treballador");
                                     idTreballador.appendChild(document.createTextNode(rs.getString("id_treballador")));
                                     pare.appendChild(idTreballador);

                                     // direccio
                                     Element dir = document.createElement("direccio");
                                     dir.appendChild(document.createTextNode(rs.getString("direccio")));
                                     pare.appendChild(dir);

                                     // latitud
                                     Element latitud = document.createElement("latitud");
                                     latitud.appendChild(document.createTextNode(rs.getString("latitud")));
                                     pare.appendChild(latitud);

                                     // longitud
                                     Element longitud = document.createElement("longitud");
                                     longitud.appendChild(document.createTextNode(rs.getString("longitud")));
                                     pare.appendChild(longitud);

                                     // pes
                                     Element pes = document.createElement("pes");
                                     pes.appendChild(document.createTextNode(rs.getString("pes")));
                                     pare.appendChild(pes);

                                     // cp
                                     Element cp = document.createElement("cp");
                                     cp.appendChild(document.createTextNode(rs.getString("cp")));
                                     pare.appendChild(cp);

                                     // data enviament
                                     Element dataEnv = document.createElement("dataEnv");
                                     dataEnv.appendChild(document.createTextNode(rs.getString("data")));
                                     pare.appendChild(dataEnv);

                                     // uid Treaballador
                                     Element uuid = document.createElement("uuid");
                                     uuid.appendChild(document.createTextNode(rs.getString("uuid")));
                                     pare.appendChild(uuid);

                                     // estat
                                     Element estat = document.createElement("estat");
                                     estat.appendChild(document.createTextNode(entregat));
                                     pare.appendChild(estat);

                                     // data entrega
                                     Element dataEnt = document.createElement("dataEnt");
                                     dataEnt.appendChild(document.createTextNode(rs.getString("data_entrega")));
                                     pare.appendChild(dataEnt);

                                     // crear l'arxiu XML
                                     // transformar l'objecte DOM a XML
                             }

                     }

                     // Transformacio de les dades i document
                     TransformerFactory tf = TransformerFactory.newInstance();
                     Transformer t = tf.newTransformer();
                     DOMSource ds = new DOMSource(document);
                     StreamResult sr = new StreamResult(new File("/var/www/html/" +  timeStamp, xmlFilePath));

                     t.transform(ds, sr);

                } catch (ParserConfigurationException pce) {
                    pce.printStackTrace();
            } catch (TransformerException te) {
                    te.printStackTrace();
            } catch (SQLException e) {
                    System.out.println("Error de connexio.");
                    e.printStackTrace();
            }
    }

    public static void CrearCarpeta() {
            File carpeta;

            carpeta = new File("carpetaExercici");
            carpeta.mkdir();
    }
}