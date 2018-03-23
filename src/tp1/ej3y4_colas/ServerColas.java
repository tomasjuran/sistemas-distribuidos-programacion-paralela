package tp1.ej3y4_colas;

import tcp.Server;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class ServerColas {

	public static void main(String[] args) {
		int port = 9000;
		Server server = new Server(port, new ColasThread());
		server.cerrar();
	}

}
