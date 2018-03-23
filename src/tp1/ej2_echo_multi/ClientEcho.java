package tp1.ej2_echo_multi;

import java.util.Scanner;

import tcp.StringClient;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class ClientEcho {
	
	public static void main(String[] args) {
		String addr = "localhost";
		int port = 9000;
		StringClient c = new StringClient(addr, port);
		Scanner sc = new Scanner(System.in);
		try {
			while (true) {
				System.out.println(c.recibir());
				c.enviar(sc.nextLine());
			}
		} finally {
			sc.close();
		}
	}
}
