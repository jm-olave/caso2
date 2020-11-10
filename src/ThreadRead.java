
public class ThreadRead extends Thread{

	//public static boolean encontrado = false;
	
	//public static String txtOrigen;
	
	private String algo;
	
	//public static byte[] buscado;
	
	private String path;
	
	private static LeerArchivo leer= new LeerArchivo();
	
	public ThreadRead(String path, String algo) {
		this.path = path;
		this.algo = algo;
	}
	
//	public static synchronized void setTextoOrigen(String res) {
//		txtOrigen = res;
//	}
	
	@Override
	public void run() {
		String texto = leer.identificar_Entrada(algo, path);
		if(texto != null)
			leer.asignarTexto(texto);
			//setTextoOrigen(texto);

	}
}
