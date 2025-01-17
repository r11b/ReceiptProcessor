package com.assignment.receipt_points;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
	
	@JsonProperty
	private String shortDescription;
	
	@JsonProperty
	private String price;
	
	public Item(String shortDescription, String price)
	{
		this.shortDescription = shortDescription;
		this.price = price;
	}
	
	public String getShortDescription()
	{
		return shortDescription;
	}
	
	public void setShortDescription(String shortDescription)
	{
		this.shortDescription = shortDescription;
	}
	
	public String getPrice()
	{
		return price;
	}
	
	public void setPrice(String price)
	{
		this.price = price;
	}
}