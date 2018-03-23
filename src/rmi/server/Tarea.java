package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 22 de mar. de 2018
 */
public interface Tarea extends Remote {
	public <T> T ejecutar(ITarea<T> t) throws RemoteException;
}
