package web.analyzer.domain;

public class Link {
	
	private String href;
	
	private String type;
	
	private boolean isAccessible;
	
	public Link(){
		
	}
	
	public Link(String href, String type, boolean isAccessible){
		this.href = href;
		this.type = type;
		this.isAccessible = isAccessible;
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

	public boolean isAccessible() {
		return isAccessible;
	}

	public void setAccessible(boolean isAccessible) {
		this.isAccessible = isAccessible;
	}
}
