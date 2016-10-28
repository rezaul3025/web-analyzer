/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.analyzer.utils;

import java.util.List;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Service;

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
    private static final String XHTML = "xhtml";
}
