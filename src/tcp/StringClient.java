package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 9 de mar. de 2018
 */
public class StringClient {
	private Socket socket = null;
	private PrintWriter output = null; 
	private BufferedReader input = null;
	
	public StringClient(String addr, int port) {
		try {
			socket = new Socket(addr, port);
			System.out.println("Conectado con " + addr + ":" + port);
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
	
	public void enviar(String msg) {
		if (socket != null && !socket.isClosed()) {
			try {
				if (output == null) {
					output = new PrintWriter(socket.getOutputStream(), true);
				}
				output.println(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No existe la conexión o se cerró");
		}
	}
	
	public String recibir() {
		if (socket != null && !socket.isClosed()) {
			try {
				if (input == null) {
					input = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()));
				}
				return input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("No existe la conexión o se cerró");
		return "";
	}
}
