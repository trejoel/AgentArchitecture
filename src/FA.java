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
	
	private Vector<Integer> listSMA; //Store the identifier of the SMAS
	private  Vector<Integer> listVMA; //Store the identifier of the SMAS

	/***
	 * The FA does not contain the SMAs nor the VMAs, it just considers how to trace back them. 
	 * */
	
	public FA()
	{
		grupo=0;
		listSMA=new Vector<Integer>();
		listVMA=new Vector<Integer>();
	}
	
	protected void assignVMA(VMA xVMA, SMA yHost){
		yHost.receiveVMA(xVMA);
	}
	
	public void subscribeSMA(int idSMA){
		listSMA.add(new Integer(idSMA));		
	}
	
	//Used when a SMA unsubscribe to such a group
	public void unsubscribeSMA(int idSMA){
		listSMA.remove((Integer)idSMA);		
	}
	
	public void enterNewVMA(int idVMA){
		listVMA.add(new Integer(idVMA));
	}
	
	public void requestSMA(SMA xSMA){
		
	}
		
	public void printSMAS(){
		   for (int i=0;i<listSMA.size();i++){
			   System.out.println("Elemento:"+listSMA.get(i));
		   }
	}

}
