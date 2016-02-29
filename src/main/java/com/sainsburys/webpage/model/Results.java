package com.sainsburys.webpage.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({
	"results",
	"total"
})
public class Results {
	
	@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
	private List<Item> results;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
	private String total;

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @param results
	 */
	public Results() {
		this.results = new ArrayList<Item>();
	}

	/**
	 * @return the results
	 */
	public List<Item> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Item> results) {
		this.results = results;
	}
	
	

}
