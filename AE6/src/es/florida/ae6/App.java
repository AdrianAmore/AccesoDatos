package es.florida.ae6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import org.bson.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;



public class App {

	public static void main(String[] args) throws InterruptedException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("Biblioteca");
		MongoCollection<Document> coleccion = database.getCollection("Llibres");
		// CRUD operations

		System.out.println("MENÚ D'OPCIONS");
		System.out.println("1. Mostrar tots els títols de la biblioteca.");
		System.out.println("2. Mostrar la informació detallada d’un llibre a partir del seu id.");
		System.out.println("3. Afegir un nou llibre a la biblioteca.");
		System.out.println("4. Modificar atributs d’un llibre a partir del seu id.");
		System.out.println("5. Esborrar un llibre a partir del seu id.");
		System.out.println("6. Eixir");
		Scanner sc = new Scanner(System.in);
		int op = 0;

		do {
			System.out.print("\nOPCIÓ: ");
			op = Integer.parseInt(sc.nextLine());
			switch (op) {
			case 1: {
				MongoCursor<Document> cursor = coleccion.find().iterator();
				while (cursor.hasNext()) {
					JSONObject obj = new JSONObject(cursor.next().toJson());
					System.out.println("Id: " + obj.getString("Id") + "\tTitol: " + obj.getString("Titol"));
				}

				break;
			}
			case 2: {
				System.out.print("Introdueix la id del llibre: ");
				String id = sc.nextLine();
				Bson query = eq("Id", id);
				MongoCursor<Document> cursor = coleccion.find(query).iterator();
				JSONObject obj = new JSONObject(cursor.next().toJson());
				System.out.println("Titol: " + obj.getString("Titol"));
				System.out.println("Autor: " + obj.getString("Autor"));

				try {
					System.out.println("Any de naixement: " + obj.getString("Any_naixement"));
				} catch (Exception e) {
					System.out.println("Any de naixement: desconegut ");
				}

				System.out.println("Any de Publicacio: " + obj.getString("Any_publicacio"));
				System.out.println("Editorial: " + obj.getString("Editorial"));
				System.out.println("Nombre pagines: " + obj.getString("Nombre_pagines"));
				break;
			}
			case 3: {
				System.out.print("Titol: ");
				String titol = sc.nextLine();
				System.out.print("Autor: ");
				String autor = sc.nextLine();
				System.out.print("Any de naixement: ");
				String any_naiximent = sc.nextLine();
				System.out.print("Any de publicació: ");
				String any_publicacio = sc.nextLine();
				System.out.print("Editorial: ");
				String editorial = sc.nextLine();
				System.out.print("Nombre de pàgines: ");
				String numpag = sc.nextLine();
				MongoCursor<Document> cursor = coleccion.find().iterator();

				int max = 0;
				while (cursor.hasNext()) {
					JSONObject obj = new JSONObject(cursor.next().toJson());
					if (obj.getInt("Id") > max) {
						max = obj.getInt("Id");
					}
				}
				max++;
				Document doc = new Document();
				doc.append("Id", max);
				doc.append("Titol", titol);
				doc.append("Autor", autor);
				doc.append("Any_naixement", any_naiximent);
				doc.append("Any_publicacio", any_publicacio);
				doc.append("Editorial", editorial);
				doc.append("Nombre_pagines", numpag);

				System.out.println("Llibre creat correctament");

				coleccion.insertOne(doc);
				break;
			}
			case 4: {
				System.out.print("Introdueix la id del llibre: ");
				String id = sc.nextLine();
				Bson query = eq("Id", id);
				System.out.println("Per a deixar les dades originals deixa en blanc el camp.");
				System.out.println("");
				try {
					System.out.print("Introdueix el titol: ");
					String titol = sc.nextLine();
					if (titol != "") {
						coleccion.updateOne(query, new Document("$set", new Document("Titol", titol)));
					}
					System.out.print("Introdueix l' autor: ");
					String autor = sc.nextLine();
					if (autor != "") {
						coleccion.updateOne(query, new Document("$set", new Document("Autor", autor)));
					}
					System.out.print("Introdueix l'any de naiximent: ");
					String any_naiximent = sc.nextLine();
					if (any_naiximent != "") {
						coleccion.updateOne(query, new Document("$set", new Document("Any_naixement", any_naiximent)));
					}
					System.out.print("Introdueix l'any de publicació: ");
					String any_publicacio = sc.nextLine();
					if (any_publicacio != "") {
						coleccion.updateOne(query,
								new Document("$set", new Document("Any_publicacio", any_publicacio)));
					}
					System.out.print("Introdueix la editorial: ");
					String editorial = sc.nextLine();
					if (editorial != "") {
						coleccion.updateOne(query, new Document("$set", new Document("Editorial", editorial)));
					}
					System.out.print("Introdueix el numero de pàgines: ");
					String numpag = sc.nextLine();
					if (numpag != "") {
						coleccion.updateOne(query, new Document("$set", new Document("Nombre_pagines", numpag)));
					}
					System.out.println("Llibre actualitzat correctament");
				} catch (Exception e) {
					System.err.println("No ha sigut possible actualitzar el llibre: " + e);
				}
				break;
			}
			case 5: {
				System.out.print("Introdueix la id del llibre: ");
				String id = sc.nextLine();
				try {
					coleccion.deleteOne(eq("Id", id));
					System.out.println("El llibre amb el Id " + id + " ha sigut eliminat correctament");
				} catch (Exception e) {
					System.err.println("error: " + e);
				}
				break;
			}

			case 6: {
				System.out.print("Adeu!!");
				break;
			}
			default:
				Thread.sleep(500);
				System.err.println("No existeix eixa opció!! ");
				break;
			}
			;
		} while (op != 6);
		mongoClient.close();
	}

}
