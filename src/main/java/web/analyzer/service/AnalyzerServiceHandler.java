package web.analyzer.service;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.analyzer.domain.AnalysisResult;
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
    public AnalyzerService analyze(String url) {
        // TODO Auto-generated method stub
        connection = Jsoup.connect(url);
        try {
            Document htmlDocument = connection.get();
            if (connection.response().statusCode() == 200) {

                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + htmlDocument.body().text());

                List<Node> nodes = htmlDocument.childNodes();
                String version = utils.getDocVersion(nodes);
                System.out.println(version);
                analysisResult.setVersion(version);
             
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

    @Override
    public AnalysisResult getAanlysisResult() {
       return analysisResult;
    }

}
