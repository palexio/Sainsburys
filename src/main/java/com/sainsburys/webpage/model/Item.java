package com.sainsburys.webpage.model;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({
	"title",
	"size",
	"unitPrice",
	"description"
})
public class Item {
	
	@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
	private String title;
	
	@JsonIgnore
	private BigDecimal price;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
	private String size;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
	@JsonProperty("unit_price")
	private String unitPrice;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
	private String description;
	
	@JsonIgnore
	private String link;

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the unitPrice
	 */
	public String getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	
}
