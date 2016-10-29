/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.analyzer.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import web.analyzer.domain.AnalysisResult;
import web.analyzer.domain.Heading;
import web.analyzer.domain.Link;

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
			// System.out.println(ele.text());
			// System.out.println(level);
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

	public List<Link> getLinks(Document doc, String hostName) throws IOException {
		List<Link> linksInfo = new ArrayList<Link>();
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			// print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"),
			// link.attr("rel"));
			String href = link.attr("abs:href");
			// String rel = link.attr("rel");

			/*
			 * URL u = new URL ( "http://www.example.com/"); HttpURLConnection
			 * huc = ( HttpURLConnection ) u.openConnection ();
			 * huc.setRequestMethod ("GET"); //OR huc.setRequestMethod ("HEAD");
			 * huc.connect () ; int code = huc.getResponseCode() ;
			 * System.out.println(code);
			 */

			if (href.startsWith("http://") || href.startsWith("https://")) {
				// System.out.println(pingHost(href));
				URL url = new URL(href);
				String linkHostName = url.getHost();
				String linkType = linkHostName.equalsIgnoreCase(hostName) ? "internal" : "external";
				//System.out.println(linkType);
				linksInfo.add(new Link(href, linkType, true));
			}
		}

		return linksInfo;
	}

	public boolean hasLoginForm(Document doc) {
		Elements formElements = doc.select("*");//doc.getElementsByTag("form");
		for (Element formElement : formElements) {
			
			if (formElement.tagName().equals("input") && formElement.hasAttr("type") && formElement.attr("type").equals("password")) {
				System.out.println("login form found");
				return true;
			}
			//formElement.tagName().equals("input")  && formElement.hasAttr("type") && formElement.attr("type").equals("password")
			
			
			Elements formChild = formElement.children();
			
			if(formChild.size()==0){
				continue;
			}
			else{
				formElements = formChild;
			}
		}
		
		return false;
	}

	public void updateAnalysisResultSatus(Integer code, String message, AnalysisResult result, boolean isError) {

		result.setRequestStatusCode(code);
		result.setRequestStatusMessage(message);

		if (isError) {
			result.setHeadings(new ArrayList<Heading>());
			result.setLinks(new ArrayList<Link>());
			result.setTitle("");
			result.setVersion("");
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

}
