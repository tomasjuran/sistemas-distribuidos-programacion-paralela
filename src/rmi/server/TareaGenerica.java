package rmi.server;

import java.rmi.RemoteException;

import rmi.interfaz.ITarea;
import rmi.interfaz.Tarea;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 27 de mar. de 2018
 */
public class TareaGenerica implements ITarea {
	/* (non-Javadoc)
	 * @see rmi.interfaz.ITarea#ejecutarTarea(rmi.interfaz.Tarea)
	 */
	@Override
	public <T> T ejecutarTarea(Tarea<T> t) throws RemoteException {
		return t.ejecutar();
	}

}
