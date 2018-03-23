package tp1.ej3y4_colas;

import java.util.Scanner;

import tcp.ObjectClient;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class ClientColas {
	
	public static void main(String[] args) {
		String addr = "localhost";
		int port = 9000;
		ObjectClient sock = new ObjectClient(addr, port);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Bienvenido al servicio de mensajería.\n"
				+ "Ingrese \"?\" para consultar los comandos disponibles");
		try {
			while (true) {
				switch (sc.nextLine()) {
				case "?":
					System.out.println(
							"post - escribir un mensaje nuevo\n"
							+ "list - listar mensajes en la cola\n"
							+ "get - descargar un mensaje\n");
					break;
				case "post":
					postMsg(sock, sc);
					break;
				case "list":
					listar(sock);
					break;
				case "get":
					getMsg(sock, sc);
					break;
				default:
					System.out.println("El comando ingresado no es válido."
							+ " Ingrese \"?\" para consultar los comandos disponibles");
					break;
				}
			}
		} finally {
			sc.close();
			sock.cerrar();
		}
	}
	
	private static void postMsg(ObjectClient sock, Scanner sc) {
		System.out.println("-- Escribir un mensaje nuevo --");
		System.out.println("Ingrese su nombre:");
		String origen = sc.nextLine();
		System.out.println("Ingrese el destino:");
		String destino = sc.nextLine();
		System.out.println("Ingrese el asunto:");
		String asunto = sc.nextLine();
		System.out.println("Escriba el mensaje (termina con doble Enter):");
		String body = "";
		String chunk = sc.nextLine();
		while (!chunk.equals("")) {
			body += chunk + "\n";
			chunk = sc.nextLine();
		}
		sock.enviar(new Mensaje(Mensaje.Tipo.POST, origen, destino, asunto, body));
		
		Object o = sock.recibir();
		if (o instanceof Mensaje) {
			Mensaje ack = (Mensaje) o;
			if (ack.getTipo() == Mensaje.Tipo.ACK) {
				System.out.println("¡El mensaje se envió con éxito!");
			} else {
				System.out.println("El servidor pudo no haber recibido el mensaje");
			}
		}
	}
	
	private static void listar(ObjectClient sock) {
		sock.enviar(new Mensaje(Mensaje.Tipo.LIST, "", "server", "", ""));
		Object o = sock.recibir();
		if (o instanceof Mensaje) {
			Mensaje msg = (Mensaje) o;
			System.out.println(msg.getBody());
		} else {
			System.out.println("Ocurrió un error al procesar el mensaje");
		}
	}
	
	private static void getMsg(ObjectClient sock, Scanner sc) {
		System.out.println("Ingrese el # del mensaje que quiere descargar:");
		sock.enviar(new Mensaje(Mensaje.Tipo.GET, "", "server", "", sc.nextLine()));
		Object o = sock.recibir();
		if (o instanceof Mensaje) {
			Mensaje msg = (Mensaje) o;
			System.out.println("Origen: " + msg.getOrigen());
			System.out.println("Destino: " + msg.getDestino());
			System.out.println("Asunto: " + msg.getAsunto());
			System.out.println("Fecha: " + msg.getFecha().toString());
			System.out.println("--\n"+ msg.getBody() + "--");
			sock.enviar(new Mensaje(Mensaje.Tipo.ACK, "", "server", "", ""));
		} else {
			System.out.println("Ocurrió un error al procesar el mensaje");
		}
	}
	
}
