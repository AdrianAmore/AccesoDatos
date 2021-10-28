package es.florida.ae3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;

import org.w3c.dom.*;

import java.io.*;

public class Biblioteca {
	static Scanner sc = new Scanner(System.in);
	static ArrayList<Llibre> count = recuperarTots();

	public static void main(String[] args) throws InterruptedException {

		int op = 0;
		System.out.println("---OPCIONS---");
		System.out.println("1. Mostrar tots els t�tols de la biblioteca");
		System.out.println("2. Mostrar informaci� detallada d�un llibre");
		System.out.println("3. Crear un nou llibre");
		System.out.println("4. Actualizar llibre");
		System.out.println("5. Borrar llibre");
		System.out.println("6. Tanca la biblioteca");
		System.out.println("------------");
		do {

			System.out.println("\n");
			System.out.print("Introdueix la opci� a triar: ");
			op = sc.nextInt();
			switch (op) {
			case 1:
				ArrayList<Llibre> llibres = recuperarTots();
				System.err.println("---\tLlibres actuals de la biblioteca\t---");
				Thread.sleep(500);
				for (Llibre l : llibres) {
					System.out.println(l.getTitol() + " || Id: " + l.getIdentificador());
				}
				break;
			case 2:
				System.out.print("Introdueix el index del llibre a mostrar: ");
				mostrarLlibre(recuperarLlibre(sc.nextInt()));
				break;
			case 3:
				try {
					System.out.print("Titol: ");
					String titl = sc.next();
					System.out.print("Autor: ");
					String aut = sc.next();
					System.out.print("Any de publicaci�: ");
					int any = sc.nextInt();
					System.out.print("Editorial: ");
					String edit = sc.next();
					System.out.print("Numero de p�gines: ");
					int nump = sc.nextInt();
					int id = count.size();
					Llibre llibre = new Llibre(id, titl, aut, any, edit, nump);
					int ll = crearLlibre(llibre);
					Thread.sleep(100);
					System.out.println("Llibre creat amb exit amb index: " + ll);
				} catch (Exception e) {
					System.err.println("Error al crear el llibre!");
					e.printStackTrace();
				}

				break;
			case 4:
				System.out.print("Introdueix el index del llibre a editar: ");
				int id = sc.nextInt();
				try {
					actualitzaLlibre(id);
					Thread.sleep(100);
					System.out.println("Llibre editat amb exit!");
				} catch (Exception e) {
					System.err.println("Error al editar el llibre!");
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.print("Introdueix el index del llibre a borrar: ");
				try {
					borrarLlibre(sc.nextInt());
					Thread.sleep(100);
					System.out.println("Llibre borrat amb exit!\n\rATENCI�, ELS INDEX DELS LLIBRES HAN CAMBIAT! Comprovar amb l'opci� 1");
				} catch (Exception e) {
					System.err.println("Error al borrar el llibre!");
					e.printStackTrace();
				}

				break;
			case 6:
				System.out.println("Biblioteca tancada, fins la proxima.");
				break;
			default:
				System.err.println("No es pot triar eixa opci�!");
			}
		} while (op != 6);
		sc.close();
	}

	static void actualitzaLlibre(int identificador) {
		Document dom;
		Element e = null;
		String xml = "biblioteca.xml";

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();

			Element rootEle = dom.createElement("llibres");
			ArrayList<Llibre> llibres = recuperarTots();
			System.out.print("Introdueix el nou titol: ");
			String tit = sc.next();
			System.out.print("Introdueix el nou autor: ");
			String aut = sc.next();
			System.out.print("Introdueix el nou any de publicaci�: ");
			String any = sc.next();
			System.out.print("Introdueix la nova editorial: ");
			String edit = sc.next();
			System.out.print("Introdueix el nou numero de p�gines: ");
			String nPag = sc.next();
			Llibre g = new Llibre(identificador, tit, aut, Integer.parseInt(any), edit, Integer.parseInt(nPag));
			llibres.set(identificador, g);
			for (Llibre llib : llibres) {
				Element l = dom.createElement("llibre");
				rootEle.appendChild(l);
				l.setAttribute("id", Integer.toString(llib.getIdentificador()));

				e = dom.createElement("titol");
				e.appendChild(dom.createTextNode(llib.getTitol()));
				l.appendChild(e);

				e = dom.createElement("autor");
				e.appendChild(dom.createTextNode(llib.getAutor()));
				l.appendChild(e);

				e = dom.createElement("any-publi");
				e.appendChild(dom.createTextNode(Integer.toString(llib.getAnyPubli())));
				l.appendChild(e);

				e = dom.createElement("editorial");
				e.appendChild(dom.createTextNode(llib.getEditorial()));
				l.appendChild(e);

				e = dom.createElement("n-pag");
				e.appendChild(dom.createTextNode(Integer.toString(llib.getNumPag())));
				l.appendChild(e);

			}
			dom.appendChild(rootEle);

			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(xml)));
			} catch (TransformerException te) {
				System.out.println(te.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		} catch (ParserConfigurationException pce) {
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
	}

	static int crearLlibre(Llibre llibre) {
		Document dom;
		Element e = null;
		String xml = "biblioteca.xml";

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();

			Element rootEle = dom.createElement("llibres");
			ArrayList<Llibre> llibres = recuperarTots();
			llibres.add(llibre);
			for (Llibre llib : llibres) {
				Element l = dom.createElement("llibre");
				rootEle.appendChild(l);
				l.setAttribute("id", Integer.toString(llib.getIdentificador()));

				e = dom.createElement("titol");
				e.appendChild(dom.createTextNode(llib.getTitol()));
				l.appendChild(e);

				e = dom.createElement("autor");
				e.appendChild(dom.createTextNode(llib.getAutor()));
				l.appendChild(e);

				e = dom.createElement("any-publi");
				e.appendChild(dom.createTextNode(Integer.toString(llib.getAnyPubli())));
				l.appendChild(e);

				e = dom.createElement("editorial");
				e.appendChild(dom.createTextNode(llib.getEditorial()));
				l.appendChild(e);

				e = dom.createElement("n-pag");
				e.appendChild(dom.createTextNode(Integer.toString(llib.getNumPag())));
				l.appendChild(e);

			}
			dom.appendChild(rootEle);

			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(xml)));

			} catch (TransformerException te) {
				System.out.println(te.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		} catch (ParserConfigurationException pce) {
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
		return llibre.getIdentificador();
	}

	static void mostrarLlibre(Llibre llibre) {
		System.out.println("");
		System.out.println("Id: " + llibre.getIdentificador());
		System.out.println("Titol: " + llibre.getTitol());
		System.out.println("Autor: " + llibre.getAutor());
		System.out.println("Any de publicaci�: " + llibre.getAnyPubli());
		System.out.println("Editorial: " + llibre.getEditorial());
		System.out.println("Numero de p�gines: " + llibre.getNumPag());
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
		Document dom;
		Element e = null;
		String xml = "biblioteca.xml";

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();

			Element rootEle = dom.createElement("llibres");
			ArrayList<Llibre> llibres = recuperarTots();

			llibres.remove(identificador);
			for (int i = 0; i < llibres.size(); i++) {
				Element l = dom.createElement("llibre");
				rootEle.appendChild(l);
				l.setAttribute("id", Integer.toString(i));

				e = dom.createElement("titol");
				e.appendChild(dom.createTextNode(llibres.get(i).getTitol()));
				l.appendChild(e);

				e = dom.createElement("autor");
				e.appendChild(dom.createTextNode(llibres.get(i).getAutor()));
				l.appendChild(e);

				e = dom.createElement("any-publi");
				e.appendChild(dom.createTextNode(Integer.toString(llibres.get(i).getAnyPubli())));
				l.appendChild(e);

				e = dom.createElement("editorial");
				e.appendChild(dom.createTextNode(llibres.get(i).getEditorial()));
				l.appendChild(e);

				e = dom.createElement("n-pag");
				e.appendChild(dom.createTextNode(Integer.toString(llibres.get(i).getNumPag())));
				l.appendChild(e);

			}
			dom.appendChild(rootEle);

			try {
				Transformer tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				tr.setOutputProperty(OutputKeys.METHOD, "xml");
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(xml)));

			} catch (TransformerException te) {
				System.out.println(te.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		} catch (ParserConfigurationException pce) {
			System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
		}
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
