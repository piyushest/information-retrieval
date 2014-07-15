import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CalculateFrequency {

	public static void main(String[] args) {
        WordsFrequency f=new WordsFrequency();
        f.calculateWordFrequency("C:\\Users\\Piyush\\Desktop\\output.txt");
        f.calculatefreq();
        List<String> temp=new ArrayList<String>();
        List<String> tempFWords=new ArrayList<String>();
        for(Map.Entry<String,Integer> entry:WordsFrequency.exactFreq.entrySet()){
        	temp.add(entry.getKey());
        	if(entry.getKey().charAt(0)=='f'||entry.getKey().charAt(0)=='F'){
        		tempFWords.add(entry.getKey());
        	}
          }
       Map<String,Integer> mostFrequent=f.get25MostFrequent(temp,tempFWords);
       f.calculateAllDetails(mostFrequent);
       System.out.println("*******The Words Starting from F******");
       for(Map.Entry<String,Map<String,Double>> entry:WordsFrequency.allDetailsOfFWords.entrySet()){
    	  System.out.println(entry.getKey());
    	   for(Map.Entry<String,Double> innerEntry:entry.getValue().entrySet()){
     	    System.out.println(innerEntry.getKey()+"/"+innerEntry.getValue());
    	   }
        }
       System.out.println("*******All Words Mixed******");
       for(Map.Entry<String,Map<String,Double>> entry:WordsFrequency.allDetails.entrySet()){
    	   System.out.println(entry.getKey());
    	   for(Map.Entry<String,Double> innerEntry:entry.getValue().entrySet()){
     	    System.out.println(innerEntry.getKey()+"/"+innerEntry.getValue());
    	   }
        }
       System.out.println("The total number of words encountered are "+ WordsFrequency.totalWords);
       System.out.println("The total number of unique words are "+WordsFrequency.exactFreq.size());
       //f.getPairs("C:\\Users\\Piyush\\Desktop\\output.txt");
       f.getTheWordsWithLessThan5();
	}

}
