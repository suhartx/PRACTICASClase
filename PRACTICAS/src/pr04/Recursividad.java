package pr04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Clase con diferentes métodos recursivos 
 * @author Suhar
 *
 */
public class Recursividad {

	static int llamadas =0;
	static int iteraciones =0;

		public static void main(String[] args) {
			
			String frase = "hola quetal hola que pasa que tal";
			
			String fraseInvertida = invertirFrase(frase);
			
			String palabrasInvertidas = invertirPalabras(frase);
			
			Long numero = 16456456l;
			
			File archivo = new File ("ficheros/fichero.txt");

			ArrayList<String> palabrasOrdenadas = ordenaQuick(sacaPalabras(archivo));

			String palabraBuscada ="y";
			
			int busquedaPalabra = buscaPalabra(palabrasOrdenadas, palabraBuscada);
			
			
//---------------------------------------------------------------------------------------

			
			System.out.println(frase +" al reves es " + fraseInvertida);
			
			System.out.println("las palabras invertidas \n" +frase +"\nson\n"+palabrasInvertidas);

			System.out.println("el numero " + numero +" enhexadecimal es " +longAHexa(numero));
			
			System.out.println("el fichero con las palabras invertidas procedente de fichero.txt\n" +sacaPalabras(archivo).toString());					

			System.out.println("Lista ordenada alfabeticamente \n" + palabrasOrdenadas.toString());
			
			System.out.println("llamadas: " + llamadas);	
			
			System.out.println("Iteraciones: " + iteraciones);

			System.out.println("la última palabra: " + palabraBuscada + " en la arrayList ordenada de fichero.txt esta en la posición " 
			+ (busquedaPalabra+1) +"\nen un array de un tamaño de longitud "+palabrasOrdenadas.size());
			

		}
		
//==============================invertirFrase=============================================
		
	/**
	 * Metodo que invierte una frase letra a letra
	 * @param frase
	 * @return frase al reves
	 */
	public static String invertirFrase(String frase) {
		//METEMOS EL ARRAY DE LA FRASE CONCATENADA EN CARACTERES
		return invertirFraseRecursiva(frase.toCharArray(),frase, 0, frase.toCharArray().length-1);
	}

	public static String invertirFraseRecursiva(char[] fraseLista,String palabra, int inicio, int fin) {
		
		String palabraAlReves="";
		if(fin<inicio) {//SI LA POSICION INICAL ES MAYOR QUE LA FINAL SE HA TERMINADO POR TANTO SE COMPRUEBA
			for (int i = 1; i <= fraseLista.length; i++) {
				palabraAlReves+=fraseLista[fraseLista.length-i];
			}
			if (palabraAlReves.equals(palabra)){
				return String.valueOf(fraseLista);
			}else {return null;}
		}else {//PONEMOS LA LETRA INICIAL DONDE LA FINAL Y VICEVERSA
			char inicial = fraseLista[inicio];
			fraseLista[inicio] = fraseLista[fin];
			fraseLista[fin]=inicial;
			return invertirFraseRecursiva(fraseLista, palabra, inicio+1, fin-1);
		}
	}
	
//=================================invertirPalabras==========================================
	
	
	/**
	 * Metodo que devuelve una frase al reves palaba a palabra
	 * @param frase
	 * @return frase al reves palabra a palabra
	 */
	public static String invertirPalabras(String frase) {
		
		String[] palabras = frase.split(" ");//SEPARAMOS EL ARRAY POR ESPACIOS

		return invertirPalabrasRecursiva(palabras, frase, 0, palabras.length-1);
	}
	
	public static String invertirPalabrasRecursiva(String[] fraseLista,String frase, int inicio, int fin) {

		if(fin<inicio) {//SI LA POSICION INICAL ES MAYOR QUE LA FINAL SE HA TERMINADO POR TANTO SE FFORMA LA FRASE AL REVES
			String fraseAlReves="";
			for (int i = 0; i < fraseLista.length; i++) {
				fraseAlReves+=fraseLista[i];
				if (i < fraseLista.length-1) {
					fraseAlReves+=" ";
				}
			}
				return fraseAlReves;
		}else {//PONEMOS LA LETRA INICIAL DONDE LA FINAL Y VICEVERSA
			String inicial = fraseLista[inicio];
			fraseLista[inicio] = fraseLista[fin];
			fraseLista[fin]=inicial;
			return invertirPalabrasRecursiva(fraseLista, frase, inicio+1, fin-1);
		}
	}
	
//==================================longAHexa=========================================
	
