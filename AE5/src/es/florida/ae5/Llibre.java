package es.florida.ae5;

public class Llibre {

	// Atributos
	int id;
	String titol;
	String autor;
	String any_naiximent;
	String any_publicacio;
	String editorial;
	String numpag;

	// Constructor
	
	public Llibre() {};
	public Llibre(int id, String titol, String autor, String any_naiximent, String any_publicacio, String editorial,
			String numpag) {
		super();
		this.id = id;
		this.titol = titol;
		this.autor = autor;
		this.any_naiximent = any_naiximent;
		this.any_publicacio = any_publicacio;
		this.editorial = editorial;
		this.numpag = numpag;
	}
	
	public Llibre(String titol, String autor, String any_naiximent, String any_publicacio, String editorial,
			String numpag) {
		super();
		this.titol = titol;
		this.autor = autor;
		this.any_naiximent = any_naiximent;
		this.any_publicacio = any_publicacio;
		this.editorial = editorial;
		this.numpag = numpag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getAny_naiximent() {
		return any_naiximent;
	}

	public void setAny_naiximent(String any_naiximent) {
		this.any_naiximent = any_naiximent;
	}

	public String getAny_publicacio() {
		return any_publicacio;
	}

	public void setAny_publicacio(String any_publicacio) {
		this.any_publicacio = any_publicacio;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getNumpag() {
		return numpag;
	}

	public void setNumpag(String numpag) {
		this.numpag = numpag;
	}
}
