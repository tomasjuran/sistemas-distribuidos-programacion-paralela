package rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 22 de mar. de 2018
 */
public class Cliente {
    public static void main(String args[]) {
    	String host = "localhost";
    	int port = 9000;
        try {
            String name = "TareaGenerica";
            Registry registry = LocateRegistry.getRegistry(host, port);
            Tarea tarea = (Tarea) registry.lookup(name);
            RndNum rnd = new RndNum();
            System.out.println("Número aleatorio: " + tarea.ejecutar(rnd));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
