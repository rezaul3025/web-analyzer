package web.analyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.analyzer.domain.FormInput;
import web.analyzer.service.AnalyzerService;

@Controller
public class WebAnalyzerController {
	
	@Autowired
	private AnalyzerService analyzerService;
	
	@RequestMapping(value="/")
	public String index(Model model){
		model.addAttribute("forminput", new FormInput());
		return "index";
	}
	
	@PostMapping(value="/init-analyzer")
	public String initWebAnalyzer(@ModelAttribute("forminput") FormInput formInput, Model model){
		int scode = analyzerService.analyze(formInput.getUrl()).getRequestStatus();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : "+scode);
                model.addAttribute("analysisresult", analyzerService.getAanlysisResult());
		return "analysis_result";
	}
}
