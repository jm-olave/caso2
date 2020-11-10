import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class EscrituraArchivo7 extends Thread{

	/**
	 * printwriter que se usara para escribir en el archivo
	 */
	private PrintWriter writerObj1;
	
	/**
	 * caracteres a usar para la creacion de la cadena.
	 */
	private char[] set1 = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
	
	/**
	 * longitud cadena
	 */
	private int k = 0;
	
	/**
	 * archivo actual de la thread.
	 */
	private int archivoActual = 0;

	/**
	 * constructor, recibe la longitud, la ruta del archivo que el thread usara y el numero del archivo actual. Crea el printwriter a usar para el archivo.
	 * @param k longitud de las cadenas
	 * @param path ruta del archivo a usar.
	 * @param archivoActual, numero del archivo actual.
	 */
	public EscrituraArchivo7(int k, String path, int archivoActual)
	{
		this.k = k;
		this.archivoActual = archivoActual;
		try {
			writerObj1 = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * escribe las cadenas de longitud k usando los caracteres de un set. llama al metodo de escribir cadena con un prefijo que sera cada letra
	 * del alfabeto, de manera que cada thread escriba solo las que inician con una letra especifica. se resta en 1 el k porque ya se tiene un prefijo.
	 * por ultimo cierra el printwriter usado.
	 * @param set, set de caracteres que se combinaran.
	 * @param k, longitud de las cadenas que se escribiran.
	 */
	public void escribirCadenasLongitudMenor(char[] set, int k)
	{
		int n = set.length;
		escribirCadena(set,""+set[archivoActual], n, k-1);
		writerObj1.flush();
		writerObj1.close();
	}

	/**
	 * de manera recursiva escribe todas las cadenas posibles de la combinacion de un set de caracteres, de longitud k.
	 * utiliza un prefijo en cada recursion que significa la cadena anterior a la que le agrega un caracter diferente en cada recursion hasta la longitud limite.
	 * Lo anterior lo realiza n veces debido a los n caracteres del set.
	 * @param set, arrecglo de caracteres a usar para las combinaciones.
	 * @param prefijo, cadena a la que le agrega un caracter diferente cada recursion hasta la longitud limite
	 * @param n, numero de caracteres del set.
	 * @param k, limite de longitud de las cadenas.
	 */
	public void escribirCadena(char[] set, String prefix, int n, int k)
	{
		if (k == 0)
		{
			writerObj1.write(prefix + '\n');
			return;
		}

		for (int i = 0; i < n; ++i)
		{
			String newPrefix = prefix + set[i];
			escribirCadena(set, newPrefix,
					n, k - 1);
		}
	}

	/**
	 * metodo que usara cada thread
	 */
	public void run() 
	{
		escribirCadenasLongitudMenor(set1, k);
	}

	
	public  static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Empieza a escribir el archivo ");

		EscrituraArchivo7[] aray = new EscrituraArchivo7[27];
		
		//A cada thread se le pasara la ruta de su archivo en el que escribira. Tambien el i-1 sera la posicion del archivo, para que cada archivo tenga solo las
		//cadenas que empiezan con una letra en especifico.
		for(int i = 1; i<27+1;i++)
		{
			String path = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+ "7" +"----"+ i + ".txt";

			EscrituraArchivo7 thread = new EscrituraArchivo7(7,path,i-1);
			aray[i-1] = thread;

		}
		for(int k = 0;k<27;k++) {
			aray[k].start();
		}

	}
}

