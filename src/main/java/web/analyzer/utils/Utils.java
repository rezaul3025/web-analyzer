/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.analyzer.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import web.analyzer.domain.AnalysisResult;
import web.analyzer.domain.Heading;
import web.analyzer.domain.Link;
import web.analyzer.domain.LinkResult;

/**
 *
 * @author rkarim
 */
@Service
public class Utils {

	private static final String HTML5_IDENTIFIER = "<!doctype html>";

	private static final String HTML4_VERSION = "4.01";
	private static final String HTML_IDENTIFIER_STRICT = "strict";
	private static final String HTML_IDENTIFIER_TRANSITIONAL = "transitional";
	private static final String HTML_IDENTIFIER_FRAMESET = "frameset";

	private static final String HTML3_VERSION = "3.2";

	private static final String XHTML1_VERSION = "1.0";
	private static final String XHTML11_VERSION = "1.1";
	// private static final String XHTM2_VERSION = "2.0";
	private static final String XHTML = "xhtml";

	private static final String HEADING_TAG = "h1,h2,h3,h4,h5,h6";

	private static final String URL_VALIDATIN_REGEX = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";

	public String getDocVersion(List<Node> nodes) {
		String version = "non";
		for (Node node : nodes) {
			if (node instanceof DocumentType) {
				DocumentType documentType = (DocumentType) node;
				System.out.println(documentType.toString());
				String docTypePublicId = documentType.toString();// documentType.attr("publicid");
				if (docTypePublicId != null && !docTypePublicId.isEmpty()) {
					docTypePublicId = docTypePublicId.toLowerCase();
					if (docTypePublicId.equals(HTML5_IDENTIFIER)) {
						version = "HTML 5";
					} else if (docTypePublicId.contains(HTML4_VERSION)
							&& docTypePublicId.contains(HTML_IDENTIFIER_STRICT)) {
						version = "HTML 4.01 Strict";
					} else if (docTypePublicId.contains(HTML4_VERSION)
							&& docTypePublicId.contains(HTML_IDENTIFIER_TRANSITIONAL)) {
						version = "HTML 4.01 Transitional";
					} else if (docTypePublicId.contains(HTML4_VERSION)
							&& docTypePublicId.contains(HTML_IDENTIFIER_FRAMESET)) {
						version = "HTML 4.01 Frameset";
					} else if (docTypePublicId.contains(HTML3_VERSION)) {
						version = "HTML 3.2";
					} else if (docTypePublicId.contains(XHTML) && docTypePublicId.contains(XHTML1_VERSION)
							&& docTypePublicId.contains(HTML_IDENTIFIER_STRICT)) {
						version = "XHTML 1.0 Strict";
					} else if (docTypePublicId.contains(XHTML) && docTypePublicId.contains(XHTML1_VERSION)
							&& docTypePublicId.contains(HTML_IDENTIFIER_TRANSITIONAL)) {
						version = "XHTML 1.0 Transitional";
					} else if (docTypePublicId.contains(XHTML) && docTypePublicId.contains(XHTML1_VERSION)
							&& docTypePublicId.contains(HTML_IDENTIFIER_FRAMESET)) {
						version = "XHTML 1.0 Frameset";
					} else if (docTypePublicId.contains(XHTML) && docTypePublicId.contains(XHTML11_VERSION)) {
						version = "XHTML 1.1";
					}
				}
				return version;
			}
		}
		return version;
	}

	public List<Heading> docHeadingsProcess(Document doc) {
		List<Heading> headingList = new ArrayList<Heading>();
		int level = 0;
		Elements eles = doc.select("*");
		for (Element ele : eles) {
			level++;
			if (HEADING_TAG.contains(ele.tagName())) {
				headingList.add(new Heading(ele.tagName(), ele.html(), level));
			}

			if (ele.children().size() == 0) {
				level = 0;
				continue;
			} else {
				eles = ele.children();
			}
		}

		return headingList;
	}

	public LinkResult getLinks(Document doc, String hostName) throws IOException {
		List<Link> linksInfo = new ArrayList<Link>();
		int totalInternalLink = 0;
		int totalExternalLink = 0;
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String href = link.attr("abs:href");
			if (isValidUrl(href)) {
				URL url = new URL(href);
				String linkHostName = url.getHost();
				String linkType = "";
				if (linkHostName.equalsIgnoreCase(hostName)) {
					linkType = "internal";
					totalInternalLink++;
				} else {
					linkType = "external";
					totalExternalLink++;
				}
				linksInfo.add(new Link(href, linkType, true));
			}
		}

		return new LinkResult(linksInfo, totalInternalLink, totalExternalLink, 0, 0);
	}

	public boolean hasLoginForm(Document doc) {
		Elements formElements = doc.getElementsByTag("form");
		for (Element formElement : formElements) {

			String frmElementAsString = formElement.toString().toLowerCase().replace("'", "\"");
			Pattern inputTextTagPattern = Pattern.compile("type=\"text\"");
			Matcher inputTextTagMatcher = inputTextTagPattern.matcher(frmElementAsString);
			int inputTextTagCount = 0;
			while (inputTextTagMatcher.find()) {
				inputTextTagCount++;
			}

			Pattern inputEmailTagPattern = Pattern.compile("type=\"email\"");
			Matcher inputEmailTagMatcher = inputEmailTagPattern.matcher(frmElementAsString);
			int inputEmailTagCount = 0;
			while (inputEmailTagMatcher.find()) {
				inputEmailTagCount++;
			}

			Pattern inputPasswordTagPattern = Pattern.compile("type=\"password\"");
			Matcher inputPasswordTagMatcher = inputPasswordTagPattern.matcher(frmElementAsString);
			int inputPasswordTagCount = 0;
			while (inputPasswordTagMatcher.find()) {
				inputPasswordTagCount++;
			}

			if ((inputTextTagCount == 1 || inputEmailTagCount == 1) && inputPasswordTagCount == 1) {
				return true;
			}
		}

		return false;
	}

	public void updateAnalysisResultSatus(Integer code, String message, AnalysisResult result, boolean isError) {

		result.setRequestStatusCode(code);
		result.setRequestStatusMessage(message);

		if (isError) {
			result.setHeadings(new ArrayList<Heading>());
			result.setLinkResult(new LinkResult());
			result.setTitle("");
			result.setVersion("");
			result.setHasLoginForm("");
		}

	}

	private boolean pingHost(String href) {
		/*
		 * try { boolean reachable =
		 * InetAddress.getByName(host).isReachable(3000); } catch
		 * (UnknownHostException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } catch (IOException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
		/*
		 * try (Socket socket = new Socket()) { socket.connect(new
		 * InetSocketAddress(host, port), timeout); return true; } catch
		 * (IOException e) { return false; // Either timeout or unreachable or
		 * failed DNS lookup. }
		 */

		/*
		 * try { InetAddress.getByName(href).isReachable(2000); //Replace with
		 * your name return true; } catch (Exception e) { return false; }
		 */

		try {
			URL url = new URL(href);
			URLConnection urlConnection = url.openConnection();

			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestMethod("HEAD");
			httpURLConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

			return (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK);

		} catch (UnknownHostException unknownHostException) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isValidUrl(String url) {
		Pattern urlPattern = Pattern.compile(URL_VALIDATIN_REGEX);
		Matcher urlMatcher = urlPattern.matcher(url);
		boolean foundMatch = urlMatcher.matches();
		return foundMatch;
	}

}
