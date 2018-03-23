package tp1.ej3y4_colas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import tcp.ServerThread;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class ColasThread extends ServerThread {

	private static ArrayList<Mensaje> cola = new ArrayList<Mensaje>();
	ObjectOutputStream output = null;
	ObjectInputStream input = null;
	String clientHostPort = null;

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		clientHostPort = "[" + client.getInetAddress().getHostAddress()
				+ ":" + client.getPort() + "]";

		System.out.println(clientHostPort + " se ha conectado");

		try {
			output = new ObjectOutputStream(client.getOutputStream());
			input = new ObjectInputStream(client.getInputStream());

			while (true) {
				Object o = input.readObject();
				if (o instanceof Mensaje) {
					Mensaje msg = (Mensaje) o;
					switch (msg.getTipo()) {
					case LIST:
						sendList();
						break;
					case POST:
						receivedMsg(msg);
						break;
					case GET:
						requestMsg(msg);
						break;
					default:
						System.out.println("Se recibió un mensaje pero no pudo procesarse correctamente");
						break;
					}
				} else {
					System.out.println("Se recibió un mensaje pero no pudo procesarse correctamente");
				}
			}

		} catch (java.net.SocketException s) {
			System.out.println(clientHostPort + " se ha desconectado");
		} catch (IOException | ClassNotFoundException e) {
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
	
	/**
	 * Envía la lista de mensajes en la cola
	 */
	private void sendList() {
		System.out.println(clientHostPort + " pidió el listado de mensajes");
		String body = String.format("%-3s%-10s%-10s%-29s%-28s\n----\n",
				"#", "Origen", "Destino", "Asunto", "Fecha");
		for (Mensaje m: cola) {
			body += String.format("%-3d%-10s%-10s%-29s%-28s\n",
					cola.indexOf(m), m.getOrigen(), m.getDestino(), m.getAsunto(), m.getFecha().toString());
		}
		
		try {
			output.writeObject(new Mensaje(Mensaje.Tipo.INFO, "server", "", "", body));
			System.out.println("Se envió a " + clientHostPort + " el listado de mensajes");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Almacena el mensaje en la cola
	 */
	private void receivedMsg(Mensaje msg) {
		try {
			cola.add(msg);
			output.writeObject(new Mensaje(Mensaje.Tipo.ACK, "server", "", "", ""));
			System.out.println("El mensaje #" + cola.indexOf(msg) + " del cliente "
						+ clientHostPort + " se almacenó en la cola");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve el mensaje al Cliente
	 */
	private void requestMsg(Mensaje msg) {
		try {
			int n = Integer.parseInt(msg.getBody());
			if (n < 0 || n >= cola.size()) {
				numNotValid(n);
			} else {
				System.out.println(clientHostPort + " pidió el mensaje #" + n);
				Mensaje enviar = cola.get(n);
				output.writeObject(enviar);
				System.out.println("Se envió el mensaje #" + n + " a " + clientHostPort);
				
				Object o = input.readObject();
				if (o instanceof Mensaje) {
					Mensaje ack = (Mensaje) o;
					if (ack.getTipo() == Mensaje.Tipo.ACK) {
						cola.remove(enviar);
						System.out.println("Se eliminó el mensaje #" + n + " de la cola");
					} else {
						System.out.println(clientHostPort
									+ " puede no haber recibido el mensaje "
									+ enviar.getId());
					}
				}
			}
		} catch (NumberFormatException e) {
			getNotValid(msg.getBody());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void getNotValid(String req) {
		try {
			System.out.println(clientHostPort
					+ " hizo una petición no válida (" + req + ")");
			output.writeObject(new Mensaje(
					Mensaje.Tipo.INFO,
					"server",
					"",
					"Petición no válida",
					"La petición (" + req + ") no es válida\n"));
			input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void numNotValid(int n) {
		try {
			System.out.println(clientHostPort
					+ " pidió un # no válido (" + n + ")");
			output.writeObject(new Mensaje(
					Mensaje.Tipo.INFO,
					"server",
					"",
					"Número de mensaje no válido",
					"El # de mensaje ingresado (" 
								+ n + ") no es válido\n"));
			input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ServerThread copy() {
		return new ColasThread();
	}

}
