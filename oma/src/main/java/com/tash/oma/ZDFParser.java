package com.tash.oma;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ZDFParser extends ChannelParser {
	
	public ZDFParser(int month) {
		super(month);
	}
	
	public String getDateFormat() {
		return "yyyy-MM-dd";
	}
	
	public String getChannelName() {
		return "ZDF";
	}
	
	protected String getUrl() {
		return "https://www.zdf.de/live-tv?airtimeDate=###day###";
	}

	@Override
	protected String analyze(Document doc, String date) {
		final StringBuilder result = new StringBuilder();
		result.append("<table>");
		result.append(addRow("Time", "Title"));

		final Elements entries = doc.select("section.timeline-ZDF");
		final Elements lis = entries.select("li");
		for (Element li : lis) {
			Element a = li.selectFirst("a");
			Element s = li.selectFirst("span.time");
			result.append(addRow(a != null ? a.ownText() : "", s != null ? s.ownText() : "" ));
		}
        result.append("</table>");
        return result.toString();
	}
}