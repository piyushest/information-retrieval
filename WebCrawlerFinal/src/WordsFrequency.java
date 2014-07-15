import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsFrequency {
	public static Map<Integer, List<String>> freq = new HashMap<Integer, List<String>>();
	public static Map<String, Integer> exactFreq = new HashMap<String, Integer>();
	public static Map<String, Integer> mostFrequentFWords = new HashMap<String, Integer>();
	public static int totalWords;
	public static Map<String, Map<String, Double>> allDetails = new HashMap<String, Map<String, Double>>();
	public static Map<String, Map<String, Double>> allDetailsOfFWords = new HashMap<String, Map<String, Double>>();
    public static List<Double> rankAlready = new ArrayList<Double>();
    public static List<String> pairs =new ArrayList<String>();
	public void calculateWordFrequency(String file) {
		BufferedReader reader = null;
		Integer len;
		try {
			int i = 0;
			String sCurrentLine;
			reader = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = reader.readLine()) != null) {
				i++;
				len = sCurrentLine.length();
				if (!(freq.get(len) != null)) {
					List<String> wordsOfSameLength = new ArrayList<String>();
					wordsOfSameLength.add(sCurrentLine);
					freq.put(len, wordsOfSameLength);
				} else {
					
					freq.get(len).add(sCurrentLine);
				}
			}
			totalWords = i;
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void calculatefreq() {
		for (int i = 1; i < freq.size(); i++) {
			arrange(freq.get(i));
		}
	}

	public void arrange(List<String> words) {
		Integer fr;
		for (int i = 0; i < words.size(); i++) {
			if (exactFreq.containsKey(words.get(i))) {
				fr = exactFreq.get(words.get(i));
				fr = fr + 1;
				exactFreq.put(words.get(i), fr);
			} else {
				exactFreq.put(words.get(i), 1);
			}
		}
	}

	public Map<String, Integer> get25MostFrequent(List<String> keys,
			List<String> keyForFWords) {
		Map<String, Integer> mostFrequent = new HashMap<String, Integer>();
		for (int i = 0; i < 25; i++) {
			int max = 0;
			int length = keys.size();
			String key = null;
			String keyForF = null;
			for (int j = 0; j < length; j++) {
				if (max < exactFreq.get(keys.get(j))) {
					max = exactFreq.get(keys.get(j));
					key = keys.get(j);
				}
			}
			int maxOfF = 0;
			int lengthOFf = keyForFWords.size();
			for (int j = 0; j < lengthOFf; j++) {
				if (maxOfF < exactFreq.get(keyForFWords.get(j))) {
					maxOfF = exactFreq.get(keyForFWords.get(j));
					keyForF = keyForFWords.get(j);
				}
			}
			mostFrequentFWords.put(keyForF, maxOfF);
			keyForFWords.remove(keyForF);
			mostFrequent.put(key, max);
			keys.remove(key);
		}
		return mostFrequent;
	}

	public void calculateAllDetails(Map<String, Integer> mostFrequent) {
		for (Map.Entry<String, Integer> entry : mostFrequent.entrySet()) {
			Map<String, Double> temp = calculateDetails(entry.getKey(), false,
					mostFrequent);
			temp.put("frequency", (double) entry.getValue());
			allDetails.put(entry.getKey(), temp);
		}
		for (Map.Entry<String, Integer> entry : mostFrequentFWords.entrySet()) {
			Map<String, Double> temp = calculateDetails(entry.getKey(), true,
					mostFrequent);
			temp.put("frequency", (double) entry.getValue());
			allDetailsOfFWords.put(entry.getKey(), temp);
		}
	}

	private Map<String, Double> calculateDetails(String word, boolean isF,
			Map<String, Integer> mostFrequent) {
		Map<String, Double> temp = new HashMap<String, Double>();
		double rank;
		rank = getRank(word, isF, mostFrequent);
		double prob = getProbablity(word, isF, mostFrequent);
		temp.put("rank", rank);
		temp.put("probablity", prob);
		temp.put("product", rank * prob);
		return temp;
	}

	private Double getProbablity(String word, boolean isF,
			Map<String, Integer> mostFrequent) {
		Map<String, Integer> accessor = mostFrequent;
		double probability;
		if (isF) {
			accessor = mostFrequentFWords;
		}
		probability = (double) accessor.get(word) / totalWords;
		return probability;
	}

	public Double getRank(String word, boolean isF,
			Map<String, Integer> mostFrequent) {
		double rank = 1;
		Map<String, Integer> accessor;
		if (isF) {
			accessor = mostFrequentFWords;
			return getRankOfFWords(word);
		} else {
			accessor = mostFrequent;
		}
		List<Integer> freqList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : accessor.entrySet()) {
			if (freqList.contains(entry.getValue())) {
				continue;
			} else {
				freqList.add(entry.getValue());
			}
		}
		int freq = accessor.get(word);
		for (int f : freqList) {
			if (f > freq) {
				rank = rank + 1;
			}
		}
		return rank;
	}

	private Double getRankOfFWords(String word) {
		double rank=1;
		int freqOfWord=mostFrequentFWords.get(word);
		for(Map.Entry<String, Integer> entry : exactFreq.entrySet()){
			if(entry.getKey()== word){
				continue;
			}
		   if(entry.getValue()>=freqOfWord){
			   rank=rank+1;
			   
		   }	
		}
		rank=getCorrectRank(rank);
		
		return rank;
	}

	private double getCorrectRank(double rank) {
		rankAlready.add(rank);
		int occurence=0;
		for(int i=0;i<rankAlready.size();i++){
        	 if(rankAlready.get(i)==rank){
        		 occurence=occurence+1;
        	 }
         }
         if(occurence>=1){
        	 rank=rank+occurence;
        	 rankAlready.add(rank);
         }
		 return rank;   
    }
	
	public void getPairs(String file)
	{
	BufferedReader reader = null;
	
	try {
		int i = 0;
		int unique=0;
		String sCurrentLine;
		reader = new BufferedReader(new FileReader(file));
		while ((sCurrentLine = reader.readLine()) != null) {
			i++;
			if(pairs.contains(sCurrentLine)){
			    continue;	
			}
			pairs.add(sCurrentLine);
			unique=unique+1;
			System.out.println("words encountered "+ i + "Unique Words "+unique);
		}			
	} catch (Exception e) {
		System.out.println(e);
	}
	}
	
	public void getTheWordsWithLessThan5(){
		int totalWords=0;
		for(Map.Entry<String,Integer> innerEntry:exactFreq.entrySet()){
	        if(innerEntry.getValue()<5){
	        	totalWords=totalWords+1;
	        }
         }
		System.out.println("Words less than 5 frequency "+ totalWords);
	}
}


