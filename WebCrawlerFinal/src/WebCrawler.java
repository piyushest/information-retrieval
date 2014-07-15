import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebCrawler {
	static Map<Integer, List<String>> urlAtAllLevel = new HashMap<Integer, List<String>>();

	public static void main(String[] args) {
		VisitPages v = new VisitPages();
		List<String> uniqueUrl = new ArrayList<String>();
		List<String> uniqueUrls = new ArrayList<String>();
		uniqueUrls = v.crawl("http://www.ccs.neu.edu", 0);
		int length = uniqueUrls.size();
		for (int i = 0; i < length; i++) {
			uniqueUrl.add(uniqueUrls.get(i));
		}
		int numberOfPages = 1;
		urlAtAllLevel.put(0, uniqueUrl);
		while (numberOfPages != 100) {
			uniqueUrls = v.crawl(uniqueUrl.get(numberOfPages), numberOfPages);
			urlAtAllLevel.put(numberOfPages, uniqueUrls);
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println(e);
			}
			if (uniqueUrls == null) {
				uniqueUrls.remove(numberOfPages);
				continue;
			}
			if (uniqueUrls.size() == 0) {
				numberOfPages = numberOfPages + 1;
				continue;
			}
			length = uniqueUrls.size();
			for (int i = 0; i < length; i++) {
				if (uniqueUrl.contains(uniqueUrls.get(i))) {
					continue;
				} else {
					uniqueUrl.add(uniqueUrls.get(i));
				}
			}
			numberOfPages = numberOfPages + 1;
        }
		printFile();
    }

	public static void printFile() {
		try {
			File file = new File("urls.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < 100; i++) {
				List<String> url = urlAtAllLevel.get(i);
				for (int j = 0; j < url.size(); j++) {
					String s = url.get(j);
					bw.write(s + " ");
				}
				bw.newLine();
			}
			bw.close();
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
