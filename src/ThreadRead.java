import java.awt.*;

public class ThreadRead extends Thread{

	public static boolean encontrado = false;
	
	public static String txtOrigen;
	
	public static String algo;
	
	public static byte[] buscado;
	
	private String path = "";
	
	private  LeerArchivo leer= new LeerArchivo();
	
	public ThreadRead(String path) {
		this.path = path;
	}
	
	public  synchronized void setTextoOrigen(String res) {
		txtOrigen = res;
	}


	public void run() {

		String texto = leer.identificar_Entrada(buscado, algo, path);
		System.out.println("null?" + texto);
		if(texto != null)
		{
			setTextoOrigen(texto);
		}



	}
}
