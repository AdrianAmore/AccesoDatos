package es.florida.ae3;

import java.util.ArrayList;
import java.util.Scanner;
import org.w3c.dom.*;



import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.*;

public class Biblioteca {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		int op = 0;
		System.out.println("---OPCIONS---");
		System.out.println("1. Mostrar tots els títols de la biblioteca");
		System.out.println("2. Mostrar informació detallada d’un llibre");
		System.out.println("3. Crear un nou llibre");
		System.out.println("4. Actualizar llibre");
		System.out.println("5. Borrar llibre");
		System.out.println("6. Tanca la biblioteca");
		System.out.println("------------");
		do {

			System.out.println("\n");
			System.out.print("Introdueix la opció a triar: ");
			op = sc.nextInt();
			switch (op) {
			case 1:
				ArrayList<Llibre> llibres = recuperarTots();
				System.err.println("---\tLlibres actuals de la biblioteca\t---");
				for (Llibre l : llibres) {
					System.out.println(l.getTitol() + " || Id: " + l.getIdentificador());
				}
				break;
			case 2:
				System.out.print("Introdueix el index del llibre a mostrar: ");
				mostrarLlibre(recuperarLlibre(sc.nextInt()));
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
				System.out.print("Introdueix el index del llibre a borrar: ");
				borrarLlibre(sc.nextInt());
				break;
			case 6:
				System.out.println("Biblioteca tancada, fins la proxima.");
				break;
			default:
				System.err.println("No es pot triar eixa opció!");
			}
		} while (op != 6);
		sc.close();
	}

	static int crearLlibre(Llibre llibre) {
	    Document dom;
	    Element e = null;
	    String xml = "prueba.xml";

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement("llibres");
	        Element l = dom.createElement("llibre");
	        rootEle.appendChild(l);
	        l.setAttribute("id",Integer.toString(llibre.getIdentificador()));
	        
	        

	        // create data elements and place them under root
	        e = dom.createElement("titol");
	        e.appendChild(dom.createTextNode(llibre.getTitol()));
	        l.appendChild(e);
	        
	        e = dom.createElement("autor");
	        e.appendChild(dom.createTextNode(llibre.getAutor()));
	        l.appendChild(e);



	        e = dom.createElement("any-publi");
	        e.appendChild(dom.createTextNode(Integer.toString(llibre.getAnyPubli())));
	        l.appendChild(e);

	        e = dom.createElement("editorial");
	        e.appendChild(dom.createTextNode(llibre.getEditorial()));
	        l.appendChild(e);
	        
	        e = dom.createElement("n-pag");
	        e.appendChild(dom.createTextNode(Integer.toString(llibre.getNumPag())));
	        l.appendChild(e);

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
		return 1;
	}

	static void mostrarLlibre(Llibre llibre) {
		System.out.println("");
		System.out.println("Id: " + llibre.getIdentificador());
		System.out.println("Titol: " + llibre.getTitol());
		System.out.println("Autor: " + llibre.getAutor());
		System.out.println("Any de publicació: " + llibre.getAnyPubli());
		System.out.println("Editorial: " + llibre.getEditorial());
		System.out.println("Numero de pàgines: " + llibre.getNumPag());
	}

	static Llibre recuperarLlibre(int identificador) {
		Llibre libro = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("biblioteca.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("llibre");
			Node node = nodeList.item(identificador);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				String id = eElement.getAttribute("id");
				String titulo = eElement.getElementsByTagName("titol").item(0).getTextContent();
				String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
				String any = eElement.getElementsByTagName("any-publi").item(0).getTextContent();
				String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
				String nPag = eElement.getElementsByTagName("n-pag").item(0).getTextContent();
				libro = new Llibre(Integer.parseInt(id), titulo, autor, Integer.parseInt(any), editorial,
						Integer.parseInt(nPag));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libro;

	}

	static void borrarLlibre(int identificador) {

	}

	static ArrayList<Llibre> recuperarTots() {

		ArrayList<Llibre> llibres = new ArrayList<Llibre>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("biblioteca.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("llibre");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					String id = eElement.getAttribute("id");
					String titulo = eElement.getElementsByTagName("titol").item(0).getTextContent();
					String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
					String any = eElement.getElementsByTagName("any-publi").item(0).getTextContent();
					String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
					String nPag = eElement.getElementsByTagName("n-pag").item(0).getTextContent();
					Llibre libro = new Llibre(Integer.parseInt(id), titulo, autor, Integer.parseInt(any), editorial,
							Integer.parseInt(nPag));
					llibres.add(libro);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return llibres;

	}

}
