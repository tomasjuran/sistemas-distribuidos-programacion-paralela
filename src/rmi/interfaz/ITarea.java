package rmi.interfaz;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 22 de mar. de 2018
 */
public interface ITarea extends Remote {
	public <T> T ejecutarTarea(Tarea<T> t) throws RemoteException;
}
