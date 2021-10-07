package pruebaes.florida;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class Vista {

	public JFrame frame;
	public JButton btn1, btn2;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Vista() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btn1 = new JButton("Boton1");
		btn1.setBounds(173, 59, 91, 23);
		frame.getContentPane().add(btn1);

		btn2 = new JButton("Boton2");
		btn2.setBounds(173, 111, 91, 23);
		frame.getContentPane().add(btn2);
		frame.setVisible(true);
	}

	public JButton getBtn1() {
		return btn1;
	}

	public JButton getBtn2() {
		return btn2;
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(new JFrame(), mensaje, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}
}
