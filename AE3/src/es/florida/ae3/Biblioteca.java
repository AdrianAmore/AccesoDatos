package es.florida.ae3;

import java.util.Scanner;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Biblioteca {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("---OPCIONS---");
		System.out.println("1. Mostrar tots els títols de la biblioteca");
		System.out.println("2. Mostrar informació detallada d’un llibre");
		System.out.println("3. Crear un nou llibre");
		System.out.println("4. Actualizar llibre");
		System.out.println("5. Borrar llibre");
		System.out.println("6. Tanca la biblioteca");
		System.out.println("------------");
		System.out.print("Introdueix la opció a triar: ");
		int op = sc.nextInt();

		switch (op) {
		case 1:

			break;
		case 2:
			break;
		case 3:
			System.out.print("Identificador: ");
			int id = sc.nextInt();
			System.out.print("Titol: ");
			String titl = sc.next();
			System.out.print("Autor: ");
			String aut = sc.next();
			System.out.print("Any de publicació: ");
			int any = sc.nextInt();
			System.out.print("Editorial: ");
			String edit = sc.next();
			System.out.print("Numero de pàgines: ");
			int nump = sc.nextInt();
			Llibre llibre = new Llibre(id, titl, aut, any, edit, nump);
			crearLlibre(llibre);
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		default:
			System.err.println("No es pot triar eixa opció!");
		}
	}

	static int crearLlibre(Llibre llibre) {
		try {
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build;
			build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();
			Element raiz = doc.createElement("Llibres");
			doc.appendChild(raiz);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return llibre.identificador;
	}

}
