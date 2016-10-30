/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.analyzer.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Domain POJO, represent analysis result
 * 
 * @author rkarim
 */
@Component
public class AnalysisResult {
    private String version;
    private String title;
    private List<Heading> headings = new ArrayList<Heading>();
    private LinkResult linkResult = new LinkResult();
    private String hasLoginForm;
    private Integer requestStatusCode;
    private String requestStatusMessage;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Heading> getHeadings() {
		return headings;
	}

	public void setHeadings(List<Heading> headings) {
		this.headings = headings;
	}

	public LinkResult getLinkResult() {
		return linkResult;
	}

	public void setLinkResult(LinkResult linkResult) {
		this.linkResult = linkResult;
	}

	public String getHasLoginForm() {
		return hasLoginForm;
	}

	public void setHasLoginForm(String hasLoginForm) {
		this.hasLoginForm = hasLoginForm;
	}

	public Integer getRequestStatusCode() {
		return requestStatusCode;
	}

	public void setRequestStatusCode(Integer requestStatusCode) {
		this.requestStatusCode = requestStatusCode;
	}

	public String getRequestStatusMessage() {
		return requestStatusMessage;
	}

	public void setRequestStatusMessage(String requestStatusMessage) {
		this.requestStatusMessage = requestStatusMessage;
	}  
    
    
}
