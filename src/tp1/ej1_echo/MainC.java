package tp1.ej1_echo;

import java.util.Scanner;

import tcp.StringClient;

/**
 * @author Mart�n Tom�s Juran
 * @version 1.0, 9 de mar. de 2018
 */
public class MainC {

	public static void main(String[] args) {
		StringClient c = new StringClient("localhost", 5500);
		Scanner sc = new Scanner(System.in);
		c.enviar(sc.nextLine());
		System.out.println("echo: " + c.recibir());
		sc.close();
	}	
}