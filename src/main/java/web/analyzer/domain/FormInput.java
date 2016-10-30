package web.analyzer.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FormInput {
	
	@NotNull
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
