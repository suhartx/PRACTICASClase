package pr00;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

/** Clase principal de minijuego de coche para Práctica 00 - Prog III
 * Ventana del minijuego.
 * @author Andoni Eguíluz
 * Facultad de Ingeniería - Universidad de Deusto (2014)
 */
public class VentanaJuego extends JFrame {
	private static final long serialVersionUID = 1L;  // Para serialización
	JPanel pPrincipal;         // Panel del juego (layout nulo)
	CocheJuego miCoche;        // Coche del juego
	MiRunnable miHilo = null;  // Hilo del bucle principal de juego	

	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public VentanaJuego() {
		// Liberación de la ventana por defecto al cerrar
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creación contenedores y componentes
		pPrincipal = new JPanel();
		JPanel pBotonera = new JPanel();
		JButton bAcelerar = new JButton( "Acelera" );
		JButton bFrenar = new JButton( "Frena" );
		JButton bGiraIzq = new JButton( "Gira Izq." );
		JButton bGiraDer = new JButton( "Gira Der." );
		// Formato y layouts
		pPrincipal.setLayout( null );
		pPrincipal.setBackground( Color.white );
		// Añadido de componentes a contenedores
		add( pPrincipal, BorderLayout.CENTER );
		pBotonera.add( bAcelerar );
		pBotonera.add( bFrenar );
		pBotonera.add( bGiraIzq );
		pBotonera.add( bGiraDer );
		add( pBotonera, BorderLayout.SOUTH );
		// Formato de ventana
		setSize( 700, 500 );
		// Escuchadores de botones
		bAcelerar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (miCoche.getVelocidad()==0)
					miCoche.acelera( +5 );
				else 
					miCoche.acelera( +5 );
					// miCoche.acelera( miCoche.getVelocidad()*0.10 );   // para acelerar progresivo
				System.out.println( "Nueva velocidad de coche: " + miCoche.getVelocidad() );
			}
		});
		bFrenar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoche.acelera( -5 );
				System.out.println( "Nueva velocidad de coche: " + miCoche.getVelocidad() );
			}
		});
		bGiraIzq.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoche.gira( +10 );
				System.out.println( "Nueva dirección de coche: " + miCoche.getDireccionActual() );
			}
		});
		bGiraDer.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				miCoche.gira( -10 );
				System.out.println( "Nueva dirección de coche: " + miCoche.getDireccionActual() );
			}
		});
		// Añadido para que también se gestione por teclado con el KeyListener
		pPrincipal.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						miCoche.acelera( +5 );
						break;
					}
					case KeyEvent.VK_DOWN: {
						miCoche.acelera( -5 );
						break;
					}
					case KeyEvent.VK_LEFT: {
						miCoche.gira( +10 );
						break;
					}
					case KeyEvent.VK_RIGHT: {
						miCoche.gira( -10 );
						break;
					}
				}
			}
		});
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		// Cierre del hilo al cierre de la ventana
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	
	/** Crea un coche nuevo y lo añade a la ventana 
	 * @param posX	Posición X de pixel del nuevo coche
	 * @param posY	Posición Y de píxel del nuevo coche
	 */
	public void creaCoche( int posX, int posY ) {
		// Crear y añadir el coche a la ventana
		miCoche = new CocheJuego();
		miCoche.setPosicion( posX, posY );
		pPrincipal.add( miCoche.getGrafico() );
	}
	
	/** Programa principal de la ventana de juego
	 * @param args
	 */
	public static void main(String[] args) {
		// Crea y visibiliza la ventana con el coche
		VentanaJuego miVentana = new VentanaJuego();
		miVentana.creaCoche( 150, 100 );
		miVentana.setVisible( true );
		miVentana.miCoche.setPiloto( "Fernando Alonso" );
		// Crea el hilo de movimiento del coche y lo lanza
		miVentana.miHilo = miVentana.new MiRunnable();  // Sintaxis de new para clase interna
		Thread nuevoHilo = new Thread( miVentana.miHilo );
		nuevoHilo.start();
	}
	
	/** Clase interna para implementación de bucle principal del juego como un hilo
	 * @author Andoni Eguíluz
	 * Facultad de Ingeniería - Universidad de Deusto (2014)
	 */
	class MiRunnable implements Runnable {
		boolean sigo = true;
		@Override
		public void run() {
			// Bucle principal forever hasta que se pare el juego...
			while (sigo) {
				// Mover coche
				miCoche.mueve( 0.040 );
				// Chequear choques
				if (miCoche.getPosX() < -JLabelCoche.TAMANYO_COCHE/2 || miCoche.getPosX()>pPrincipal.getWidth()-JLabelCoche.TAMANYO_COCHE/2 ) {
					// Espejo horizontal si choca en X
					System.out.println( "Choca X");
					double dir = miCoche.getDireccionActual();
					dir = 180-dir;   // Rebote espejo sobre OY (complementario de 180)
					if (dir < 0) dir = 360+dir;  // Corrección para mantenerlo en [0,360)
					miCoche.setDireccionActual( dir );
				}
				// Se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones
				if (miCoche.getPosY() < -JLabelCoche.TAMANYO_COCHE/2 || miCoche.getPosY()>pPrincipal.getHeight()-JLabelCoche.TAMANYO_COCHE/2 ) {
					// Espejo vertical si choca en Y
					System.out.println( "Choca Y");
					double dir = miCoche.getDireccionActual();
					dir = 360 - dir;  // Rebote espejo sobre OX (complementario de 360)
					miCoche.setDireccionActual( dir );
				}
				// Dormir el hilo 40 milisegundos
				try {
					Thread.sleep( 40 );
				} catch (Exception e) {
				}
			}
		}
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() {
			sigo = false;
		}
	};
	
}
