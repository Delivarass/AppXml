package Xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CrearPdf {


        public static final Date date = new Date();
        public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        public static final String timeStamp = formatter.format(date);
        private static final String arxiu = "/var/www/html/"+ timeStamp +"/arxiu"+ timeStamp +".xml";
        // PdfWriter writer = PdfWriter.getInstance(docPdf,new
        // FileOutputStream("F:\\DAM2\\Pdf XML\\" + idEnv + "-" + timeStamp + ".pdf"));
        public static void main(String[] args) throws DocumentException {

                // Instanciar el factory/creador
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                Document docPdf = new Document();
                try {


                        // Parse l'arxiu xml
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        org.w3c.dom.Document doc = db.parse(new File(arxiu));

                        // Obtenir pedido
                        NodeList llista = doc.getElementsByTagName("pedido");

                        for (int i = 0; i < llista.getLength(); i++) {
                                Node node = llista.item(i);

                                if (node.getNodeType() == Node.ELEMENT_NODE) {
                                        Element el = (Element) node;
                                        // Obtenir id de pedido
                                        String id = el.getAttribute("id");

                                        // Obtenir contingut
                                        NodeList id_enviament = el.getElementsByTagName("id_enviament");
                                        String idEnv = id_enviament.item(0).getTextContent();
                                        NodeList id_destinatari = el.getElementsByTagName("id_destinatari");
                                        String idDest = id_destinatari.item(0).getTextContent();
                                        NodeList id_treballador = el.getElementsByTagName("id_treballador");
                                        String idTreb = id_treballador.item(0).getTextContent();
                                        String direccio = el.getElementsByTagName("direccio").item(0).getTextContent();
                                        String pes = el.getElementsByTagName("pes").item(0).getTextContent();
                                        NodeList cp = el.getElementsByTagName("cp");
                                        String codiPostal = cp.item(0).getTextContent();
                                        String dataEnv = el.getElementsByTagName("dataEnv").item(0).getTextContent();
                                        String dataEnt = el.getElementsByTagName("dataEnt").item(0).getTextContent();
                                        String estat = el.getElementsByTagName("estat").item(0).getTextContent();
                                        //System.out.println(estat);

                                        if(estat.equals("Entregat")) {
                                                // Generar PDF
                                                PdfWriter writer = PdfWriter.getInstance(docPdf, new FileOutputStream("/var/www/html/PDF pedidos/"+ idEnv +"-"+ timeStamp +".pdf"));

                                                // obrir pdf
                                                docPdf.open();

                                                docPdf.add(new Paragraph("____________DELIVERASS_____________"));
                                                docPdf.add(new Paragraph("   "));
                                                docPdf.add(new Paragraph("ID enviament: " + idEnv));
                                                docPdf.add(new Paragraph("ID destinatari: " + idDest));
                                                docPdf.add(new Paragraph("ID treballador: " + idTreb));
                                                docPdf.add(new Paragraph("Direccio d'entrega: " + direccio));
                                                docPdf.add(new Paragraph("Codi postal: " + codiPostal));
                                                docPdf.add(new Paragraph("Pes: " + pes));
                                                docPdf.add(new Paragraph("Data Enviament: " + dataEnv));
                                                docPdf.add(new Paragraph("Data Entrega: " + dataEnt));
                                                docPdf.add(new Paragraph("____________________________________"));
                                                docPdf.add(new Paragraph("  "));

                                                docPdf.close();
                                                writer.close();

                                        }else if(estat.equals("No entregat")) {
                                                // Generar PDF
                                                PdfWriter writer = PdfWriter.getInstance(docPdf, new FileOutputStream("/var/www/html/PDF pedidos/NOENTREGAT "+ idEnv +"-"+ timeStamp +".pdf"));

                                                // obrir pdf
                                                docPdf.open();

                                                docPdf.add(new Paragraph("____________DELIVERASS_____________"));
                                                docPdf.add(new Paragraph("   "));
                                                docPdf.add(new Paragraph("ID enviament: " + idEnv));
                                                docPdf.add(new Paragraph("ID destinatari: " + idDest));
                                                docPdf.add(new Paragraph("ID treballador: " + idTreb));
                                                docPdf.add(new Paragraph("Direccio d'entrega: " + direccio));
                                                docPdf.add(new Paragraph("Codi postal: " + codiPostal));
                                                docPdf.add(new Paragraph("Pes: " + pes));
                                                docPdf.add(new Paragraph("Data Enviament: " + dataEnv));
                                                docPdf.add(new Paragraph("Data Entrega: " + dataEnt));
                                                docPdf.add(new Paragraph("____________________________________"));
                                                docPdf.add(new Paragraph("  "));

                                                // Tancar el pdf i el editor/escriptor
                                                docPdf.close();
                                                writer.close();
                                        }


        }

                }
                        
                // Borrar l'arxiu xml del dia despres de crear els pdf       
                File arxiuXML = new File("/var/www/html/"+ timeStamp +"/arxiu"+ timeStamp +".xml");
                arxiuXML.delete();

                } catch (ParserConfigurationException | SAXException | IOException e) {
                        e.printStackTrace();
                }

        }

}