package pruebaes.florida;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private ActionListener actionListenerBoton1, actionListenerBoton2;

	Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		control();
	}

	private void control() {
		actionListenerBoton1 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje1 = modelo.getMensaje1();
				vista.mostrarMensaje(mensaje1);

			}
		};
		vista.getBtn1().addActionListener(actionListenerBoton1);
		
		actionListenerBoton2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje2 = modelo.getMensaje2();
				vista.mostrarMensaje(mensaje2);

			}
		};
		vista.getBtn2().addActionListener(actionListenerBoton2);
	}

}
