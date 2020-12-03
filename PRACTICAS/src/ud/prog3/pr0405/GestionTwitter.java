package ud.prog3.pr0405;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

public class GestionTwitter {
	
	static int contadorUsuariosConAmigosSistema = 0;
	
	public static void main(String[] args) {
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			String fileName = "ficheros/prueba.csv";
			CSV.processCSV( new File( fileName ) );
			
			for (Entry<String, UsuarioTwitter> entry : CSV.UsuariosNick.entrySet()) {
			    int amigosEnSistema = 0;
			    int amigosFueraSistema = 0;
			    int tieneAmigosdentro = 0;
				String key = entry.getKey();
			    UsuarioTwitter value = entry.getValue();
			    
			    for (String nicks : value.getFriends()) {
					if (CSV.UsuariosID.containsKey(nicks)) {
						amigosEnSistema++;
						tieneAmigosdentro = 1;
					}else {
						amigosFueraSistema++;
					}
				}
			    
			    contadorUsuariosConAmigosSistema+= tieneAmigosdentro;
			    
			    System.out.println("El usuario " + key + " tiene " + amigosFueraSistema +" amigos fuera del sistema y "+ amigosEnSistema +" amigos dentro del sistema");
			    
			    //TODO parte 7.5 de la practica
			    
			}
			System.out.println("en el numero de usuarios que tienen algun amigo en el sistema es de " + contadorUsuariosConAmigosSistema);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
