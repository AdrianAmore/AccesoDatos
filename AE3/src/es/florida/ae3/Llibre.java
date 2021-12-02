package es.florida.ae3;

public class Llibre {
	// Atributos
	int identificador;
	String titol;
	String autor;
	int any_publi;
	String editorial;
	int num_pag;

	// Constructor
	public Llibre(int id, String titl, String aut, int any, String ed, int num) {
		this.identificador = id;
		this.titol = titl;
		this.autor = aut;
		this.any_publi = any;
		this.editorial = ed;
		this.num_pag = num;
	}

	// Setters - Getters

	public int getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(int s) {
		this.identificador = s;
	}

	public String getTitol() {
		return this.titol;
	}

	public void setTitol(String s) {
		this.titol = s;
	}

	public String getAutor() {
		return this.autor;
	}

	public void setAutor(String s) {
		this.autor = s;
	}

	public String getEditorial() {
		return this.editorial;
	}

	public void setEditorial(String s) {
		this.editorial = s;
	}

	public int getAnyPubli() {
		return this.any_publi;
	}

	public void setAnyPubli(int s) {
		this.any_publi = s;
	}

	public int getNumPag() {
		return this.num_pag;
	}

	public void setNumPag(int s) {
		this.num_pag = s;
	}
}
