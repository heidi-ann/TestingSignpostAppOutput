import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class SignpostOutputAnalysis {

	public static void main(String[] args) {
		
		try {
			
			//load the bufferedReaders for the files outputed by Signposts, Iperf and Ping
			BufferedReader SignOutput = new BufferedReader (new FileReader("/home/heidihoward/TestingSignpostAppOutput/SignOutput.txt"));
			BufferedReader IperfOutput = new BufferedReader (new FileReader("/home/heidihoward/TestingSignpostAppOutput/IperfOutput.txt"));
			BufferedReader PingDownstreamOutput = new BufferedReader (new FileReader("/home/heidihoward/TestingSignpostAppOutput/PingDownstreamOutput.txt"));
			BufferedReader PingUpstreamOutput = new BufferedReader (new FileReader("/home/heidihoward/TestingSignpostAppOutput/PingUpstreamOutput.txt"));
			
			String str;
			
			//load up the ArrayLists which will hold the data after extraction 
			
			// Latency values collected from Signposts and checked using Ping, split into Upstream and downstream
			ArrayList<Float> TrueDownstreamLatency = new ArrayList<Float> (); /*all latency values stored in ms*/
			ArrayList<Float> SignDownstreamLatency = new ArrayList<Float> (); /*all latency values stored in ms*/
			ArrayList<Float> TrueUpstreamLatency = new ArrayList<Float> (); /*all latency values stored in ms*/
			ArrayList<Float> SignUpstreamLatency = new ArrayList<Float> (); /*all latency values stored in ms*/
			
			// Goodput values collected from Signposts and checked using Iperf UDP 
			ArrayList<Float> TrueDownstreamGoodput = new ArrayList<Float> (); 
			ArrayList<Float> SignDownstreamGoodput = new ArrayList<Float> (); 
			ArrayList<Float> TrueUpstreamGoodput = new ArrayList<Float> (); 
			ArrayList<Float> SignUpstreamGoodput = new ArrayList<Float> (); 
			
			//Jitter values collected from Signposts and checked using Iperf UDP
			ArrayList<Float> TrueDownstreamJitter = new ArrayList<Float> (); 
			ArrayList<Float> SignDownstreamJitter = new ArrayList<Float> (); 
			ArrayList<Float> TrueUpstreamJitter = new ArrayList<Float> (); 
			ArrayList<Float> SignUpstreamJitter = new ArrayList<Float> ();
			
			//load data to ArrayLists from IperfOutput 
			/*TODO get goodput data from IperfOutput and put into TrueDownstreamGoodput and TrueUpstreamGoodput, 
			 * TODO get jitter data from IperfOutput and put into TrueDownstreamJitter and TrueUpstreamJitter
			 * not yet sure how to calucate goodput from iperf output
			 * not yet sure how to take iperf data and seperate into Downstream and Upstream
			 * not yet sure about units			
			 */
			
			
			//load data to ArrayLists from PingDownstreamOutput		
			while ((str = PingDownstreamOutput.readLine()) != null) {
				if (str.startsWith("rtt min/avg/max/mdev =")) {
		    	   String[] RRTline = str.split("="); /* seperates the values and the words */
		    	   String[] RRTvalues = RRTline[1].split("/");
		    	   Float RRTaverage = new Float (RRTvalues[1]);
		    	   TrueDownstreamLatency.add(RRTaverage/2); /*all latency values stored in ms, ping produces results in ms*/
						}
		    }
			
			//load data to ArrayLists from PingUpstreamOutput	
			while ((str = PingUpstreamOutput.readLine()) != null) {
				if (str.startsWith("rtt min/avg/max/mdev =")) {
		    	   String[] RRTline = str.split("="); /* seperates the values and the words */
		    	   String[] RRTvalues = RRTline[1].split("/");
		    	   Float RRTaverage = new Float (RRTvalues[1]);
		    	   TrueUpstreamLatency.add(RRTaverage/2); /*all latency values stored in ms, ping produces results in ms*/
						}
		    }
			
			//load data to ArrrayLists from SignOutput
			while ((str = SignOutput.readLine()) != null) {
			       if (str.contains("Received Latency Downstream")) {
			    	   String[] LatencyLine = str.split(":"); /* seperates the values and the words */
			    	   Float LatencyValue = new Float (LatencyLine[2]);
			    	   SignDownstreamLatency.add(LatencyValue/1000); /*all latency values stored in ms, Signposts produces results in micro secs so divide by 1000 */
			    	   }
			       else if (str.contains("Received Latency Upstream")) {
			    	   String[] LatencyLine = str.split(":"); /* seperates the values and the words */
			    	   Float LatencyValue = new Float (LatencyLine[2]);
			    	   SignUpstreamLatency.add(LatencyValue/1000); /*all latency values stored in ms, Signposts produces results in micro secs so divide by 1000 */ 
			    	   } 
			       else if (str.contains("Received Goodput Downstream")) {
			    	   String[] GoodputLine = str.split(":"); /* seperates the values and the words */
			    	   Float GoodputValue = new Float (GoodputLine[2]);
			    	   SignDownstreamGoodput.add(GoodputValue); /* not yet sure about UNITs for Goodput*/ 
			    	   } 
			       else if (str.contains("Received Goodput Upstream")) {
			    	   String[] GoodputLine = str.split(":"); /* seperates the values and the words */
			    	   Float GoodputValue = new Float (GoodputLine[2]);
			    	   SignUpstreamGoodput.add(GoodputValue); /* not yet sure about UNITs for Goodput*/ 
			    	   } 
			       
			       /*TODO this Jitter data extracted from Signposts is the assumed format, 
			        * but this needs to be checked with the lastest version of the app*/
			       else if (str.contains("Received Jitter Downstream")) {
			    	   String[] JitterLine = str.split(":"); /* seperates the values and the words */
			    	   Float JittertValue = new Float (JitterLine[2]);
			    	   SignDownstreamGoodput.add(JittertValue); /* not yet sure about UNITs for Goodput*/ 
			    	   } 
			       else if (str.contains("Received Jitter Upstream")) {
			    	   String[] JitterLine = str.split(":"); /* seperates the values and the words */
			    	   Float JittertValue = new Float (JitterLine[2]);
			    	   SignUpstreamGoodput.add(JittertValue); /* not yet sure about UNITs for Goodput*/ 
			    	   } 
			    }
			
			
			//Print average values from each of the ArrayList

			System.out.println("Assumed true value of Downstream Latency: "+CalcAverage(TrueDownstreamLatency));
			System.out.println("Assumed true value of Upstream Latency: "+CalcAverage(TrueUpstreamLatency));
			System.out.println("Signpost diagnostic value of Downstream Latency: "+CalcAverage(SignDownstreamLatency));
			System.out.println("Signpost diagnostic value of Upstream Latency: "+CalcAverage(SignUpstreamLatency));			

			System.out.println("Assumed true value of Downstream Goodput: "+CalcAverage(TrueDownstreamGoodput));
			System.out.println("Assumed true value of Upstream Goodput: "+CalcAverage(TrueUpstreamGoodput));
			System.out.println("Signpost diagnostic value of Downstream Goodput: "+CalcAverage(SignDownstreamGoodput));
			System.out.println("Signpost diagnostic value of Upstream Goodput: "+CalcAverage(SignUpstreamGoodput));	
			
			System.out.println("Assumed true value of Downstream Jitter: "+CalcAverage(TrueDownstreamJitter));
			System.out.println("Assumed true value of Upstream Jitter: "+CalcAverage(TrueUpstreamJitter));
			System.out.println("Signpost diagnostic value of Downstream Jitter: "+CalcAverage(SignDownstreamJitter));
			System.out.println("Signpost diagnostic value of Upstream Jitter: "+CalcAverage(SignUpstreamJitter));	
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	static Float CalcAverage (ArrayList<Float> values) {
		Float Total = (float) 0;
		for (int i = 0; i<values.size(); i++) {
			Total += values.get(i);
		}
		return Total / values.size();
	}
	
	
}