package es.florida.AE02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Modelo {
	private String original;
	private String modified;
	private String ruta = "AE02_T1_2_Streams_Groucho.txt"; 
	private String rutaNou = "AE02_T1_2_Streams_Groucho_Nou.txt";
	Modelo() {
		this.original = cargaTextoOriginal(ruta);
		this.modified = "";
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

	public String remplazar(String p, String r) {
		creaFitxer(rutaNou);
		File fileToBeModified = new File(ruta);

		String oldContent = "";

		BufferedReader reader = null;

		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			String line = reader.readLine();

			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();

				line = reader.readLine();
			}

			String newContent = oldContent.replaceAll(p, r);
			writer = new FileWriter(rutaNou);

			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cargaTextoOriginal(rutaNou);
	}
	
	public static void creaFitxer(String path) {

		try {
			String dir = path;
			File f1 = new File(dir);
			f1.createNewFile();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	

}
