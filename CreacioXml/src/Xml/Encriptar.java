package Xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encriptar {
        public static final Date date = new Date();
        public static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        public static final String timeStamp = formatter.format(date);
        public static final String xmlFilePath = ("arxiu"+timeStamp+".xml");


        public static void main(String[] args) {
                // Creacio de key i arxius
                SecretKey key;
                key = keygenKeyGeneration(256);
                File arxiuEntrada = new File("/home/servidor/backup_postgres/deliverass_backup_"+timeStamp+".sql");
                File arxiuEncrypted= new File("/home/servidor/backup_postgres/Encriptacio/encrypted-text-"+timeStamp+".txt");
                File arxiuDecrypted= new File("/home/servidor/backup_postgres/Decriptacio/decrypted-text_deliverass_backup_"+timeStamp+".txt");

                try {
                        encriptacioIdecriptacio(Cipher.ENCRYPT_MODE, key, arxiuEntrada, arxiuEncrypted);
                        encriptacioIdecriptacio(Cipher.DECRYPT_MODE, key, arxiuEncrypted, arxiuDecrypted);
                        System.out.println("S'ha pogut encriptar i decriptar correctament!");
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                }
        }

        public static void encriptacioIdecriptacio(int cipherMode, SecretKey key, File arxiuEntrada, File arxiuSortida) {
                try {
                        // Xifrar en mode AES
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(cipherMode, key);

                        // Obtencio de dades de l'arxiu
                        FileInputStream is = new FileInputStream(arxiuEntrada);
                        byte[] bytes = new byte[(int) arxiuEntrada.length()];
                        is.read(bytes);

                        byte[] os = cipher.doFinal(bytes);

                        FileOutputStream outputStream = new FileOutputStream(arxiuSortida);
                        outputStream.write(os);

                        is.close();
                        outputStream.close();

                } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
                                | IllegalBlockSizeException | IOException e) {
                        e.printStackTrace();
                }
        }

        public static SecretKey keygenKeyGeneration(int keySize) {
                SecretKey sKey = null;
                if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
                        try {
                                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                                kgen.init(keySize);
                                sKey = kgen.generateKey();

                        } catch (NoSuchAlgorithmException ex) {
                                System.err.println("Generador no disponible.");
                        }
                }
                return sKey;
        }
}
