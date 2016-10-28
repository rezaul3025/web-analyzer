/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.analyzer.domain;

import org.springframework.stereotype.Component;

/**
 *
 * @author rkarim
 */
@Component
public class AnalysisResult {
    private String version;
    private Integer requestStatusCode;
    private String requestStatusMessage;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
