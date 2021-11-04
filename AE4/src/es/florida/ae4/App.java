package es.florida.ae4;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		String jdbcURL = "jdbc:mysql://localhost:3306/biblioteca";
		String username = "root";
		String password = "";

		String csvFilePath = "AE04_T1_4_JDBC_Dades.csv";

		int batchSize = 20;

		Connection connection = null;

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

		} catch (IOException ex) {
			System.err.println(ex);
		} catch (SQLException ex) {
			ex.printStackTrace();

			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Scanner sc = new Scanner(System.in);
			System.out.println("---OPCIONS---");
			System.out.println("1. Mostrar llibres de autors nascuts abans de 1950");
			System.out.println("2. Mostrar editorials amb llibres del segle XXI");
			System.out.println("3. Fer una consulta SQL");
			
			int op = sc.nextInt();			
			switch (op) {
			case 1: {

				break;
			}
			case 2: {

				break;
			}
			case 3: {

				break;
			}
			default:
				System.err.println("Eixa opció no existeix");
			}
		}

	}

}
