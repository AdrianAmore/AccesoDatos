package es.florida.exament1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class App {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws SQLException {

		ArrayList<String> connect = RecuperaConexio();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connect.get(0), connect.get(1), connect.get(2));
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, nom FROM preus");
			while (rs.next()) {
				System.out.println("id: " + rs.getString(1));
				System.out.println("Nom: " + rs.getString(2));
				System.out.println("______________________________________________");
			}
			Scanner sc = new Scanner(System.in);
			System.out.print("Tria una destinació (per el seu id): ");
			int idV = sc.nextInt();
			rs = stmt.executeQuery("SELECT nom, preu FROM preus WHERE id LIKE " + idV);
			String result = "";
			while (rs.next()) {
				System.out.println("El preu de la destinació " + rs.getString(1) + " es: " + rs.getString(2));
				result = "El preu de la destinació " + rs.getString(1) + " es: " + rs.getString(2);
			}
			BufferedReader reader = null;

			FileWriter writer = null;
			try {
				File f1 = new File("consulta.txt");
				f1.createNewFile();
				reader = new BufferedReader(new FileReader("consulta.txt"));

				String line = reader.readLine();

				while (line != null) {
					line = reader.readLine();
				}

				String newContent = result;
				writer = new FileWriter("consulta.txt");

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

			rs.close();
			stmt.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static ArrayList<String> RecuperaConexio() {
		ArrayList<String> conValues = new ArrayList<String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File("config.xml"));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("config1");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					String url = eElement.getElementsByTagName("url").item(0).getTextContent();
					String user = eElement.getElementsByTagName("user").item(0).getTextContent();
					String password = eElement.getElementsByTagName("password").item(0).getTextContent();
					conValues.add(url);
					conValues.add(user);
					conValues.add(password);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conValues;
	}

}
