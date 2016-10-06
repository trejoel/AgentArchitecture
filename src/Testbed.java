
import java.util.Hashtable;

public class Testbed extends Thread {
	//See the specifications of the VMAs in page 12 of the cluster computing journal
	
	private int simulationRun=0;
    private String dataCenterIP;	
	
	private SMA[] SMA_low=new SMA[6];
	private SMA[] SMA_medium=new SMA[8];
	private SMA[] SMA_high=new SMA[6];
	private VMA[] virtual_machine=new VMA[300];
	private FA front_agent=new FA();
	private WorkloadGenerator myWorkload;
	//private Hashtable <Integer, SMA> SMAS; //This is maybe not required, we can access to the 
										   //SMA just by typing SMA[x].method. 
	
	private int startTime;
	
	public Testbed(int xstartTime, String dataCenterIP, int simulationRun){
        this.startTime=xstartTime;
        this.dataCenterIP = dataCenterIP;
        this.simulationRun=simulationRun;        
	}
	
	public int getStartingTime(){
		return this.startTime;
	}
	
	public void start(){   // Start the FA and the SMAs

		
		
		/**
		 * Create the SMAS
		 * */
		
		
		//While created, the SMA are added to the FA
		
		//There exists 6 hosts with 20 CORES and 48 GB.
		
		for (int i=0;i<6;i++){
			SMA_low[i]=new SMA(i, 20, 48);
			front_agent.subscribeSMA(i);
			//SMAS.put(i, SMA_low[i]);
		}
		
		//There exists 8 hosts with 40 CORES and 96 GB.
		
		for (int i=0;i<8;i++){
			SMA_medium[i]=new SMA(i+6, 40, 96);
			front_agent.subscribeSMA(i+6);
			//SMAS.put(i+6, SMA_low[i]);
		}
		
		//There exists 6 hosts with 60 CORES and 144 GB.
		for (int i=0;i<6;i++){
			SMA_high[i]=new SMA(i+14, 60, 144);
			front_agent.subscribeSMA(i+14);
			//SMAS.put(i+14, SMA_low[i]);
		}
		this.run();
				
		front_agent.printSMAS();
        //At this point all the SMAS are available
		
		//Generates the 300 VMAs
		//We need that the WorkLoadGenerator returns one by one the
		//VMA,
		// First choice. Bring the VMA to this code
		//Second choice. Update the WorkLoadGenerator to be here

		//Generates the 300 VMAs
	}
	
	/*Code added 2016 October 6th**/
	
    public void run(){    	
		int xCPU_Avaible,xMEM_Avaible;
		long xstart_time=0;
		long xexecution_time=0;
		long delay=0;
		long old_start_time;
		myWorkload=new WorkloadGenerator();
		/**
		 * During this execution, every time a new VMAs enter the simulation, the FA should designates a host SMA
		 * */		
		try{
		for (int i=0;i<300;i++){
		//public VMA(int xId, int xCPU_Avaible, int xMEM_Avaible, float xstart_time, float xexecution_time)
		  xCPU_Avaible=myWorkload.generateRandomCPU();
		  xMEM_Avaible=myWorkload.generateRandomMEM();
		  old_start_time=xstart_time;
		  xstart_time=myWorkload.generateRandomStartTime(xstart_time);			
		  xexecution_time= myWorkload.generateRandomExecutionTime(xexecution_time);
		  delay=xstart_time-old_start_time;
		  Thread.sleep(delay);
		  virtual_machine[i]=new VMA(i, xCPU_Avaible, xMEM_Avaible, (float)xstart_time, (float)xexecution_time);
		  //Here we need to subscribe the VMA to the FA. Review if the listVMA is better to be a VMA object
		  //virtual_machine[i].printVMA();
	}	
	}catch (Exception ex){
        System.out.println(ex);
     }
		
    }



	/*Code added 2016 October 6th**/
	

	
	public static void main(String args[]){						
		Testbed Simulation=new Testbed(0,"127.0.0.0",1);
		Simulation.start();
	}

}
