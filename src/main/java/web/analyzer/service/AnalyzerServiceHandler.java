package web.analyzer.service;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.analyzer.domain.AnalysisResult;
import web.analyzer.domain.Heading;
import web.analyzer.utils.Utils;

@Service
public class AnalyzerServiceHandler implements AnalyzerService {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	private Connection connection;

	@Autowired
	private AnalysisResult analysisResult;

	@Autowired
	private Utils utils;

	@Override
	public void analyze(String url) {
		if (url != null && !url.isEmpty() && (url.startsWith("http://") || url.startsWith("https://"))) {

			connection = Jsoup.connect(url.trim());

			try {
				Document htmlDocument = connection.get();
				int statusCode = connection.response().statusCode();
				String statusMessage = connection.response().statusMessage();
				//analysisResult.setRequestStatusCode(statusCode);
				//analysisResult.setRequestStatusMessage(statusMessage);
				utils.updateAnalysisResultSatus(statusCode,statusMessage, analysisResult, false);
				
				if (statusCode == 200) {

					// System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ :
					// " + htmlDocument.body().text());
					// get doc version
					List<Node> nodes = htmlDocument.childNodes();
					String version = utils.getDocVersion(nodes);
					System.out.println(version);
					analysisResult.setVersion(version);

					// get doc title
					analysisResult.setTitle(htmlDocument.title());

					// process headings
					List<Heading> headings = utils.docHeadingsProcess(htmlDocument);
					analysisResult.setHeadings(headings);
					// System.out.println(headingList);

					// Process links
					utils.getLinks(htmlDocument);

				}
			
			}catch(HttpStatusException he){
				//analysisResult.setRequestStatusCode(he.getStatusCode());
				//analysisResult.setRequestStatusMessage(he.getMessage());
				utils.updateAnalysisResultSatus(he.getStatusCode(),he.getMessage(), analysisResult, true);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//analysisResult.setRequestStatusCode(500);
				//analysisResult.setRequestStatusMessage(e.getLocalizedMessage());
				//System.out.println(e.getMessage()+","+e);
				utils.updateAnalysisResultSatus(500, e.getLocalizedMessage(), analysisResult, true);
			}
		}
	}

	@Override
	public AnalysisResult getAanlysisResult() {
		return analysisResult;
	}
}
