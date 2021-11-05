package es.florida.ae4;

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import org.apache.ibatis.jdbc.ScriptRunner;

public class App {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String jdbcURL = "jdbc:mysql://localhost:3306/biblioteca";
		String username = "root";
		String password = "";

		String csvFilePath = "AE04_T1_4_JDBC_Dades.csv";

		int batchSize = 20;

		Connection connection = null;

		System.out.print("Es la primera vegada que utilitzes l'aplicació? (s/n): "); // Pregunte a l'usuari si es la
																						// primera vegada que utilitza
																						// l'apliació axí borre tots el
																						// registres, torne
																						// l'autoincrement a 1 y
																						// carregue de nou l'arxiu CSV
		String sn = sc.next();
		if (sn.toLowerCase().equals("s")) {
			try {
				connection = DriverManager.getConnection(jdbcURL, username, password);
				connection.setAutoCommit(false);
				PreparedStatement psBorrar = connection.prepareStatement("DELETE FROM llibres");
				psBorrar.executeUpdate();
				Statement st = connection.createStatement();
				st.execute("ALTER TABLE llibres AUTO_INCREMENT = 1");
				String sql = "INSERT INTO llibres (titol, autor, any_naiximent, any_publicacio, editorial, numpag) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(sql);

				BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
				String lineText = null;

				int count = 0;

				lineReader.readLine();

				while ((lineText = lineReader.readLine()) != null) {
					String[] data = lineText.split(";");
					String titol = data[0];
					String autor = data[1];
					String any_naiximent = data[2];
					String any_publicacio = data[3];
					String editorial = data[4];
					String numpag = data[5];

					statement.setString(1, titol);
					statement.setString(2, autor);
					statement.setString(3, any_naiximent);
					statement.setString(4, any_publicacio);
					statement.setString(5, editorial);
					statement.setString(6, numpag);

					statement.addBatch();

					if (count % batchSize == 0) {
						statement.executeBatch();
					}
				}

				lineReader.close();

				statement.executeBatch();

				connection.commit();
				connection.close();
				System.out.println("Dades cargades amb exit!");
			} catch (IOException ex) {
				System.err.println(ex);
			} catch (SQLException ex) {
				ex.printStackTrace();

				try {
					connection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		System.out.println("---OPCIONS---");
		System.out.println("1. Mostrar llibres de autors nascuts abans de 1950");
		System.out.println("2. Mostrar editorials amb llibres del segle XXI");
		System.out.println("3. Fer una consulta SQL");
		System.out.println("4. Eixir de l'apliació");
		int op = 0;
		do {
			System.out.print("Opció: ");
			op = sc.nextInt();
			switch (op) {
			case 1: {

				try {
					connection = DriverManager.getConnection(jdbcURL, username, password);
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery(
							"SELECT titol, autor, any_publicacio FROM llibres WHERE any_naiximent < 1950"); // Execute y
																											// faig
																											// print de
																											// la
																											// consulta
					while (rs.next()) {
						System.out.println("Titol: " + rs.getString(1));
						System.out.println("Autor: " + rs.getString(2));
						System.out.println("Any de publicació: " + rs.getString(3));
						System.out.println("______________________________________________");
					}
					rs.close();
					stmt.close();
					connection.close();
				} catch (Exception e) {
					System.err.println(e);
				}

				break;
			}
			case 2: {
				try {
					connection = DriverManager.getConnection(jdbcURL, username, password);
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT editorial FROM llibres WHERE any_publicacio > 2000");// Execute
																													// y
																													// faig
																													// print
																													// de
																													// la
																													// consulta
					while (rs.next()) {
						System.out.println(rs.getString(1));
					}
					rs.close();
					stmt.close();
					connection.close();
				} catch (Exception e) {
					System.err.println(e);
				}
				break;
			}
			case 3: {
				try {
					connection = DriverManager.getConnection(jdbcURL, username, password);
					Statement stmt = connection.createStatement();
					File consql = new File("consulta.sql");
					consql.delete();
					consql.createNewFile();
					System.out.print("La consulta será SELECT (s/n)?: ");
					if (sc.next().toLowerCase().equals("s")) {
						System.out.print("Introdueix la consulta SQL a fer (IMPORTANT: tanca la frase amb ';'): ");
						String s = sc.next();
						s += sc.nextLine();
						FileWriter wr = new FileWriter(consql);
						wr.write(s);
						wr.close();
						stmt.executeQuery(s);
					} else {
						System.out.print("Introdueix la consulta SQL a fer (IMPORTANT: tanca la frase amb ';'): ");
						String s = sc.next();
						s += sc.nextLine();
						FileWriter wr = new FileWriter(consql);
						wr.write(s);
						wr.close();
						stmt.executeUpdate(s);
					}
					// Amb scriptrunner pase el SQL statement a un arxiu SQL que s'executa dins de
					// la BD y després llig els resultats amb el Reader y faig print dels resultats
					ScriptRunner scr = new ScriptRunner(connection);
					Reader rd = new BufferedReader(new FileReader(consql));
					scr.runScript(rd);
					stmt.close();
					connection.close();
				} catch (Exception e) {
					System.err.println(e);
				}
				break;
			}
			case 4: {
				System.out.println("Fins la proxima!");
			}
				break;
			default:
				System.err.println("Eixa opció no existeix");
			}

		} while (op != 4);

	}

}
