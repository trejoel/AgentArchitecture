

//The idea is to create a socket server object both in FA and in SMAs. This objects transport the VMAs to distribute
//among the SMAs groups

import java.io.*;
import java.net.*;

import sockets.Cliente;
import sockets.Servidor;

public class ChannelServer {

	static final String HOST="localhost";
	static final int PUERTOCLIENTE = 5001;
	//static final int PUERTOSERVICIO = 5001;
	private int PUERTOSERVICIO=5000;
	private int pide=0;
	
	//private VMA machineVirtual;
	
	public ChannelServer (int xPort) { // pide = 0 se comporta como servidor, pide=1 se comporta como cliente
		this.PUERTOSERVICIO=xPort;
		recibe();
	}
	
	
	public void recibe(){
		try{
			ServerSocket skServidor = new ServerSocket(this.PUERTOSERVICIO);
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
	            VMA x,y;
	            x=(VMA)FromClient.readObject();
	            System.out.println("I receive request from Job: "+x.getId());
	            y=new VMA(2,2,2,2,2);	            
	            ToClient.writeObject((VMA)y);	            
	            System.out.println("I send the Job :"+y.getId());		           
	            //communication channels with the client				
			}								
		} catch(Exception e){
			System.out.println(e.getMessage());
		}				
		// as a server
	}
	
	public void envia(String xHost, int xPort,VMA xVMA){
		try{
			Socket skCliente= new Socket(xHost,xPort);
			// as a client
			ObjectOutputStream ToServer = new ObjectOutputStream(skCliente.getOutputStream());
            ObjectInputStream FromServer = new ObjectInputStream(skCliente.getInputStream());            
            VMA yVMA;
            //as a client
            //Send VMA xVMA to the server hosted at xHost at port xPort
			ToServer.writeObject((VMA)xVMA);
			System.out.println("Envie el job: "+xVMA.getId());
			yVMA=(VMA)FromServer.readObject();
			System.out.println("Recibi el jobA:"+yVMA.getId());
			skCliente.close();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}				
		// as a server
	}	
	
	public static void main(String[] args){
		new ChannelServer(5000);

	}
}
