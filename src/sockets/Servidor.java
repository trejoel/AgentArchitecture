package sockets;

//The idea is to create a socket server object both in FA and in SMAs. This objects transport the VMAs to distribute
//among the SMAs groups

import java.io.*;
import java.net.*;

public class Servidor {

	static final String HOST="localhost";
	static final int PUERTOCLIENTE = 5001;
	static final int PUERTOSERVICIO=5000;
	
	public Servidor (int pide){ // pide = 0 se comporta como servidor, pide=1 se comporta como cliente
         if (pide==0)  // Es un servidor
        	 escucha(PUERTOSERVICIO);
         else{
        	 request(HOST,PUERTOCLIENTE);        	 
         }
	}
	
	public void escucha(int xPort){
		try{
			ServerSocket skServidor = new ServerSocket(xPort);
			//as a server
			int cont=0;
			while (true && cont<5){
				cont++;
				System.out.println(cont);
				Socket myCliente=skServidor.accept();
				System.out.println("Atiendo a un cliente ");
				//communication channels with the client
				ObjectOutputStream ToClient = new ObjectOutputStream(myCliente.getOutputStream());
	            ObjectInputStream FromClient = new ObjectInputStream(myCliente.getInputStream());
	            Integer x,y;
	            x=(Integer)FromClient.readObject();
	            y=x*x;
	            System.out.println("Objeto remoto:"+x.toString());
	            ToClient.writeObject((Integer)y);	            
	            //communication channels with the client				
			}
			
			/*System.out.println("Escucho en el puerto:"+PUERTOSERVICIO);
			Socket myCliente=skServidor.accept();
			System.out.println("Atiendo a un cliente ");
			OutputStream aux2= myCliente.getOutputStream();
			DataOutputStream flujo2 = new DataOutputStream(aux2);
			flujo2.writeUTF("HOLA CLIENTE");
			flujo2.writeUTF("ADIOS CLIENTE");
			
			
			myCliente.close();
			*/
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}				
		// as a server
	}
	
	public void request(String xHost, int xPort){
		try{
			Socket skCliente= new Socket(xHost,xPort);
			// as a client
			ObjectOutputStream ToServer = new ObjectOutputStream(skCliente.getOutputStream());
            ObjectInputStream FromServer = new ObjectInputStream(skCliente.getInputStream());
            Integer x=new Integer(8);
            Integer y;
			//as a client
			ToServer.writeObject((Integer)x);
			y=(Integer)FromServer.readObject();
			System.out.println("Message from server:"+y);
			skCliente.close();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}				
		// as a server
	}	
	
	public static void main(String[] args){
		new Servidor(0);
	}
}
