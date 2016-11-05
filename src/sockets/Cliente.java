package sockets;

// The idea is to create a socket server object both in FA and in SMAs. This objects transport the VMAs to distribute
//among the SMAs groups
// First we create a set of threads clients to access the server

import java.io.*;
import java.net.*;

public class Cliente implements Runnable {
    //Solo agregue este comentario
	static final String HOST="localhost";
	static final int PUERTOCLIENTE = 5000;
	static final int PUERTOSERVICIO=5001;
	
	private int pide=0;
	
	public Cliente (int xpide){ // pide = 0 se comporta como servidor, pide=1 se comporta como cliente
       pide=xpide; //By default it is a client
	}
	
	public void run(){
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
			
			while (true){
				Socket myCliente=skServidor.accept();
				System.out.println("Atiendo a un cliente ");
				//communication channels with the client
				ObjectOutputStream ToClient = new ObjectOutputStream(myCliente.getOutputStream());
	            ObjectInputStream FromClient = new ObjectInputStream(myCliente.getInputStream());
	            Integer x,y;
	            x=(Integer)FromClient.readObject();
	            y=x*2;
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
            Integer x=new Integer((int)(Math.random() * 100));
            Integer y;
			//as a client
			ToServer.writeObject((Integer)x);
			y=(Integer)FromServer.readObject();
			System.out.println("Message from server:"+y);
			skCliente.close();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}		
		//System.out.println("SALIO");
		// as a server
	}	
	
	public static void main(String[] args){
		Thread[] T=new Thread[5];
		for (int i=0;i<5;i++){
			T[i]=new Thread(new Cliente(1));
			T[i].start();
		}
		//(new  Thread(new Cliente(1))).start();		
	}
}