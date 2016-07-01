
import java.util.Hashtable;

public class Testbed {
	//See the specifications of the VMAs in page 12 of the cluster computing journal
	
	private SMA[] SMA_low=new SMA[6];
	private SMA[] SMA_medium=new SMA[8];
	private SMA[] SMA_high=new SMA[6];
	private FA front_agent=new FA();
	private WorkloadGenerator myWorkload;
	//private Hashtable <Integer, SMA> SMAS; //This is maybe not required, we can access to the 
										   //SMA just by typing SMA[x].method. 
	
	private int startTime;
	
	public Testbed(int xstartTime){
        this.startTime=xstartTime;
	}
	
	public int getStartingTime(){
		return this.startTime;
	}
	
	public void start(){   // Start the FA and the SMAs

		
		//Here comes a workloadgenerator object
		
		/*for (int i=0;i<300;i++){
			xCPU_Avaible=generateRandomCPU();
			xMEM_Avaible=generateRandomMEM();
			xstart_time=xstart_time+generateRandomStartTime();			
			xexecution_time= generateRandomExecutionTime();
			virtual_machine[i]=new VMA(i, xCPU_Avaible, xMEM_Avaible, (float)xstart_time, (float)xexecution_time);	
			virtual_machine[i].printNew();
		}	*/
		
		
		
		
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
				
		front_agent.printSMAS();
        //At this point all the SMAS are available
		
		//Generates the 300 VMAs
		myWorkload=new WorkloadGenerator(0,"127.0.0.0",1);
		/**
		 * During this execution, every time a new VMAs enter the simulation, the FA should designates a host SMA
		 * */
		//Generates the 300 VMAs
	}
	

	
	public static void main(String args[]){						
		Testbed Simulation=new Testbed(0);
		Simulation.start();
	}

}
