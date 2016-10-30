package web.analyzer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.analyzer.domain.AnalysisResult;
import web.analyzer.domain.FormInput;
import web.analyzer.service.AnalyzerService;
import web.analyzer.utils.Utils;

@Controller
public class WebAnalyzerController {

	@Autowired
	private AnalyzerService analyzerService;
	
	@Autowired
	private Utils utils;

	@RequestMapping(value = "/")
	public String index(Model model, @RequestParam(value="error", required=false) String error) {
		model.addAttribute("forminput", new FormInput());
		model.addAttribute("errormessage", error);
		return "index";
	}

	@PostMapping(value = "/init-analyzer")
	public String initWebAnalyzer(@ModelAttribute("forminput") @Valid FormInput formInput, Model model, BindingResult bindingResult) {
		
		String url = formInput.getUrl();
		
		if(!utils.isValidUrl(url)){
			return "redirect:/?error=Error : Invalid url !";
		}
	
		analyzerService.analyze(url);
		AnalysisResult analysisResult = analyzerService.getAanlysisResult();
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : " + analysisResult.getRequestStatusCode());
		model.addAttribute("analysisresult", analysisResult);
		return "analysis_result";
	}
}
