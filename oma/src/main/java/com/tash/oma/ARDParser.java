package com.tash.oma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ARDParser extends ChannelParser {

	public ARDParser(int month) {
		super(month);
	}
	
	public String getChannelName() {
		return "ARD";
	}

	public String getDateFormat() {
		return "dd.MM.yyyy";
	}

	protected String getUrl() {
		return "http://programm.ard.de/TV/Programm/Sender?datum=###day###&hour=0&sender=28106";
	}

	@Override
	protected String analyze(Document doc, String date) {
		final Elements entries = doc.select(".accordion-item .row");
		final StringBuilder result = new StringBuilder();
		result.append("<table class='ard'>");
		result.append(addRow("Time", "Title"));
		final HashSet<String> items = new HashSet<>();
        for (Element entry : entries) {
             Elements dates = entry.select(".date");
             Elements titles = entry.select(".title");
             if (dates.size() <= 0 ||  titles.size() <= 0) {
            	 continue;
             }
             items.add(addRow(dates.get(0).ownText(),titles.get(0).ownText()));
        }
        
        ArrayList<String> out = new ArrayList<>(items);
        Collections.sort(out);
        result.append(String.join("", out));
        result.append("</table>");
        return result.toString();
	}

}
