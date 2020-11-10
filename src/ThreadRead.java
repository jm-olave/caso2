
public class ThreadRead extends Thread{
	
	/**
	 * algoritmo que usara el thread para generar el hash
	 */
	private String algo;
	
	/**
	 * ruta del archivo que leera la thread.
	 */
	private String path;
	
	/**
	 * variable estatica que usaran todos los threads que representa la otra clase que funcionara como monitor de estos threads.
	 */
	private static LeerArchivo leer= new LeerArchivo();
	
	/**
	 * constructor, recibe ruta y algoritmo que usara el thread.
	 * @param path, ruta del archivo que la thread revisara
	 * @param algo, algoritmo que se usara para generar hash
	 */
	public ThreadRead(String path, String algo) {
		this.path = path;
		this.algo = algo;
	}
	
	
	@Override
	public void run() {
		//identifica entrada
		String texto = leer.identificar_Entrada(algo, path);
		if(texto != null)
			leer.asignarTexto(texto);
	}
}
