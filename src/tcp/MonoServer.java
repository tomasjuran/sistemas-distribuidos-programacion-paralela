package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 9 de mar. de 2018
 */
public class MonoServer {	
	private Socket client = null;
	private ServerSocket server = null;
	private PrintWriter output = null; 
	private BufferedReader input = null;
	
	public MonoServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Servidor funcionando en el puerto " + port);
		} catch (IOException e) {
			e.printStackTrace();
			cerrar();
		}
	}
	
	public void cerrar() {
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void finalize() {
		cerrar();
	}
	
	public void aceptar() {
		if (server != null) {
			try {
				System.out.println("Esperando conexión...");
				client = server.accept();
				System.out.println(
						"Se ha conectado el cliente "
						+ client.getInetAddress().getHostAddress()
						+ ":" + client.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void enviar(String msg) {
		if (client != null) {
			try {
				if (output == null) {
					output = new PrintWriter(client.getOutputStream(), true);
				}
				output.println(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String recibir() {
		if (client != null) {
			try {
				if (input == null) {
					input = new BufferedReader(
							new InputStreamReader(
									client.getInputStream()));
				}
				return input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Cliente no conectado");
		return "";
	}
	
}
