package tp1.ejRmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.interfaz.ITarea;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 22 de mar. de 2018
 */
public class Cliente {
    public static void main(String args[]) {
    	String host = "localhost";
    	int port = 9000;
        try {
            String objName = "TareaGenerica";
            Registry registry = LocateRegistry.getRegistry(host, port);
            ITarea tarea = (ITarea) registry.lookup(objName);
            RndNum rnd = new RndNum();
            System.out.println("Número aleatorio: " + tarea.ejecutarTarea(rnd));
            
            Integer[] vector1 = new Integer[5];
            Integer[] vector2 = new Integer[5];
            for (int i = 0; i < vector1.length; i++) {
            	vector1[i] = tarea.ejecutarTarea(rnd) % 100;
            	vector2[i] = tarea.ejecutarTarea(rnd) % 100;
            }
            
            SumVec sum = new SumVec(vector1, vector2);
            Integer[] resultado = tarea.ejecutarTarea(sum);
            
            String salida = "Suma de vectores:\n  "
            		+ mostrarVector(vector1) + "\n+ "
            		+ mostrarVector(vector2) + "\n= "
            		+ mostrarVector(resultado);
            System.out.println(salida);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    private static String mostrarVector(Integer[] vector) {
    	String resultado = "[";
    	int i;
    	for (i = 0; i < vector.length-1; i++) {
    		resultado += String.format("%3s, ", vector[i].toString());
    	}
    	resultado += String.format("%3s]", vector[i].toString());
    	return resultado;
    }
}
