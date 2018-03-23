package tp1.ej1_echo;

import tcp.MonoServer;

/**
 * @author Mart�n Tom�s Juran
 * @version 1.0, 9 de mar. de 2018
 */
public class MainS {

	public static void main(String[] args) {
		MonoServer s = new MonoServer(5500);
		s.aceptar();
		s.enviar(s.recibir());
	}

}
