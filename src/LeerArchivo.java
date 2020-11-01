
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;



public class LeerArchivo {



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

	public String identificar_Entrada(byte[] cadenaCrypto, String algo, String path) {
		if(path.equals("C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+"all"+".txt") )
		{
			System.out.println("sera aca?");
		}
		String textoOrigen = null;
		try {
			BufferedReader bf;
			bf = new BufferedReader(new FileReader(path));

			String txt = bf.readLine();

			while(txt!= null && !encon())
			{

				byte[] hashedTry = generar_codigo(algo, txt);

				if(Arrays.equals(hashedTry, cadenaCrypto))
				{
					ThreadRead.encontrado = true;
					textoOrigen = txt;
				}

				txt = bf.readLine();
			}
			bf.close();
			return textoOrigen;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized boolean encon() {
		return ThreadRead.encontrado;
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
		long a = System.currentTimeMillis();
		byte[] searchingFor = generar_codigo(alg, texto);

		System.out.println("hash texto: ");

		imprimirHexa(searchingFor);

		ThreadRead.algo = alg;
		ThreadRead.buscado = searchingFor;
		long b = System.currentTimeMillis();
		System.out.println(b-a);

		String[] rutas = new String[28];

		rutas[0] = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+"all"+".txt";

	for (int i = 1; i < rutas.length; i++) {
			rutas[i] = "C:\\Users\\Joshua\\IdeaProjects\\Caso2Infracomp\\case2Scrypt"+ "7" +"----"+ i + ".txt";
		}

		ThreadRead[] threads = new ThreadRead[28];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new ThreadRead(rutas[i]);

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

		System.out.println("cadena encontrada: ");
		if(ThreadRead.txtOrigen != null)
			System.out.println(ThreadRead.txtOrigen);
		else
			System.out.println("no se encontro cadena del hash");

	}

}
