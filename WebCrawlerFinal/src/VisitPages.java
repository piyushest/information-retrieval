import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class VisitPages {
	static List<String> newDomain=new ArrayList<String>();
	static List<String> urlNotAllowed = new ArrayList<String>();
	public List<String> crawl(String url,Integer level){
		List<String> addUrl = new ArrayList<String>();
    	addUrl.add(url);
		try{
    	URL myUrl = new URL(url);
    	HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
    	connection.setRequestProperty("User-Agent","Mozilla/5.0");
    	BufferedReader urlReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	if(!(newDomain.contains(myUrl.getHost())))
    	{
    		newDomain.add(myUrl.getHost());
    		String a="http://"+myUrl.getHost();
    		robotTxt(a.concat("/robots.txt"));
    	}
    	if(!(connection.getContentType().contains("text/html"))||(connection.getContentType().contains("pdf"))){
    		return null;
    	}
    	String urlss;
    	if(connection.getResponseCode()!=200){
			  return addUrl;
		  }
    	
    	while((urlss=urlReader.readLine())!= null){
    		Document d=Jsoup.parse(urlss);
    		for( Element element : d.select("a[href]") )    
    		{
    			if((element.attr("href").contains("neu.edu")) ||(element.attr("href").contains("northeastern.edu")))                 
    		    {
    		    	if((addUrl.contains(element.attr("abs:href")))||(addUrl.contains(element.attr("abs:href").concat("#")))||checkAllowed(element.attr("abs:href"))){
    		        continue;
    		        }
    		        else{
         	        	addUrl.add(element.attr("abs:href"));
    		        }
    		    }
    		}
    		}
    	urlReader.close();
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}

    	return addUrl;
    }
	
	private boolean checkAllowed(String attr) {
		try{
		for(int i=0;i<urlNotAllowed.size();i++){
			if(attr.contains(urlNotAllowed.get(i))){
			return true;}
		}
		}
		catch(Exception e){}
		return false;
	}

	public List<String> robotTxt(String url){
		try{
	    	URL myUrl = new URL(url);
	    	HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
	    	connection.setRequestProperty("User-Agent","Mozilla/5.0");
	    	BufferedReader urlReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String urlNotAllowed;
	        int allowAdd=0;
	    	while((urlNotAllowed=urlReader.readLine())!= null){
	    		if(urlNotAllowed.contains("User-agent: *")){
	    			allowAdd=allowAdd+1;
	    		}
	    		if(allowAdd>0){
	    			fetchNotAllowedUrl(urlNotAllowed);
	    		}
	    	}
	    	urlReader.close();
	    	}
	    	catch(Exception e){
	    		System.out.println(e);}
		return urlNotAllowed;
	}
	public static List<String> fetchNotAllowedUrl(String link){
		int startIndex=link.indexOf("Disallow:");
		urlNotAllowed.add(link.substring(startIndex+10));
		return urlNotAllowed;
	}
}
