package sockets;

import java.util.ArrayList;

public class MasterClientServer 
{
	
	private int count;
	private ArrayList<Channel> arrayOfNodes;
	
	public MasterClientServer(int xNumberOfNodes){
		this.count=xNumberOfNodes;
        ArrayList<Channel> arrayOfNodes = createNodes( count );//only 10 for this case		
	}
	
	
    public static void main( String [] args )
    {
    	MasterClientServer ClientServer=new MasterClientServer(10);
    }

    public static ArrayList<Channel> createNodes( int count)
    {
    	//We require the channels to communicate among the elements of the agent architecture
        System.out.println("Creating a network of "+ count + " channels...");
        ArrayList< Channel > arrayOfNodes = new ArrayList<Channel>();
        
        for( int i =1 ; i<=count; i++)
        {
        	System.out.println("Adding the node:"+i);
            arrayOfNodes.add( new Channel( 0 ) ); //providing 0, will take any free node
        }
        return arrayOfNodes;
    }
}


