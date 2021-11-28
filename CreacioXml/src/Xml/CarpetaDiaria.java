package Xml;

import java.io.File;

public class CarpetaDiaria {
	
	public static void CrearCarpeta() {
		File carpeta;
		
		carpeta = new File(File.listRoots()[1]+"carpetaExercici");
		carpeta.mkdir();
	}
}
