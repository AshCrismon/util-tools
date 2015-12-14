package pers.ash.util.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class JsoupTest {

	@Test
	public void test() {
		String html = "<html><head><title>First parse</title></head>"
				+ "<body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		String title = doc.title();
		String head = doc.head().html();
		String body = doc.body().html();
		String h = doc.attr("html");
		System.out.println(title);
		System.out.println(head);
		System.out.println(body);
		System.out.println(h);
	}
}
