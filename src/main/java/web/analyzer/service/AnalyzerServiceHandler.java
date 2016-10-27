package web.analyzer.service;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerServiceHandler implements AnalyzerService {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	private Connection connection;

	@Override
	public AnalyzerService analyze(String url) {
		// TODO Auto-generated method stub
		connection = Jsoup.connect(url);
		try {
			Document htmlDocument = connection.get();
			if (connection.response().statusCode() == 200) {
				
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + htmlDocument.body().text());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
		

		return this;
	}

	@Override
	public Integer getRequestStatus() {
		// TODO Auto-generated method stub
		return connection.response().statusCode();
	}

}
