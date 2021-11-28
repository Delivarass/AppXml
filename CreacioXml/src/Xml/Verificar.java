package Xml;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class Verificar {

	public static void main(String[] args) {
		try {

			byte[] clauPublica = Files.readAllBytes(Paths.get("F:\\DAM2\\M09\\claupublica.txt"));
			byte[] firmaDigitalFeta = Files.readAllBytes(Paths.get("F:\\DAM2\\M09\\firma.txt"));

			X509EncodedKeySpec clauPublicaSpec = new X509EncodedKeySpec(clauPublica);
			KeyFactory kf = KeyFactory.getInstance("DSA", "SUN");

			PublicKey pk = kf.generatePublic(clauPublicaSpec);
			Signature signer = Signature.getInstance("SHAwithDSA");
			signer.initVerify(pk);
			
			byte[] bytes = Files.readAllBytes(Paths.get("F:\\DAM2\\claus.txt"));
			signer.update(bytes);

			boolean verificat = signer.verify(firmaDigitalFeta);
			if (verificat) {
				System.out.println("Dades verificades");
			} else {
				System.out.println("No s'ha pogut verificar les dades");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
