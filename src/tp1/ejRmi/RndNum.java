package tp1.ejRmi;

import java.io.Serializable;
import java.util.Random;

import rmi.interfaz.Tarea;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 22 de mar. de 2018
 */
public class RndNum implements Tarea<Integer>, Serializable {

	private static final long serialVersionUID = 7557348222357876230L;

	/* (non-Javadoc)
	 * @see rmi.client.Tarea#ejecutar(rmi.client.ITarea)
	 */
	@Override
	public Integer ejecutar() {
		Random rnd = new Random();
		return rnd.nextInt();
	}

}
