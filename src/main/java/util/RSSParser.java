package util;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import model.RSSItemBean;

public class RSSParser {
	
	/**
	 * @param weburl rss source url
	 * @return the list of {@link RSSItemBean}
	 * test site "https://www.zhihu.com/rss" 
	 */
	public List<RSSItemBean> getAllRSSItemBeans(String weburl) {
		List<RSSItemBean> rssItemBeans = null;
        try {
        	SyndFeed syndFeed = null;
        	SyndFeedInput input = new SyndFeedInput();
        	
        	URLConnection urlConn = new URL(weburl).openConnection();
        	urlConn.setConnectTimeout(5000);
        	urlConn.setRequestProperty("User-Agent",
        	        "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        	
        	syndFeed = input.build(new XmlReader(urlConn));
        	
			List<SyndEntryImpl> entries = syndFeed.getEntries();
            RSSItemBean item = null;
            rssItemBeans = new ArrayList<RSSItemBean>();
            
            for(SyndEntryImpl entry : entries) {
                item = new RSSItemBean();
                item.setTitle(entry.getTitle().trim());
                item.setType(syndFeed.getTitleEx().getValue().trim());
                item.setUri(entry.getUri());
                item.setPubDate(entry.getPublishedDate());
                item.setAuthor(entry.getAuthor());
                System.out.println(item.toString());
                rssItemBeans.add(item);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return rssItemBeans;
	}
}
