import java.util.ArrayList;
import java.util.Vector;

public class FA {
	
	/**
	 * When a VM arrives to the FA. 
	 * 
	 * **/
	
	private int grupo;
	
	/***
	 * The FA does not contain the SMAs nor the VMAs, it just considers how to trace back them. 
	 * */
	
	private Vector<SMA> listSMA; //Store the identifier of the SMAS
	private  Vector<Integer> listVMA; //Store the identifier of the VMAS and 
    //We will be able to trace a VMA historic by its object hostSMA
	
	/***
	 * The FA does not contain the SMAs nor the VMAs, it just considers how to trace back them. 
	 * */
	
	public FA()
	{
		grupo=0;
		listSMA=new Vector<SMA>();
		listVMA=new Vector<Integer>();
	}
	
	protected void assignVMA(VMA xVMA, SMA yHost){
		yHost.receiveVMA(xVMA);
	}
	
	public void subscribeSMA(SMA newSMA){
		listSMA.add(newSMA);
	}
	
	//Used when a SMA unsubscribe to such a group
	public void unsubscribeSMA(int idSMA){
		listSMA.remove((Integer)idSMA);
	}
	
	
	//This function need to trace the location of the VMA. Please modify the 
	//structure to include the SMA location in the listVMA function
	//Deadline 04-10-2016
	public void enterNewVMA(int idVMA, int idCurrentSMA){
		listVMA.add(new Integer(idVMA));
		//Here we stabilish the distribution policy
		//roundRobin();
	}

	/*
	 * Here we define the scheduling policies
	 * */	
	public void roundRobin(int idVMA, int idCurrentSMA){
		
	}
		

	/*
	 * Here we define the scheduling policies
	 * */		
	
	public void printSMAS(){
		   for (int i=0;i<listSMA.size();i++){
			   System.out.println("Elemento:"+listSMA.get(i).getID());
		   }
	}

}
