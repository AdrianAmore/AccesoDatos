package es.florida.AE02;

import es.florida.AE02.Controlador;
import es.florida.AE02.Modelo;
import es.florida.AE02.Vista;



public class Principal {

	public static void main(String[] args) {
		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		Controlador controlador = new Controlador(modelo, vista);
	}

}