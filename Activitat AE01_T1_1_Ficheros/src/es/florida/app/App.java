package es.florida.app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Comparator;
import java.util.Scanner;

public class App {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("1. getInformacio");
		System.out.println("2. creaCarpeta");
		System.out.println("3. creaFitxer");
		System.out.println("4. elimina");
		System.out.println("5. renomena");
		System.out.print("Introdueix la funció que vols executar: ");
		int metodo = scan.nextInt();
		String sDirectorio = args[0];
		File f = new File(sDirectorio);
		switch (metodo) {

		case 1:
			getInformacio(f);
			break;
		case 2:
			creaCarpeta(f);
			break;
		case 3:
			creaFitxer(f);
			break;
		case 4:
			elimina(f);
			break;
		case 5:
			renomena(f);
			break;
		default:
			System.out.println("Opció no valida");
			break;

		}

	}

	public static void getInformacio(File f) {
		if (f.exists()) {
			try {
				System.out.println("El directori es: " + f.getName());
				if (f.isDirectory()) {
					System.out.println("Es un directori");
					System.out.println("Nº d'elements: " + f.listFiles().length);
					System.out.println("Espai lliure: " + humanReadableByteCountSI(f.getFreeSpace()));
					System.out.println("Espai disponible: " + humanReadableByteCountSI(f.getUsableSpace()));
					System.out.println("Espai total: " + humanReadableByteCountSI(f.getTotalSpace()));

				} else {
					System.out.println("Es un fitxer");
					System.out.println("La seua grandaria es: " + f.length() + " bytes");
				}
				System.out.println("La ubicació es: " + f.getPath());
				SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
				String fecha = myFormat.format(f.lastModified());
				System.out.println("Ultima data de modificació: " + fecha);
				if (f.isHidden()) {
					System.out.println("Es ocult");
				} else {
					System.out.println("No es ocult");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static void creaCarpeta(File f) {

		try {
			System.out.print("Introdueix el nom de la nova carpeta: ");
			String dir = f + "/" + scan.next();
			File f1 = new File(dir);
			if (f1.mkdirs()) {
				System.out.println("Carpeta creada correctament.");
			} else {
				System.out.println("Error");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void creaFitxer(File f) {

		try {
			System.out.print("Introdueix el nom del nou fitxer: ");
			String dir = f + "/" + scan.next();
			File f1 = new File(dir);
			if (f1.exists()) {
				if (f1.createNewFile()) {
					System.out.println("Fitxer creat correctament.");
				} else {
					System.out.println("Error");
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void elimina(File f) {
		try {
			System.out.print("Introdueix el nom del fitxer o carpeta per a eliminar: ");
			String dir = f + "/" + scan.next();
			File f1 = new File(dir);
			if (f1.exists()) {
				if (f1.listFiles() != null) {
					Files.walk(f1.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
					System.out.println("Eliminat correctament.");
				} else {
					if (f1.delete()) {
						System.out.println("Eliminat correctament.");
					} else {
						System.out.println("Error");
					}
				}
			} else {
				System.out.println("Error, el fitxer o carpeta no existeix");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void renomena(File f) {
		try {
			System.out.print("Introdueix el nom del fitxer o carpeta per a renomenar: ");
			String dir = f + "/" + scan.next();
			File f1 = new File(dir);
			if (f1.exists()) {
				System.out.print("Introdueix el nou nom: ");
				String nom = f + "/" + scan.next();
				File f2 = new File(nom);
				if (f1.renameTo(f2)) {
					System.out.println("Renomenat amb exit!");
				} else {
					System.out.println("Error");
				}
			} else {
				System.out.println("Error, el fitxer o carpeta no existeix");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Funciones que no tienen que ver con la actividad

	public static String humanReadableByteCountSI(long bytes) { // Función que convierte bytes a MB/GB etc... (De
																// internet), basicamente para dejar el output mas legible.
		if (-1000 < bytes && bytes < 1000) {
			return bytes + " B";
		}
		CharacterIterator ci = new StringCharacterIterator("kMGTPE");
		while (bytes <= -999_950 || bytes >= 999_950) {
			bytes /= 1000;
			ci.next();
		}
		return String.format("%.1f %cB", bytes / 1000.0, ci.current());
	}

}