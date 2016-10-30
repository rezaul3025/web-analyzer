package web.analyzer.domain;

public class Link {
	
	private String href;
	
	private String type;
	
	private boolean accessible;
	
	public Link(){
		
	}
	
	public Link(String href, String type, boolean accessible){
		this.href = href;
		this.type = type;
		this.accessible = accessible;
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

	public boolean getAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
}
