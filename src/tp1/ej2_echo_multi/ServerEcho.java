package tp1.ej2_echo_multi;

import tcp.Server;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class ServerEcho {

	public static void main(String[] args) {
		int port = 9000;
		Server s = new Server(port, new EchoThread());
		s.cerrar();
	}

}
