package web.analyzer.service;

import web.analyzer.domain.AnalysisResult;

public interface AnalyzerService {
	void analyze(String url);
    AnalysisResult getAanlysisResult();
}
