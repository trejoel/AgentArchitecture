package sockets;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.UnknownHostException;
import java.io.IOException;

public class Channel
{
    private int port;
    private ArrayList<Channel> contacts;
    Channel preNode;
    Channel postNode;
    private String directoryLocation = "";

    public Channel(int port)
    {
        System.out.println("Canal creado en el puerto "+port);    	
    	this.port = port;
        this.setDirectoryLocation( port+"");
        startClientServer( port );
    }
    
    private void setDirectoryLocation(String xDirLoc){
    	this.directoryLocation=xDirLoc;
    }

    private void sendRequest(String fileName, String host, int port) throws UnknownHostException, IOException
    {
        Socket socket = new Socket(host, port);//machine name, port number
        PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
        out.println(fileName);

        out.close();
        socket.close();

    }

    private void startClientServer( int portNum )
    {
        try 
        {
            // Establish the listen socket.
        	//ServerSocket server = new ServerSocket( portNum );
            ServerSocket server = new ServerSocket( 0 );
            System.out.println("listening on port " + server.getLocalPort());
            while( true )
            {
                
            	// Listen for a TCP connection request.
                Socket connection = server.accept();

                // Construct an object to process the HTTP request message.
                
                HttpRequestHandler request = new HttpRequestHandler( connection );
                
                // Create a new thread to process the request.
                Thread thread = new Thread(request);
                //Thread thread= new Thread("Cualquier Objeto");                
                // Start the thread.
                thread.start();

                System.out.println("Thread started for "+ portNum);
            
            }

        } 
        catch (Exception e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}