	/**
	 * Metodo que vuelve el valor hexadecimal de un numero
	 * @param decimal el numero decimal que meteremos como parametro
	 * @return el numero hexadecimal que devolveremos como string
	 */
	public static String longAHexa(Long decimal) {

		//EN ESTE CASO COMO LA LIATA QUE CREAMOS ES AL REVES UTILIZAREMOS UN STACK(UNA PILA) PARA AHORRARNOS PARTE DE CÓDIGO
		Stack<String> hexadecimal = new Stack<String>();

		return longAHexaRecursiva(hexadecimal, decimal);
	}
	/**
	 * metodo recursivo
	 * @param numerosLista la lista de digitos exadecimales que vamos rellenando
	 * @param num numero original que iremos dividiendo poco a poco para sacar su resto
	 * @return el numero exadecimal
	 */
	public static String longAHexaRecursiva(Stack<String> numerosLista,long num) {

		if(num/16<1) {//SI EL CALCULO DA UN NUMERO MAS PEQUEÑO QUE 1
			numerosLista.push(Integer.toHexString((int) (num)));
			String hexadecimal="";
			while (!numerosLista.empty()) {
				  hexadecimal+=numerosLista.pop();
			}
			
				return hexadecimal;
		}else {//PONEMOS LA LETRA INICIAL DONDE LA FINAL Y VICEVERSA

			numerosLista.push(Integer.toHexString((int) (num%16)));

			return longAHexaRecursiva(numerosLista, num / 16);
		}
	}
	
	//==================================sacaPalabras=========================================

	/**
	 * Metodo que devuelve la llamada al método recursivo. en este método leemos el fichero y lo metemos en un array
	 * @param fichero es el fichero que introducimos como parametro
	 * @return el array de strings que nos dará el metodo recursivo
	 */
	public static ArrayList<String> sacaPalabras(File fichero) {
		ArrayList<String> ficheroAlReves= new ArrayList<String>();
		FileReader fr=null;
		BufferedReader br= null;
		String contenido = "";
		try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).

			fr = new FileReader (fichero);
			br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null) {
	         
	        	 contenido += linea+" ";
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }



		return sacaPalabrasRecursiva(contenido.split(" "), ficheroAlReves, contenido.split(" ").length-1);
	}
	/**
	 * Clase recursiva que nos permite devolver un arraylist con las palabras al reves del que hayamos introducido en el array
	 * @param palabras la lista de palabras que introducimos como parametro
	 * @param palabrasAlReves el arraylist vacio que iremos rellenando con recursividad
	 * @param fin la posicion final que añadiremos en el arraylist poco a poco
	 * @return la lista de palabras al reves en el arraylist
	 */
	public static ArrayList<String> sacaPalabrasRecursiva(String[] palabras,ArrayList<String> palabrasAlReves, int fin) {

		if (fin<0) {
			
			System.out.println(palabrasAlReves.toString());
			
			return palabrasAlReves;
			
		}else {//PONEMOS LA LETRA INICIAL DONDE LA FINAL Y VICEVERSA

			palabrasAlReves.add(palabras[fin]);

			return sacaPalabrasRecursiva(palabras, palabrasAlReves, fin-1);
		}
	}
	
//==================================ordenaQuick=========================================

