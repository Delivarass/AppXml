package Xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class Firma {

	public static void main(String[] args) throws IOException {
		KeyPair claus;
		claus = randomGenerate(2048);
		PrivateKey privada = claus.getPrivate();
		PublicKey publica = claus.getPublic();

		byte[] privadaBytes = Files.readAllBytes(Paths.get("F:\\DAM2\\claus.txt"));
		byte[] publicaBytes = publica.getEncoded();

		// Crear firma
		byte[] firma = signData(privadaBytes, privada);
		// System.out.println("La firma és: " + new String(firma));

		Files.write(Paths.get("F:\\DAM2\\M09\\claupublica.txt"), claus.getPublic().getEncoded());

		// Validar firma
		boolean confirmarFirma = validateSignature(privadaBytes, firma, publica);
		if (confirmarFirma == true) {
			System.out.println("La firma és correcta");
		} else {
			System.out.println("La firma és incorrecta");
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
			Files.write(Paths.get("F:\\DAM2\\M09\\firma.txt"), signature);

			boolean Valid = signer.verify(signature);
			isValid = true;
		} catch (Exception ex) {
			System.err.println("Error validant les dades: " + ex);
		}
		return isValid;
	}

	/*
	 * public static void main(String[] args){ try {
	 * 
	 * KeyPair claus; claus = randomGenerate(1024); PrivateKey privada =
	 * claus.getPrivate(); PublicKey publica = claus.getPublic();
	 * 
	 * Signature signer = Signature.getInstance("SHA1withRSA");
	 * signer.initSign(privada);
	 * 
	 * byte[] bytes = Files.readAllBytes(Paths.get("F:\\DAM2\\claus.txt"));
	 * signer.update(bytes); byte[] firmaDigital = signer.sign();
	 * 
	 * Files.write(Paths.get("F:\\DAM2\\M09\\firma.txt"), firmaDigital);
	 * Files.write(Paths.get("F:\\DAM2\\M09\\claupublica.txt"),
	 * claus.getPublic().getEncoded());
	 * 
	 * byte[] privadaBytes = privada.getEncoded();
	 * 
	 * boolean confirmarFirma = validateSignature(privadaBytes, firmaDigital,
	 * claus.getPublic()); if(confirmarFirma == true) {
	 * System.out.println("Dades confirmades");
	 * 
	 * } else { System.out.println("Error a validar dades"); }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * public static KeyPair randomGenerate(int len) { KeyPair keys = null; try {
	 * KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	 * keyGen.initialize(len); keys = keyGen.genKeyPair(); } catch (Exception ex) {
	 * System.err.println("Generador no disponible."); } return keys; }
	 * 
	 * public static boolean validateSignature(byte[] data, byte[] signature,
	 * PublicKey pub) { boolean isValid = false; try { Signature signer =
	 * Signature.getInstance("SHA1withRSA"); signer.initVerify(pub);
	 * signer.update(data); isValid = signer.verify(signature); } catch (Exception
	 * ex) { System.err.println("Error validant les dades: " + ex); } return
	 * isValid; }
	 */

}
