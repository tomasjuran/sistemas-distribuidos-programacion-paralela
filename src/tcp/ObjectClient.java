package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class ObjectClient {
	private Socket socket = null;
	private ObjectOutputStream output = null; 
	private ObjectInputStream input = null;
	
	public ObjectClient(String addr, int port) {
		try {
			socket = new Socket(addr, port);
			System.out.println("Conectado con " + addr + ":" + port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			cerrar();
		}
	}
	
	public void cerrar() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void finalize() {
		cerrar();
	}
	
	public void enviar(Object obj) {
		if (socket != null && !socket.isClosed()) {
			try {
				output.writeObject(obj);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No existe la conexión o se cerró");
		}
	}
	
	public Object recibir() {
		if (socket != null) {
			try {
				return input.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println("No existe la conexión o se cerró");
		return null;
	}
}
