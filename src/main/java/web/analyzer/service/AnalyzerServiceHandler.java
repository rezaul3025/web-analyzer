package web.analyzer.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.analyzer.domain.AnalysisResult;
import web.analyzer.domain.Heading;
import web.analyzer.domain.LinkResult;
import web.analyzer.utils.Utils;

@Service
public class AnalyzerServiceHandler implements AnalyzerService {
	
    private Log LOG = LogFactory.getLog(AnalyzerServiceHandler.class);

	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";

	private Connection connection;

	@Autowired
	private AnalysisResult analysisResult;

	@Autowired
	private Utils utils;

	@Override
	public void analyze(String url) {
		if (utils.isValidUrl(url)) {
			LOG.info("Start process request page");
			LOG.info("===========================================================");
			connection = Jsoup.connect(url.trim());
			try {

				Document htmlDocument = connection.timeout(10 * 1000).userAgent(USER_AGENT).get();
				int statusCode = connection.response().statusCode();
				String statusMessage = connection.response().statusMessage();
				utils.updateAnalysisResultSatus(statusCode, statusMessage, analysisResult, false);
				
				if (statusCode == 200) {
					// get doc version
					LOG.info("Getting HTML page version");
					List<Node> nodes = htmlDocument.childNodes();
					String version = utils.getDocVersion(nodes);
					analysisResult.setVersion(version);

					// get doc title
					LOG.info("Getting page title");
					analysisResult.setTitle(htmlDocument.title());

					// process headings
					LOG.info("Getting page headings");
					List<Heading> headings = utils.docHeadingsProcess(htmlDocument);
					analysisResult.setHeadings(headings);

					// Process links
					LOG.info("Getting page internal & external links");
					String hostName = connection.request().url().getHost();
					LinkResult linkResult = utils.getLinks(htmlDocument, hostName);
					analysisResult.setLinkResult(linkResult);

					// process form
					analysisResult.setHasLoginForm(utils.hasLoginForm(htmlDocument) ? "YES" : "NO");

				}

			} catch (HttpStatusException he) {
				utils.updateAnalysisResultSatus(he.getStatusCode(), he.getMessage(), analysisResult, true);

			} 
			catch (IOException e) {
				utils.updateAnalysisResultSatus(500, e.toString(), analysisResult, true);
			}
		} else {

			utils.updateAnalysisResultSatus(500, "Invalid url", analysisResult, true);
			throw new IllegalArgumentException();
		}
	}

	@Override
	public AnalysisResult getAanlysisResult() {
		return analysisResult;
	}
}
