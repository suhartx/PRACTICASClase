package ud.prog3.pr01;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;




/** Clase para crear instancias como listas de reproducci�n,
 * que permite almacenar listas de ficheros con posici�n de �ndice
 * (al estilo de un array / arraylist)
 * con marcas de error en los ficheros y con m�todos para cambiar la posici�n
 * de los elementos en la lista, borrar elementos y a�adir nuevos.
 */
public class ListaDeReproduccion implements ListModel<String> {
	ArrayList<File> ficherosLista;     // ficheros de la lista de reproducci�n
	int ficheroEnCurso = -1;           // Fichero seleccionado (-1 si no hay ninguno seleccionado)
	private static Logger logger = Logger.getLogger( ListaDeReproduccion.class.getName() );////variable de clase que nos permite registrar las cosas que van ocurriendo
	//ESTE OBJETO NOS SIRVE PARA SUSTITUIR OS MAINS Y HACER EN UN JUNIT LO QUE TENDRIAMOS QUE HACER EN UN MAIN
	private static final boolean ANYADIR_A_FIC_LOG = false; // poner true para no sobreescribir
	
	static {
	 try {
	 logger.addHandler( new FileHandler(
	 ListaDeReproduccion.class.getName()+".log.xml", ANYADIR_A_FIC_LOG ));
	 } catch (SecurityException | IOException e) {
	 logger.log( Level.SEVERE, "Error en creación fichero log" );
	 }
	}

	
	/** Devuelve uno de los ficheros de la lista
	 * @param posi	Posici�n del fichero en la lista (de 0 a size()-1)
	 * @return	Devuelve el fichero en esa posici�n
	 * @throws IndexOutOfBoundsException	Si el �ndice no es v�lido
	 */
	public File getFic( int posi ) throws IndexOutOfBoundsException {
		return ficherosLista.get( posi );
	}	
	

	/** Inicializa la variable ficheroslista
	 * 
	 * @param ficherosLista
	 */
	public ListaDeReproduccion() {
		super();
		ficherosLista = new ArrayList<>();
	}
	/**
	 * intercambia posiciones en caso de que tengan contenido esas posiciones
	 * @param posi1
	 * @param posi2
	 */
	public void intercambia( int posi1, int posi2 ) {
		if(this.ficherosLista.get(posi1)!= null&&this.ficherosLista.get(posi2)!=null){
		File fichero1 = this.getFic(posi1);
		File fichero2 = this.getFic(posi2);
		this.ficherosLista.set(posi2, fichero1);
		this.ficherosLista.set(posi1, fichero2);
		}
	}	
	
	/**
	 * Devuelve la largura de los ficheros
	 * @return
	 */
	public int size(){
		return ficherosLista.size();
	}
	
	

	/** A�ade a la lista de reproducci�n todos los ficheros que haya en la 
	 * carpeta indicada, que cumplan el filtro indicado.
	 * Si hay cualquier error, la lista de reproducci�n queda solo con los ficheros
	 * que hayan podido ser cargados de forma correcta.
	 * @param carpetaFicheros	Path de la carpeta donde buscar los ficheros
	 * @param filtroFicheros	Filtro del formato que tienen que tener los nombres de
	 * 							los ficheros para ser cargados.
	 * 							String con cualquier letra o d�gito. Si tiene un asterisco
	 * 							hace referencia a cualquier conjunto de letras o d�gitos.
	 * 							Por ejemplo p*.* hace referencia a cualquier fichero de nombre
	 * 							que empiece por p y tenga cualquier extensi�n.
	 * @return	N�mero de ficheros que han sido a�adidos a la lista
	 */
	public int add(String carpetaFicheros, String filtroFicheros) {

		int ficsAnyadidos = 0;
		if (carpetaFicheros!=null) {
			
		logger.log( Level.INFO, "Añadiendo ficheros con filtro " + filtroFicheros );
			try {
				filtroFicheros = filtroFicheros.replaceAll( "\\.", "\\\\." );  // Pone el s�mbolo de la expresi�n regular \. donde figure un .
				filtroFicheros = filtroFicheros.replaceAll( "\\*", "\\.*" );
				logger.log( Level.INFO, "Añadiendo ficheros con filtro " + filtroFicheros ); 
				Pattern pFics = Pattern.compile( filtroFicheros, Pattern.CASE_INSENSITIVE );
				
				File fInic = new File(carpetaFicheros);
				if (fInic.isDirectory()) {
					for( File f : fInic.listFiles() ) {
					logger.log( Level.FINE, "Procesando fichero " + f.getName() );
					// TODO: Comprobar que f.getName() cumple el patrón y añadirlo a la lista
					if(pFics.matcher(f.getName()).matches() ) {
								// Si cumple el patrón, se añade
								logger.log( Level.INFO, "Añadido vídeo a lista de reproducción: " + f.getName() );
								add( f );
								ficsAnyadidos++;
								
						 }
					 }
				 }
			}catch (PatternSyntaxException e) {
				logger.log( Level.SEVERE, "Error en patrón de expresión regular ", e );
			}
		 }
		logger.log( Level.INFO, "ficheros añadidos: " + ficsAnyadidos );
		return ficsAnyadidos;
	}
	
