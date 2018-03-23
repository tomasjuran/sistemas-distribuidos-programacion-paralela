package tcp;

import java.net.Socket;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public abstract class ServerThread implements Runnable {

	protected Socket client;
	
	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}
	
	public abstract ServerThread copy();
}
