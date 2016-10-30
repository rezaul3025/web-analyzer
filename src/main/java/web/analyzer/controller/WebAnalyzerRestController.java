package web.analyzer.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST controller class, only contain one url to check link accessibility
 * 
 * @author rkarim
 *
 */

@RestController
public class WebAnalyzerRestController {
	
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

	@RequestMapping(value="/check-link-accessibility")
	public boolean  checkLinkAccessibility(@RequestParam(value="link") String link){
		try {
			URL url = new URL(link);
			URLConnection urlConnection = url.openConnection();

			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestMethod("HEAD");
			httpURLConnection.setRequestProperty("User-Agent",USER_AGENT);

			return (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK);

		} catch (UnknownHostException unknownHostException) {
			return false;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}
}
