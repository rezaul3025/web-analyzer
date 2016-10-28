package web.analyzer.domain;

public class Heading {
	
	private String tagName;
	
	private String tagContent;
	
	private Integer level;
	
	public Heading(){
		
	}

	public Heading(String tagName, String tagContent, Integer level){
		this.tagName = tagName;
		this.tagContent = tagContent;
		this.level = level;
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
