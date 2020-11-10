

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

public class LeerArchivo {

	// **************************************************
	// variables
	// **************************************************
	private static boolean encontrado;
	private static byte[] buscado;
	private static String txtOrigen;
	private static long promedio  = 0;

	/**
	 *  Metodo que se encarga de recibir una cadena de texto y una cadena con el nombre de un algoritm,
	 *  mediante eesto  retorna el código criptográfico de hash correspondiente.
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
	 *  Metodo que se encarga de recibir un código criptográfico de hash y
	 *  una cadena con el nombre de un algoritmo y retorna la cadena que se usó para generar dicho código.
	 * @param algo
	 * @param path
	 * @return
	 */
	public String identificar_Entrada(String algo, String path) { //byte[] cadenaCrypto,{

		String textoOrigen = null;
		try {
			BufferedReader bf;
			bf = new BufferedReader(new FileReader(path));

			String txt = bf.readLine();
			/**
			 *  ciclo donde se revisa que el hash que se encuentra en buscado sea igual al hash generado
			 */
//			while(txt!= null && !encon())
			{
				long nanoA = System.nanoTime();
				byte[] hashedTry = generar_codigo(algo, txt);

				if(Arrays.equals(hashedTry, buscado))
				{
					//ThreadRead.encontrado = true;
					encontrado = true;
					textoOrigen = txt;
				}
				long nanoB = System.nanoTime();
				calcularPromedio(nanoA,nanoB);

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
	 *  Metodo utilizado para hallar el promedio de tiempo que se toma en
	 *  indentificar si corresponde al hash deseado.
	 * @param nanoA tiempo en el estado inicial nanoA != null
	 * @param nanoB tiempo en el estado final nanoB != null
	 */

	public  static synchronized void  calcularPromedio(long nanoA, long nanoB)
	{
		promedio += (nanoB-nanoA);

	}
	public void asignarTexto(String txt) {
		txtOrigen = txt;
	}
	
	public static synchronized boolean encon() {
		return encontrado;
	}

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
		System.out.println("Escriba el texto:");
		Scanner sc = new Scanner(System.in);
		String texto = sc.nextLine();

		System.out.println("Texto ingresado: "+texto);

		System.out.println("Escriba el algoritmo de hasheo:");
		String alg = sc.nextLine();

		sc.close();

		byte[] searchingFor = generar_codigo(alg, texto);

		System.out.println("hash texto: ");

		imprimirHexa(searchingFor);

		buscado = searchingFor;

		String[] rutas = new String[28];
		rutas[0] = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+"all"+".txt";

		for (int i = 1; i < rutas.length; i++) {
			rutas[i] = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+ "7" +"----"+ i + ".txt";
		}

		long tiempoA = System.currentTimeMillis();

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

		System.out.println("promedio" + promedio/28);
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
