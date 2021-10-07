package es.florida.AE02;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private ActionListener actionListenerBtnBuscar, actionListenerBtnReemplazar;

	Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		control();
	}

	private void control() {
		String original = modelo.getOriginal(); //Obté el text original
		vista.getTextAreaOriginal().setText(original); //El carga
		
		actionListenerBtnBuscar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = modelo.buscar(vista.getTextFieldBuscar().getText());
				vista.mostrarMensaje(message);
				
				
				// D'aquesta forma ho posa en el textfield de buscar
				//vista.getTextFieldBuscar().setText(Integer.toString(modelo.buscar(vista.getTextFieldBuscar().getText().toString())));
			}
		};
		vista.getBtnBuscar().addActionListener(actionListenerBtnBuscar);
	}

}
