package com.assignment.receipt_points;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class ReceiptPointsCalculator {
	
	private final Map<String, Long> receiptPointsMap = new ConcurrentHashMap<>();

	public String processReceipt(Receipt receipt)
	{
		String receiptId = UUID.randomUUID().toString();
		
		if (this.receiptPointsMap.containsKey(receiptId)) {
		    throw new IllegalStateException("Duplicate receipt ID generated. Try again.");
		}
		
        long points = this.calculatePoints(receipt);
        this.receiptPointsMap.put(receiptId, points);
        
        return receiptId;
	}
	
	public Long getPoints(String receiptId) {
        return this.receiptPointsMap.get(receiptId);
    }
	
	public long calculatePoints(Receipt receipt)
	{

		String retailer = receipt.getRetailer();
		String total = receipt.getTotal();
		List<Item> itemList = receipt.getItemList();
			
		long points = 0;
		
		// Rule 1 : One point for every alphanumeric character in the retailer name. 
		for(int i = 0; i < retailer.length(); i++) {
			char ch = retailer.charAt(i);
			if(Character.isLetterOrDigit(ch))
			{
				points++;
			}
		}
		
		// Rule 2 : 50 points if the total is a round dollar amount with no cents.
		boolean match = total.matches("^\\d+\\.00$");
		if (match)
		{
			points += 50;
		}
		
		// Rule 3 : 25 points if the total is a multiple of 0.25.
		double totalValue = Double.parseDouble(total);
		
		if(totalValue % 0.25 == 0)
		{
			points += 25;
		}
		
		// Rule 4 : 5 points for every two items on the receipt.
		int itemCount = itemList.size();
		int pairs = itemCount/2;
		points += pairs * 5;
		
		
		// Rule 5 : If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer.
		// The result is the number of points earned.
		for(int i = 0; i < itemCount; i++)
		{
			Item item = itemList.get(i);
			String trimShortDescription = item.getShortDescription().trim();
			if(trimShortDescription.length() % 3 == 0)
			{
				points += (int)Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
			}
		}
		
		// Rule 6 : 6 points if the day in the purchase date is odd.
		
		String purchaseDate = receipt.getPurchaseDate();
		String[] dateParts = purchaseDate.split("-");
	    
	    int day = Integer.parseInt(dateParts[2]);
	    if(day % 2 == 1)
		{
			points += 6;
		}
		
		// Rule 7 : 10 points if the time of purchase is after 2:00pm and before 4:00pm.
		String purchaseTime = receipt.getPurchaseTime();
	    String[] timeParts = purchaseTime.split(":");
		int hour = Integer.parseInt(timeParts[0]);
	    
		if(hour == 14 || hour == 15)
		{
			points += 10;
		}
		
		return points;
	}
}