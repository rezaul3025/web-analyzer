/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.analyzer.utils;

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
                String docTypePublicId = documentType.toString();//documentType.attr("publicid");
                if (docTypePublicId != null && !docTypePublicId.isEmpty()) {
                    docTypePublicId = docTypePublicId.toLowerCase();
                    if (docTypePublicId.equals(HTML5_IDENTIFIER)) {
                        version = "HTML 5";
                    } else if (docTypePublicId.contains(HTML4_VERSION) && docTypePublicId.contains(HTML_IDENTIFIER_STRICT)) {
                        version = "HTML 4.01 Strict";
                    } else if (docTypePublicId.contains(HTML4_VERSION) && docTypePublicId.contains(HTML_IDENTIFIER_TRANSITIONAL)) {
                        version = "HTML 4.01 Transitional";
                    } else if (docTypePublicId.contains(HTML4_VERSION) && docTypePublicId.contains(HTML_IDENTIFIER_FRAMESET)) {
                        version = "HTML 4.01 Frameset";
                    } else if (docTypePublicId.contains(HTML3_VERSION)) {
                        version = "HTML 3.2";
                    } else if (docTypePublicId.contains(XHTML) && docTypePublicId.contains(XHTML1_VERSION) && docTypePublicId.contains(HTML_IDENTIFIER_STRICT)) {
                        version = "XHTML 1.0 Strict";
                    } else if (docTypePublicId.contains(XHTML) && docTypePublicId.contains(XHTML1_VERSION) && docTypePublicId.contains(HTML_IDENTIFIER_TRANSITIONAL)) {
                        version = "XHTML 1.0 Transitional";
                    } else if (docTypePublicId.contains(XHTML) && docTypePublicId.contains(XHTML1_VERSION) && docTypePublicId.contains(HTML_IDENTIFIER_FRAMESET)) {
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
    
    public List<Heading> docHeadingsProcess(Document doc){
    	
    	List<Heading> headingList = new ArrayList<Heading>();
    	int level =0;
    	Elements eles = doc.select("*");
    	for(Element ele : eles){
    		level++;
    		//System.out.println(ele.text());
    		//System.out.println(level);
    		if(HEADING_TAG.contains(ele.tagName())){
    			headingList.add(new Heading(ele.tagName(),ele.html(),level));
    		}
    		
    		if(ele.children().size() == 0){
    			level=0;
    			continue;
    		}
    		else{
    			eles = ele.children();
    		}
    	}
    	
    	return headingList;
    }
    
    public void getLinks(Document doc){
    	Elements links = doc.select("a[href]");
    	for (Element link : links) {
           // print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
            String href = link.attr("abs:href");
            String rel = link.attr("rel");
            
            System.out.println(href+" -> "+rel);
        }
    }
    
    public void updateAnalysisResultSatus(Integer code, String message, AnalysisResult result, boolean isError){
    	
    	result.setRequestStatusCode(code);
    	result.setRequestStatusMessage(message);
    	
    	if(isError){
    		result.setHeadings(new ArrayList<Heading>());
    		result.setTitle("");
    		result.setVersion("");
    	}
    	
    	//return result;
    }
}
