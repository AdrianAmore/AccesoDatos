package es.florida.AE02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Modelo {
	private String original;
	private String modified;
	private String ruta = "C:\\Users\\adampe\\Desktop\\Acceso_Datos\\Activitat AE02_T1_2_Streams\\AE02_T1_2_Streams_Groucho.txt";

	Modelo() {
		this.original = cargaTextoOriginal(ruta);
	}

	public String getOriginal() {
		return this.original;
	}

	public String getModified() {
		return this.modified;
	}

	public String cargaTextoOriginal(String path) {
		String data = "";
		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				data += myReader.nextLine() + "\n";
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return data;
	}

	public String buscar(String palabra) {
		int contador = 0;
		String word = palabra;
		try {
			Scanner file = new Scanner(new File(ruta));

			while (file.hasNextLine()) {
				String line = file.nextLine();
				if (line.indexOf(word) != -1) {
					contador++;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return "La paraula ha sigut trobada " + contador + " vegades";
	}
	
	public void remplazar(String p, String r) {
		File fichMod = new File(ruta);
		String orig = cargaTextoOriginal(ruta);
		String nuevo = 
		
	}
	
}
