package main;

import util.RSSParser;



public class Main {  
	  
    public static void main(String[] args) {  
        RSSParser parser = new RSSParser();
        parser.getAllRSSItemBeans("https://feeds.feedburner.com/zhihu-daily");
    }
    
}