import java.util.Random;

/**
 * Modified by Joel Trejo
 * */
public class WorkloadGenerator extends Thread {

	private VMA[] virtual_machine=new VMA[300];

    
	private static final int AVG_INTERARRIVAL_TIME=500;
	private static final int AVG_INTERDEPARTURE_TIME=35000;
	
	private int simulationRun=0;
    private String dataCenterIP;
    
    
    public WorkloadGenerator (String dataCenterIP, int simulationRun){
        this.dataCenterIP = dataCenterIP;
        this.simulationRun=simulationRun;
        this.run();
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
		  //Here we need to subscribe the VMA to the FA. Review if the listVMA is better to be a VMA object
		  //virtual_machine[i].printNew();
	}	
	}catch (Exception ex){
        System.out.println(ex);
     }
		
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
}
