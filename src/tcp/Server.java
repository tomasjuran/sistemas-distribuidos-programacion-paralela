package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class Server {	
	private ServerSocket server = null;
	
	public Server(int port, ServerThread st) {
		try {
			server = new ServerSocket(port);
			System.out.println("Servidor funcionando en el puerto " + port);
		} catch (IOException e1) {
			e1.printStackTrace();
			cerrar();
		}
		
		while (server != null) {
			try {
				Socket client = server.accept();
				ServerThread nst = st.copy();
				nst.setClient(client);
				Thread stThread = new Thread(nst);
				stThread.start();
				
			} catch (IOException e) {
				e.printStackTrace();
				cerrar();
			}
		}
	}
	
	public void cerrar() {
		System.out.println("Goodbye!");
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	@Override
	public void finalize() {
		cerrar();
	}
}