import java.util.Random;

/**
 * Modified by Joel Trejo
 * */
public class WorkloadGenerator extends Thread {

	//This class will be the Testbed
	//Added from the testbed class
	private SMA[] mySMA=new SMA[20];
	//private SMA[] SMA_low=new SMA[6];
	//private SMA[] SMA_medium=new SMA[8];
	//private SMA[] SMA_high=new SMA[6];
	private FA front_agent=new FA();	
	private int startTime;
	//Added from the testbed class
	
	private VMA[] virtual_machine=new VMA[300];

    
	private static final int AVG_INTERARRIVAL_TIME=500;
	private static final int AVG_INTERDEPARTURE_TIME=35000;
	
	private int simulationRun=0;
    private String dataCenterIP;
    
    
    public WorkloadGenerator (int xstartTime,String dataCenterIP, int simulationRun){
        this.startTime=xstartTime; //added from testbed class
    	this.dataCenterIP = dataCenterIP;
        this.simulationRun=simulationRun;
    }    

    //method added from testBed class
	public int getStartingTime(){
		return this.startTime;
	}
	
    //method added from testBed class	
	public void initialize(){   // Initialize the FA and the SMAs

		
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
		
		//There exists 6 hosts with 20 CORES and 48 GB. (low)
		
		for (int i=0;i<6;i++){
			mySMA[i]=new SMA(i, 20, 48);
			front_agent.subscribeSMA(i);
			//SMAS.put(i, SMA_low[i]);
		}
		
		//There exists 8 hosts with 40 CORES and 96 GB. (medium)
		
		for (int i=0;i<8;i++){
			mySMA[i]=new SMA(i+6, 40, 96);
			front_agent.subscribeSMA(i+6);
			//SMAS.put(i+6, SMA_low[i]);
		}
		
		//There exists 6 hosts with 60 CORES and 144 GB. (high)
		for (int i=0;i<6;i++){
			mySMA[i]=new SMA(i+14, 60, 144);
			front_agent.subscribeSMA(i+14);
			//SMAS.put(i+14, SMA_low[i]);
		}
				
		front_agent.printSMAS();
        //At this point all the SMAS are available
		
		//Generates the 300 VMAs
		
		//myWorkload=new WorkloadGenerator("127.0.0.0",1);
		
		/**
		 * During this execution, every time a new VMAs enter the simulation, the FA should designates a host SMA
		 * */
		//Generates the 300 VMAs
	}
	
	
	
	private int generateRandomCPU(){
		Random randomGenerator=new Random();
		int randCPU=1;
		//1,2,4,6,8
		int valGen= randomGenerator.nextInt(5);
		switch  (valGen){
		  case 0:  randCPU=1;
		           break;
		  case 1: randCPU=2;
          		  break;
		  case 2: randCPU=4;
  		  		  break;
		  case 3: randCPU=6;
	  		  	   break;
	  	  default: randCPU=8;
	  		  	   break;
		}
		
		return randCPU;
	}
	
	private int generateRandomMEM(){
		Random randomGenerator=new Random();
		int randMEM= randomGenerator.nextInt(21);
		randMEM++;
		return randMEM;
	}
	
	private long generateRandomStartTime(long averageDelayForDeallocation){
		// Based on WorkLoadGenerator
		long delayForAllocation = (long) (AVG_INTERARRIVAL_TIME*(-Math.log(Math.random())));		
		averageDelayForDeallocation=averageDelayForDeallocation+delayForAllocation;		
		return averageDelayForDeallocation;
	}
	
	private long generateRandomExecutionTime(long averageDelayForDeallocation){
		// Based on WorkLoadGenerator
		// Rate of inter-departure time is 35000 ms (it is 35 seconds)
		//We construct a function that determines a value from from 30000 to 40000 starting from the arrival time of the previous machine.
        long delayForDeallocation = (long) (AVG_INTERDEPARTURE_TIME*(-Math.log(Math.random()))); //  Arrival process is Poisson Distributed
        averageDelayForDeallocation=averageDelayForDeallocation+delayForDeallocation;			
		return averageDelayForDeallocation;		
	}	
    
    public void run(){    	
		int xCPU_Avaible,xMEM_Avaible;
		long xstart_time=0;
		long xexecution_time=0;
		long delay=0;
		long old_start_time;
		try{
		for (int i=0;i<300;i++){
		//public VMA(int xId, int xCPU_Avaible, int xMEM_Avaible, float xstart_time, float xexecution_time)
		  xCPU_Avaible=generateRandomCPU();
		  xMEM_Avaible=generateRandomMEM();
		  old_start_time=xstart_time;
		  xstart_time=generateRandomStartTime(xstart_time);			
		  xexecution_time= generateRandomExecutionTime(xexecution_time);
		  delay=xstart_time-old_start_time;
		  Thread.sleep(delay);
		  virtual_machine[i]=new VMA(i, xCPU_Avaible, xMEM_Avaible, (float)xstart_time, (float)xexecution_time);
		  //Only for testing we assign the first VM
		  if (i==0){
			  this.roundRobin(virtual_machine[i],0);
		  }
		//Only for testing we assign the first VM
		  
		  //Here we need to subscribe the VMA to the FA. Review if the listVMA is better to be a VMA object
		  //virtual_machine[i].printNew();
	}	
	}catch (Exception ex){
        System.out.println(ex);
     }
		
    }
    
    
   protected int roundRobin(VMA xVMA, int idSMA){
	   int xSMA=idSMA;
	   if (mySMA[idSMA].acceptVMA(Strategy.RoundRobin, xVMA)){
		     
	   }
	   return xSMA;
   }
    
	public static void main(String args[]){						
		WorkloadGenerator Simulation=new WorkloadGenerator(0,"127.0.0.0",1);
        //Here we have to add the FA and the SMAs
        Simulation.initialize();
        //In this moment the FA and SMAS are stabilished in the simulation
        Simulation.run();
	}    


}
