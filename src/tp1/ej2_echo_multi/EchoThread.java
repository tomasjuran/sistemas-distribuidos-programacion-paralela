package tp1.ej2_echo_multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import tcp.ServerThread;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class EchoThread extends ServerThread {
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String clientHostPort = client.getInetAddress().getHostAddress()
				+ ":" + client.getPort();
		
		System.out.println(clientHostPort + " se ha conectado");

		BufferedReader input = null;
		PrintWriter output = null;

		try {
			input = new BufferedReader(
					new InputStreamReader(
							client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(), true);
			
			output.println("¡Bienvenido al Echo Server! Comience a escribir:"
					+ " (presione Enter para enviar el mensaje)");
			
			while (true) {
				String msg = input.readLine();
				output.println("echo: " + msg);
				System.out.println("[" + clientHostPort +"] " + msg);
			}
			
		} catch (java.net.SocketException s) {
			System.out.println(clientHostPort + " se ha desconectado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ServerThread copy() {
		return new EchoThread();
	}
}
