package main;

import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;



public class Main {  
	  
    public static void main(String[] args) {  
        String weburl = "http://meiwenrishang.com/rss";
        try {  
            SyndFeedInput input = new SyndFeedInput();  
            XmlReader reader = new XmlReader(new URL(weburl));
            SyndFeed feed = input.build(reader);
            //这里的entries就是item的集合,一般是20个  
            List<SyndEntry> entries = feed.getEntries();  
            //这里我演示的是item集合里面的第一个list  
            SyndEntry entry = entries.get(0);  
            System.out.println("title :" + entry.getTitle() );  
            System.out.println("pubDate :" + entry.getPublishedDate() );  
            System.out.println("link :" + entry.getLink());  
            String description = entry.getDescription().getValue();  
            //下面的需要对css,html有了解  
            Document des_value = Jsoup.parse(description);  
            String text = des_value.text();  
            String imgsrc = des_value.select("img[src]").attr("src");  
            System.out.println("description_text = " + text);  
            System.out.println("description_imgsrc=  " + (imgsrc.isEmpty()?"没抓到":imgsrc));  
  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  