	/**
	 * A�ade un fichero al final de la lista
	 * @param f el fichero a�adido en cuesti�n
	 */
	public void add(File f) {
		this.ficherosLista.add(f);
	}
	
	/**
	 * elimina el fichero de la posicion que hayamos se�alado
	 * @param posi
	 */
	public void removeFic( int posi ) {
		this.ficherosLista.remove(posi);
	}
	
	/**
	 * limpia la lista
	 */
	public void clear() {
		this.ficherosLista.clear();
	}
	
	//
	// M�todos de selecci�n
	//
	
	/** Seleciona el primer fichero de la lista de reproducci�n
	 * @return	true si la selecci�n es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irAPrimero() {
		ficheroEnCurso = 0;  // Inicia
		if (ficheroEnCurso>=ficherosLista.size()) {
			ficheroEnCurso = -1;  // Si no se encuentra, no hay selecci�n
			return false;  // Y devuelve error
		}
		return true;
	}
	
	/** Seleciona el �ltimo fichero de la lista de reproducci�n
	 * @return	true si la selecci�n es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irAUltimo() {
		ficheroEnCurso = ficherosLista.size()-1;  // Inicia al final
		if (ficheroEnCurso==-1) {  // Si no se encuentra, no hay selecci�n
			return false;  // Y devuelve error
		}
		return true;
	}

	/** Seleciona el anterior fichero de la lista de reproducci�n
	 * @return	true si la selecci�n es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irAAnterior() {
		if (ficheroEnCurso>=0) ficheroEnCurso--;
		if (ficheroEnCurso==-1) {  // Si no se encuentra, no hay selecci�n
			return false;  // Y devuelve error
		}
		return true;
	}

	/** Seleciona el siguiente fichero de la lista de reproducci�n
	 * @return	true si la selecci�n es correcta, false si hay error y no se puede seleccionar
	 */
	public boolean irASiguiente() {
		ficheroEnCurso++;
		if (ficheroEnCurso>=ficherosLista.size()) {
			ficheroEnCurso = -1;  // Si no se encuentra, no hay selecci�n
			return false;  // Y devuelve error
		}
		return true;
	}

	/** Devuelve el fichero seleccionado de la lista
	 * @return	Posici�n del fichero seleccionado en la lista de reproducci�n (0 a n-1), -1 si no lo hay
	 */
	public int getFicSeleccionado() {
		return ficheroEnCurso;
	}

	//
	// M�todos de DefaultListModel
	//
	
	@Override
	public int getSize() {
		return ficherosLista.size();
	}

	@Override
	public String getElementAt(int index) {
		return ficherosLista.get(index).getName();
	}

		// Escuchadores de datos de la lista
		ArrayList<ListDataListener> misEscuchadores = new ArrayList<>();
	@Override
	public void addListDataListener(ListDataListener l) {
		misEscuchadores.add( l );
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		misEscuchadores.remove( l );
	}
	
	// Llamar a este m�todo cuando se a�ada un elemento a la lista
	// (Utilizado para avisar a los escuchadores de cambio de datos de la lista)
	private void avisarAnyadido( int posi ) {
		for (ListDataListener ldl : misEscuchadores) {
			ldl.intervalAdded( new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, posi, posi ));
		}
	}
}
