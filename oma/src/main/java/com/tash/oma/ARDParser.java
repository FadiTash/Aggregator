package com.tash.oma;

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
		result.append("<table>");
		result.append(addRow("Time", "Title", "Details"));
		final HashSet<String> items = new HashSet<String>();
        for (Element entry : entries) {
             Elements dates = entry.select(".date");
             Elements titles = entry.select(".title");
             Elements subTitles = entry.select(".subtitle");
             items.add(addRow(dates.size() > 0 ? dates.get(0).ownText() : "", titles.size() > 0 ? titles.get(0).ownText() : "" , subTitles.size() > 0 ? subTitles.get(0).ownText() : "" ));
        }
        result.append(String.join("", items));
        result.append("</table>");
        return result.toString();
	}

}
