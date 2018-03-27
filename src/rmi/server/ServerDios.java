package rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmi.interfaz.ITarea;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 22 de mar. de 2018
 */
public class ServerDios {
	public static void main(String[] args) {
    	int port = 9000;
    	int objPort = 8000;
        try {
        	String objName = "TareaGenerica";
            ITarea objPublicado = new TareaGenerica();
            ITarea stub =
                (ITarea) UnicastRemoteObject.exportObject(objPublicado, objPort);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(objName, stub);
            System.out.println("Servidor funcionando en el puerto " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
