

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

public class LeerArchivo {

	// **************************************************
	// variables
	// **************************************************
	
	/**
	 * variable que indica si ya se encontro la cadena buscada.
	 */
	private static boolean encontrado;
	
	/**
	 * bytes del hash de la cadena buscada.
	 */
	private static byte[] buscado;
	
	/**
	 * texto origen del hash buscado.
	 */
	private static String txtOrigen;
	
	/**
	 * promedio de tiempo de solo el metodo de identificar. Solo fue usada para calcular el promedio.
	 */
	private static long promedio  = 0;

	/**
	 *  Metodo que se encarga de recibir una cadena de texto y una cadena con el nombre de un algoritm,
	 *  mediante eesto  retorna el código criptográfico de hash correspondiente.
	 *  primero convierte el string a bytes y este lo convierte a hash.
	 * @param algorithm algoritmo de cifrado deseado  ej: MD5, SHA512,SHA384 algorithm != null
	 * @param entrada la cadena de texto a cifrar entrada != null
	 * @return cadena de bytes del hash , null = en caso de que se genere una excepcion.
	 */
	public static byte[] generar_codigo(String algorithm, String entrada)
	{
		byte[] buffer = entrada.getBytes();
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(buffer);
			return digest.digest();
		}
		catch (Exception e) {
			return null;
		}
	}


	/**
	 *  Metodo que se encarga de usar un codigo criptografico de hash que se busca y
	 *  recibe una cadena con el nombre de un algoritmo y el path del archivo a revisar. Retorna la cadena que se usa para generar dicho codigo.
	 *  Primero abre el archivo, lee cada linea, con cada linea genera un codigo de hash y lo compara el hash buscado, si corresponde la cadena
	 *  entonces retorna la cadena origen de ese hash, la cual fue leida en el archivo.
	 * @param algo
	 * @param path
	 * @return
	 */
	public String identificar_Entrada(String algo, String path) {

		String textoOrigen = null;
		try {
			BufferedReader bf;
			bf = new BufferedReader(new FileReader(path));

			String txt = bf.readLine();
			/**
			 *  ciclo donde se revisa que el hash que se encuentra en buscado sea igual al hash generado
			 */
			while(txt!= null && !encon())
			{
				//long nanoA = System.nanoTime();
				byte[] hashedTry = generar_codigo(algo, txt);

				if(Arrays.equals(hashedTry, buscado))
				{
					encontrado = true;
					textoOrigen = txt;
				}
				//long nanoB = System.nanoTime();
				//calcularPromedio(nanoA,nanoB);

				txt = bf.readLine();
			}
			bf.close();
			return textoOrigen;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *  Metodo utilizado para hallar la suma de los tiempos que se usara luego para hallar el promedio de tiempo que se toma en
	 *  indentificar si corresponde o no al hash deseado. Debe ser synchronized porque todas las threads acceden a la variable.
	 * @param nanoA tiempo en el estado inicial nanoA != null
	 * @param nanoB tiempo en el estado final nanoB != null
	 */
	public  static synchronized void  calcularPromedio(long nanoA, long nanoB)
	{
		promedio += (nanoB-nanoA);

	}
	
	/**
	 * asigna a la variable txtOrigen un texto.
	 */
	public void asignarTexto(String txt) {
		txtOrigen = txt;
	}
	
	/**
	 * retorna el valor de la variable que identifica si ya se encontro o no la cadena buscada. debe ser synchronized debido a que todos leen esta variable
	 */
	public static synchronized boolean encon() {
		return encontrado;
	}

	/**
	 * metodo usado para imprimir en consola un arreglo de bytes
	 * @param byteArray, arreglo de bytes a imprimir.
	 */
	public static void imprimirHexa(byte[] byteArray) 
	{
		String out = "";
		for (int i = 0; i < byteArray.length; i++) {
			if((byteArray[i] & 0xff) <= 0xf) {
				out += "0";
			}
			out += Integer.toHexString(byteArray[i] & 0xff).toLowerCase();
		}
	
		System.out.println(out);
		
	}
	
	public static void main(String[] args) 
	{
		//ingresa el texto a probar
		System.out.println("Escriba el texto:");
		Scanner sc = new Scanner(System.in);
		String texto = sc.nextLine();

		System.out.println("Texto ingresado: "+texto);

		//recibe algoritmo a usar.
		System.out.println("Escriba el algoritmo de hasheo:");
		String alg = sc.nextLine();

		sc.close();

		//genera codigo de hash de la cadena que se buscara.
		byte[] searchingFor = generar_codigo(alg, texto);

		System.out.println("hash texto: ");

		imprimirHexa(searchingFor);

		buscado = searchingFor;

		//rutas que usara cada thread,
		String[] rutas = new String[28];
		rutas[0] = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+"all"+".txt";

		for (int i = 1; i < rutas.length; i++) {
			rutas[i] = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+ "7" +"----"+ i + ".txt";
		}

		long tiempoA = System.currentTimeMillis();

		// creacion, inicializacion y join de threads.
		ThreadRead[] threads = new ThreadRead[28];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new ThreadRead(rutas[i],alg);

		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		long tiempoB = System.currentTimeMillis();
		System.out.println("Tiempo tomado :"+ (tiempoB-tiempoA));

		//promedio de la parte pedida en el enunciado.
		//System.out.println("promedio " + promedio/28);
//		hashing.ThreadRead thread = new ThreadRead("C:\\Users\\pabli\\eclipse-workspace\\Cifrado\\data\\case2Scrypt"+"all"+".txt", alg);
//		thread.start();
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		System.out.println("cadena encontrada: ");
		if(txtOrigen != null)
			System.out.println(txtOrigen);
		else
			System.out.println("no se encontro cadena del hash");
	}

}
