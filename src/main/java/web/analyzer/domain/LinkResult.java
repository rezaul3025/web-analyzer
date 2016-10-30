package web.analyzer.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain POJO class, represent processed link result
 * 
 * @author rkarim
 *
 */

public class LinkResult {
	private List<Link> links = new ArrayList<Link>();

	private Integer totalInternalLink;

	private Integer totalExternalLink;
	
	public LinkResult() {

	}

	public LinkResult(List<Link> links, Integer totalInternalLink, Integer totalExternalLink) {
		this.links = links;
		this.totalInternalLink = totalInternalLink;
		this.totalExternalLink = totalExternalLink;
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
}
