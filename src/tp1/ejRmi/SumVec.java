package tp1.ejRmi;

import java.io.Serializable;

import rmi.interfaz.Tarea;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 27 de mar. de 2018
 */
public class SumVec implements Tarea<Integer[]>, Serializable {

	private static final long serialVersionUID = -4602794266331129107L;
	private Integer[] vector1;
	private Integer[] vector2;
	
	public SumVec(Integer[] vector1, Integer[] vector2) {
		super();
		this.vector1 = vector1;
		this.vector2 = vector2;
	}

	/* (non-Javadoc)
	 * @see rmi.interfaz.Tarea#ejecutar()
	 */
	@Override
	public Integer[] ejecutar() {
		Integer[] resultado = new Integer[vector1.length];
		for (int i = 0; i < vector1.length; i++) {
			resultado[i] = vector1[i] + vector2[i];
			vector1[i] = 0;
		}
		System.out.println("¡Me estoy ejecutando desde el servidor!\n"
				+ "¡Cambié el vector1 a 0, pero el cliente no lo ve!");
		return resultado;
	}

}
