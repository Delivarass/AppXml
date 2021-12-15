package Xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Firma {

        public static final Date date = new Date();
        public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        public static final String timeStamp = formatter.format(date);
        public static final String xmlFilePath = ("arxiu"+timeStamp+".xml");

        public static void main(String[] args) throws IOException {
                KeyPair claus;
                claus = randomGenerate(2048);
                PrivateKey privada = claus.getPrivate();
                PublicKey publica = claus.getPublic();

                Path path = Paths.get("/var/www/html/"+ timeStamp + "/arxiu"+timeStamp+".xml");
                byte[] privadaBytes = Files.readAllBytes(path);
                byte[] publicaBytes = publica.getEncoded();

                // Crear firma
                byte[] firma = signData(privadaBytes, privada);
                Files.write(Paths.get("/var/www/html/"+timeStamp+"/claupublica.txt"), claus.getPublic().getEncoded());

                // Validar firma
                boolean confirmarFirma = validateSignature(privadaBytes, firma, publica);
                if (confirmarFirma == true) {
                        System.out.println("La firma es correcta");
                } else {
                        System.out.println("La firma es incorrecta");
                }
        }

        public static KeyPair randomGenerate(int len) {
                KeyPair keys = null;
                try {
                        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                        keyGen.initialize(len);
                        keys = keyGen.genKeyPair();
                } catch (Exception ex) {
                    System.err.println("Generador no disponible.");
            }
            return keys;
    }

    public static byte[] signData(byte[] data, PrivateKey priv) {
            byte[] signature = null;
            try {
                    Signature signer = Signature.getInstance("SHA1withRSA");
                    signer.initSign(priv);
                    signer.update(data);
                    signature = signer.sign();
            } catch (Exception ex) {
                    System.err.println("Error signant les dades: " + ex);
            }
            return signature;
    }

    public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) {
            boolean isValid = false;
            try {
                    Signature signer = Signature.getInstance("SHA1withRSA");
                    signer.initVerify(pub);
                    signer.update(data);
                    Files.write(Paths.get("/var/www/html/"+timeStamp+"/firma.txt"), signature);

                    boolean Valid = signer.verify(signature);
                    isValid = true;
            } catch (Exception ex) {
                    System.err.println("Error validant les dades: " + ex);
            }
            return isValid;
    }

}
