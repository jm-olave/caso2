import java.io.*;

class EscrituraArchivo {


	/**
	 * printwriter que se usara para escribir en el archivo
	 */
	private  PrintWriter  writerObj1;
	
	/**
	 * caracteres a usar para la creacion de la cadena.
	 */
	private  static char[] set1 = "abcdefghijklmnñopqrstuvwxyz".toCharArray();

	/**
	 * constructor, recibe como parametro la ruta del archivo en el cual se escribiran las cadenas.
	 * @param path, la ruta del archivo.
	 */
	public EscrituraArchivo( String path)
	{

		try {
			writerObj1 = new PrintWriter(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * escribe todas las cadenas posibles con los caracteres dados y de longitud menor a un numero k.
	 * @param set, caracteres a combinar.
	 * @param k, limite de longitud de las cadenas.
	 */
	public  void escribirCadenasLongitudMenor(char[] set, int k)
	{
		int n = set.length;

		for(int i = 1; i<k;i++)
		{
			escribirCadena(set, "", n, i);
		}
		
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
	public void escribirCadena(char[] set,String prefijo,int n, int k)
	{
		if (k == 0)
		{
			writerObj1.write(prefijo + '\n');
			return;
		}


		for (int i = 0; i < n; ++i)
		{
			String newPrefix = prefijo + set[i];
			escribirCadena(set, newPrefix,
					n, k - 1);
		}
	}

	public static void main(String[] args) throws FileNotFoundException 
	{
		System.out.println("Empieza a escribir el archivo ");

		String path = "C:\\Users\\pabli\\eclipse-workspace\\Cifrado\\data\\case2Scrypt"+"all"+".txt";
		
		EscrituraArchivo writer = new EscrituraArchivo(path);
		
		//aqui al ser 7 entonces haria todas las cadenas hasta longitud 6
		writer.escribirCadenasLongitudMenor(set1,7);

	}
}

