package web.analyzer.service;

import web.analyzer.domain.AnalysisResult;

public interface AnalyzerService {
	AnalyzerService analyze(String url);
	Integer getRequestStatus();
        AnalysisResult getAanlysisResult();
}
