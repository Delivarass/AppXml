package Xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encriptar {
	public static void main(String[] args) {
		// Creació de key i arxius
		SecretKey key;
		key = keygenKeyGeneration(256);
		File arxiuEntrada = new File("F:\\DAM2\\claus.txt");
		File arxiuEncrypted= new File("F:\\DAM2\\encrypted-text.txt");
		File arxiuDecrypted= new File("F:\\DAM2\\decrypted-text.txt");

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

			// Obtenció de dades de l'arxiu
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