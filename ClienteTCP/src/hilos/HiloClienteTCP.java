package hilos;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import principal.MyFileWriter;

public class HiloClienteTCP extends Thread{
	
	private Socket cliente;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida; 
	
	private boolean imagenRecibida = false;
	private boolean sePididoImagen = false;
	private boolean imagenVerificada = false;
	public HiloClienteTCP(String IPservidor){
		// le pasamos la ip del servidor  y el puerto
		try {
			
			cliente = new Socket(InetAddress.getByName(IPservidor), 3000);
			obtenerStreams();
			enviarDatos("iniciar conexion");
			
		} catch (UnknownHostException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		do{
			try {
				procesarConexion();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				try {
					cliente.close();
					entrada.close();
					salida.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				break;
			}
			
		}while(true);
		
	}
	
	private void procesarConexion() throws ClassNotFoundException, IOException {
		String mensaje;
		mensaje =(String)entrada.readObject();
		System.out.println(mensaje);

		    MyFileWriter fw = new MyFileWriter();
		    BufferedInputStream bis = new BufferedInputStream(cliente.getInputStream());
		    imagenRecibida = true;
	        String fileName = "image-" + System.currentTimeMillis() + ".jpg";
	        int fileSize = fw.writeFile(fileName, bis);
	       
	        bis.close();
	     
	        
	        
				
	}

	
	
	
	public boolean isSePididoImagen() {
		return sePididoImagen;
	}

	public void setSePididoImagen(boolean sePididoImagen) {
		this.sePididoImagen = sePididoImagen;
	}

	public void enviarDatos(String mensaje) throws IOException {
		
		salida.writeObject(mensaje);
		
	}
	
	private void obtenerStreams() throws IOException {
		salida = new ObjectOutputStream(cliente.getOutputStream());
		salida.flush();//limpia el buffer
		entrada = new ObjectInputStream(cliente.getInputStream());
	}
	
	public void cerrarConexion(){
		try {
			enviarDatos("cerrar conexion");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Conexion termianda");
	}

	public boolean imagenRecibida(){
		return imagenRecibida;
	}
	
	public boolean imagenVerificad(){
		
		return imagenVerificada;
	}
	
	public String sacarHashImgen() throws NoSuchAlgorithmException, IOException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[]  input= null;
		md.update(input);
		byte[] b = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b1: b){
			sb.append(Integer.toHexString(b1 & 0xff).toString());
		}
		
		return sb.toString();
	}
	
}
