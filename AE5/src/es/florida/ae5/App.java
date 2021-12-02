package es.florida.ae5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App {

	public static void main(String[] args) throws InterruptedException {
		// Carrega la configuracio i crea un session factory
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		configuration.addClass(Llibre.class);
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
				.build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(registry);
		// Obri una nova sessió de la session factory
		Session session;
		System.out.println("MENÚ D'OPCIONS");
		System.out.println("1. Mostrar tots els títols de la biblioteca.");
		System.out.println("2. Mostrar la informació detallada d’un llibre a partir del seu id.");
		System.out.println("3. Afegir un nou llibre a la biblioteca.");
		System.out.println("4. Modificar atributs d’un llibre a partir del seu id.");
		System.out.println("5. Esborrar un llibre a partir del seu id.");
		System.out.println("6. Eixir");
		Scanner sc = new Scanner(System.in);
		int op = 0;

		do  {
			session = sessionFactory.openSession();
			session.beginTransaction();
			System.out.print("\nOPCIÓ: ");
			op = Integer.parseInt(sc.nextLine());
			switch (op) {
			case 1: {
				List listaLlibres = new ArrayList();
				listaLlibres = session.createQuery("FROM Llibre").list();
				for (int i = 0; i < listaLlibres.size(); i++) {
					Llibre l = (Llibre) listaLlibres.get(i);
					System.out.println("ID: " + l.getId());
					System.out.println("ID: " + l.getTitol());
					System.out.println("_____________");
				}
				break;
			}
			case 2: {
				System.out.print("Introdueix l'ID del llibre que vols recuperar: ");
				int idL = Integer.parseInt(sc.nextLine());
				Llibre llib = (Llibre) session.get(Llibre.class, idL);
				System.out.println("ID: " + llib.getId());
				System.out.println("Titol: " + llib.getTitol());
				System.out.println("Autor: " + llib.getAutor());
				System.out.println("Any de naiximent: " + llib.getAny_naiximent());
				System.out.println("Any de publicació: " + llib.getAny_publicacio());
				System.out.println("Editorial: " + llib.getEditorial());
				System.out.println("Numero de pàgines: " + llib.getNumpag());
				break;
			}
			case 3: {
				System.out.print("Introdueix el titol: ");
				String titol = sc.nextLine();
				System.out.print("Introdueix l' autor: ");
				String autor = sc.nextLine();
				System.out.print("Introdueix l'any de naiximent: ");
				String any_naiximent = sc.nextLine();
				System.out.print("Introdueix l'any de publicació: ");
				String any_publicacio = sc.nextLine();
				System.out.print("Introdueix la editorial: ");
				String editorial = sc.nextLine();
				System.out.print("Introdueix el numero de pàgines: ");
				String numpag = sc.nextLine();
				try {
					Llibre llib = new Llibre(titol, autor, any_naiximent, any_publicacio, editorial, numpag);
					Serializable id = session.save(llib);
					System.out.println("Llibre creat correctament");
				} catch (Exception e) {
					System.err.println("No ha sigut possible crear el llibre: " + e);
				}
				break;
			}
			case 4: {
				System.out.print("Introdueix l'ID del llibre que vols recuperar: ");
				int idL = Integer.parseInt(sc.nextLine());
				Llibre llib = (Llibre) session.load(Llibre.class, idL);
				System.out.println("Per a deixar les dades originals deixa en blanc el camp.");
				System.out.println("");
				System.out.print("Introdueix el titol: ");
				String titol = sc.nextLine();
				if (titol != "") {
					llib.setTitol(titol);
				}
				System.out.print("Introdueix l' autor: ");
				String autor = sc.nextLine();
				if (autor != "") {
					llib.setAutor(autor);
				}
				System.out.print("Introdueix l'any de naiximent: ");
				String any_naiximent = sc.nextLine();
				if (any_naiximent != "") {
					llib.setAny_naiximent(any_naiximent);
				}
				System.out.print("Introdueix l'any de publicació: ");
				String any_publicacio = sc.nextLine();
				if (any_publicacio != "") {
					llib.setAny_publicacio(any_publicacio);
				}
				System.out.print("Introdueix la editorial: ");
				String editorial = sc.nextLine();
				if (editorial != "") {
					llib.setEditorial(editorial);
				}
				System.out.print("Introdueix el numero de pàgines: ");
				String numpag = sc.nextLine();
				if (numpag != "") {
					llib.setNumpag(numpag);
				}
				try {
					session.update(llib);
					System.out.println("Llibre actualitzat correctament");
				} catch (Exception e) {
					System.err.println("No ha sigut possible actualitzar el llibre: " + e);
				}

				break;
			}
			case 5: {
				System.out.print("Introdueix l'ID del llibre que vols esborrar: ");
				int idL = Integer.parseInt(sc.nextLine());
				try {
					Llibre llib = new Llibre();
					llib.setId(idL);
					session.delete(llib);
					System.out.println("Llibre esborrat correctament");
				} catch (Exception e) {
					System.err.println("No ha sigut possible esborrar el llibre: " + e);
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
			};
			session.getTransaction().commit();
			session.close();
		}while (op != 6);

		// Ací les operacio/ns CRUD (crear, llegir, actualitzar, borrar)
		// Commit de la transacció i tanca de sessi

	}

}
