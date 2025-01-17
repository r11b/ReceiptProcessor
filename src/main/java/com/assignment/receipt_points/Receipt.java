package com.assignment.receipt_points;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Receipt {
	
	@JsonProperty
	private String id;
	
	@JsonProperty
	private String retailer;
	
	@JsonProperty
	private String purchaseDate;
	
	@JsonProperty
	private String purchaseTime;
	
	@JsonProperty
	private String total;
	
	@JsonProperty
	private List<Item> items = new ArrayList<Item>();
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getRetailer()
	{
		return this.retailer;
	}
	
	public void setRetailer(String retailer)
	{
		this.retailer = retailer;
	}
	
	public String getPurchaseDate()
	{
		return this.purchaseDate;
	}
	
	public void setPurchaseDate(String purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}
	
	public String getPurchaseTime()
	{
		return this.purchaseTime;
	}
	
	public void setPurchaseTime(String purchaseTime)
	{
		this.purchaseTime = purchaseTime;
	}
	
	public String getTotal()
	{
		return this.total;
	}
	
	public void setTotal(String total)
	{
		this.total = total;
	}
	
	public List<Item> getItemList()
	{
		return this.items;
	}
	
	public void setItemList(List<Item> items)
	{
		this.items = items;
	}
}
