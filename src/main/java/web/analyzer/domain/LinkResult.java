package web.analyzer.domain;

import java.util.ArrayList;
import java.util.List;

public class LinkResult {
	private List<Link> links = new ArrayList<Link>();

	private Integer totalInternalLink;

	private Integer totalExternalLink;

	private Integer totalAccessibleLink;

	private Integer totalInAccessibleLink;

	public LinkResult() {

	}

	public LinkResult(List<Link> links, Integer totalInternalLink, Integer totalExternalLink,
			Integer totalAccessibleLink, Integer totalInAccessibleLink) {
		this.links = links;
		this.totalInternalLink = totalInternalLink;
		this.totalExternalLink = totalExternalLink;
		this.totalAccessibleLink = totalAccessibleLink;
		this.totalInAccessibleLink = totalInAccessibleLink;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public Integer getTotalInternalLink() {
		return totalInternalLink;
	}

	public void setTotalInternalLink(Integer totalInternalLink) {
		this.totalInternalLink = totalInternalLink;
	}

	public Integer getTotalExternalLink() {
		return totalExternalLink;
	}

	public void setTotalExternalLink(Integer totalExternalLink) {
		this.totalExternalLink = totalExternalLink;
	}

	public Integer getTotalAccessibleLink() {
		return totalAccessibleLink;
	}

	public void setTotalAccessibleLink(Integer totalAccessibleLink) {
		this.totalAccessibleLink = totalAccessibleLink;
	}

	public Integer getTotalInAccessibleLink() {
		return totalInAccessibleLink;
	}

	public void setTotalInAccessibleLink(Integer totalInAccessibleLink) {
		this.totalInAccessibleLink = totalInAccessibleLink;
	}

}
