import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DownstreamLatency {

	static BufferedReader PingOutput;
	static ArrayList<Float> PingLatency; //values in ms
	static BufferedReader SignOutput;
	static ArrayList<Float> SignLatency; //values in ms


	
	public static void main(String[] args) {
		
		try {
			//load the bufferedReaders
			PingOutput = new BufferedReader (new FileReader("/home/heidihoward/TestingSignpostAppOutput/SAMPLEDownstreamPingoutput.txt"));
			SignOutput = new BufferedReader (new FileReader("/home/heidihoward/TestingSignpostAppOutput/SAMPLESignoutput.txt"));
			String str;
			PingLatency = new ArrayList<Float> ();
			SignLatency = new ArrayList<Float> ();
					
			//read from PingOutput
			while ((str = PingOutput.readLine()) != null) {
				if (str.startsWith("rtt min/avg/max/mdev =")) {
		    	   String[] RRTline = str.split("="); /* seperates the values and the words */
		    	   String[] RRTvalues = RRTline[1].split("/");
		    	   Float RRTaverage = new Float (RRTvalues[1]);
		    	   PingLatency.add(RRTaverage/2);
						}
		    }
			
			//read from Signpost App logcat file
			while ((str = SignOutput.readLine()) != null) {
			       if (str.contains("Received Latency Downstream")) {
			    	   String[] LatencyLine = str.split(":"); /* seperates the values and the words */
			    	   Float LatencyValue = new Float (LatencyLine[2]);
			    	   SignLatency.add(LatencyValue/1000);
			    	   }
			    }
			
			
			//Print averages 
			Float PingTotal = (float) 0;
			for (int i = 0; i<PingLatency.size(); i++) {
				PingTotal += PingLatency.get(i);
			}
			Float PingLatencyAv = PingTotal / PingLatency.size();
			System.out.println("Latency according to Ping: "+PingLatencyAv);
			
			Float SignTotal = (float) 0;
			for (int i = 0; i<SignLatency.size(); i++) {
				SignTotal += SignLatency.get(i);
			}
			Float SignLatencyAv = SignTotal / SignLatency.size();
			System.out.println("Latency according to Singpost Application: "+SignLatencyAv);
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
}
}