/**
 * Este método llama a la función recursiva quickSort
 * @param palabrasSinOrden cualquier lista de strings que esté sin ordenar
 * @return
 */
	public static ArrayList<String> ordenaQuick(ArrayList<String> palabrasSinOrden) {

		return quickSort( palabrasSinOrden, 0, palabrasSinOrden.size()-1);
		
	}

	/**
	 * Metodo recursivo que nos ordena la lista alfabeticamente
	 * @param lista de palabras que queremos ordenar
	 * @param inicio posición inicial de la lista
	 * @param fin última posición de la lista
	 * @return lista de Strings ordenada
	 */
	public static ArrayList<String> quickSort(ArrayList<String> lista,   int inicio, int fin) {

		int i = inicio;
		int j = fin;
		String medio = lista.get(inicio + (fin - inicio) / 2);//PALABRA DEL MEDIO
		
		while (i <= j) {
			iteraciones++;

		
		    while (lista.get(i).compareToIgnoreCase(medio) < 0) {//METODO QUE COMPARA LA POSISIÓN DEL ABECEDARIO DE DOS LETRAS
		    	iteraciones++;
		        i++;
		    }
		    
		    while (lista.get(j).compareToIgnoreCase(medio) > 0) {
		    	iteraciones++;
		        j--;
		    }
		    
		    if (i <= j) {        
		    	String temp = lista.get(i);
		    	lista.set(i, lista.get(j));
		    	lista.set(j, temp);
		        i++;
		        j--;
		    }
		}
		
		//LLAMADA RECURSIVA A QUICKSORT
		if (inicio < j) {
			llamadas++;
		    quickSort(lista, inicio, j);
		    
		}
		if (i < fin) {
			llamadas++;
		    quickSort(lista,i, fin);
		    
		}
		return lista;
		
		//ESTE ALGORITMO NO ES MUY EFICIENTE

//		if(inicio>=fin) {
//			return lista;}
//		
//		int i = inicio;
//		int j = fin;
//		
//		if (inicio!=fin) {
//			
//			int pivote;
//			int aux;
//			pivote = inicio;
//			while (inicio!=fin){
//				iteraciones++;
//				while (lista.get(fin).compareToIgnoreCase(lista.get(pivote)) >= 0 && inicio<fin) {
//					iteraciones++;
//					fin--;}
//				while (lista.get(inicio).compareToIgnoreCase(lista.get(pivote)) < 0 && inicio<fin) {
//					iteraciones++;
//					inicio++;}
//				if (inicio!= fin) {
//		        	String temp = lista.get(fin);
//		    		lista.set(fin, lista.get(inicio));
//		        	lista.set(inicio, temp);
//				}
//			}	
//			if(inicio==fin) {
//				llamadas++;
//				quickSort(lista, i,  inicio-1);
//				llamadas++;
//				quickSort(lista,   inicio+1, j);
//			}
//		}else {
//			return lista;
//		}
//		
//		return lista;				
		
    }

	
//==================================buscaPalabra=========================================

	/**
	 * Método que devuelve la última posición en una lista odenada
	 * @param palabrasOrdenadas lista de palabras ordenadas
	 * @param PalabraBuscada palabra que buscamos
	 * @return
	 */
	public static int buscaPalabra(ArrayList<String> palabrasOrdenadas, String PalabraBuscada) {

		return buscaPalabraRecursiva( palabrasOrdenadas, PalabraBuscada, 0, palabrasOrdenadas.size()-1);
	}

	/**
	 * Busqueda recursiva binaria que prioriza la busqueda en la parte final del arraylist
	 * @param lista arraylist donde buscamos
	 * @param valor valor que buscamos
	 * @param ini posicion inicial
	 * @param fin posicion final
	 * @return posicion de la busqueda en el arraylist
	 */
	public static int buscaPalabraRecursiva( ArrayList<String> lista, String valor, int ini, int fin  ) {

		if (ini==fin) {

			if (lista.get(ini).equals(valor)) {
				return ini;
			} else {
				return -1;
			}
		} else {
			int mitad = (ini + fin + 1) / 2;
			if (lista.get(mitad).compareToIgnoreCase(valor) > 0) {
				return buscaPalabraRecursiva( lista, valor, ini, mitad-1 );  // Si no es igual, se desprecia la mitad
			} else { 
				return buscaPalabraRecursiva( lista, valor, mitad, fin ); // Si es <= se incluye la mitad
			}
		}
		
    }
}
