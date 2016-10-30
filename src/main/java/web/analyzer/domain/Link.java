package web.analyzer.domain;

/**
 * Domain POJO class, represent link of requested page
 * 
 * @author rkarim
 *
 */

public class Link {
	
	private String href;
	
	private String type;
	
	public Link(){
		
	}
	
	public Link(String href, String type){
		this.href = href;
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
