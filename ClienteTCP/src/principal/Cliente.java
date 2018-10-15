package principal;

import java.io.IOException;
import java.util.Scanner;

import hilos.HiloClienteTCP;

public class Cliente {

	static HiloClienteTCP hc;
	static boolean fin = false;
	
	static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
	
		hc = new HiloClienteTCP("157.253.205.49");
		hc.start();
		do{
//			System.out.println("Elija una opción");
//			System.out.println("1) Enviar mensaje");
//			System.out.println("3) traer Imagen");
//			System.out.println("2) Cerrar conexión");
//			
//			int opc= s.nextInt();
//			
//			if(opc== 1){
//				try {
//					
//					hc.enviarDatos("Mensaje enviado desde el cliente");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if(opc== 3){
//				try {
//					
//					hc.enviarDatos("imagen");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			if(opc== 2){
//				fin = !fin;
//			}
//			
			if(hc.imagenRecibida() == true)
			{
				
				try {
					hc.enviarDatos("imagenReci");
				} catch (IOException e) {
					e.printStackTrace();
				}
				fin = !fin;
			}
			
			if(hc.isSePididoImagen() == false){
				hc.setSePididoImagen(true);
				try {
					
					hc.enviarDatos("imagen");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			

			System.out.println(" ");
			
			
		}while(!fin);
		
		hc.cerrarConexion();
	}
	
	
}
