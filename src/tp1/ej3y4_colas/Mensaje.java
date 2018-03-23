package tp1.ej3y4_colas;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 21 de mar. de 2018
 */
public class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 1329835384949350386L;

	public enum Tipo {
		GET, POST, LIST, ACK, INFO
	}
	
	private Random rn = new Random();
	private volatile static int idGen = 0;
	
	private int id;
	private Tipo tipo;
	private String origen;
	private String destino;
	private String asunto;
	private Date fecha;
	private String body;
	
	public Mensaje(Tipo tipo, String origen, String destino, String asunto, String body) {
		super();
		this.id = getIdGen();
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
		this.asunto = asunto;
		this.fecha = new Date();
		this.body = body;
	}
	
	private synchronized int getIdGen() {
		if (idGen == 0) 
			idGen = rn.nextInt() % 10000;
		return idGen++;
	}

	public int getId() {
		return id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}
	
	public String getAsunto() {
		return asunto;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getBody() {
		return body;
	}
	
}
