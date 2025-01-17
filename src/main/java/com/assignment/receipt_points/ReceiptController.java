package com.assignment.receipt_points;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
	
	private final ReceiptPointsCalculator receiptPointsCalculator;
	
	private final String ReceiptNotFoundResponseMessage = "No receipt found for that ID.";
	private final String InvalidReceiptResponseMessage = "The receipt is invalid.";
	
	public ReceiptController(ReceiptPointsCalculator receiptPointsCalculator)
	{
		this.receiptPointsCalculator = receiptPointsCalculator;
	}
	
	@PostMapping("/process")
	public ResponseEntity<?> processReceipt(@RequestBody Receipt receipt)
	{
		if (!this.isReceiptValid(receipt))
		{
			return ResponseEntity.status(400).body(Map.of("error", this.InvalidReceiptResponseMessage));
		}
		
		String receiptId = this.receiptPointsCalculator.processReceipt(receipt);
		return ResponseEntity.ok(Map.of("id", receiptId));
	}
	
	@GetMapping("/{id}/points")
    public ResponseEntity<?> getPoints(@PathVariable String id) {
        Long points = this.receiptPointsCalculator.getPoints(id);
        if (points == null) {
            return ResponseEntity.status(404).body(Map.of("error", this.ReceiptNotFoundResponseMessage));
        }
        
        return ResponseEntity.ok(Map.of("points", points));
    }
	
	private boolean isReceiptValid(Receipt receipt) {
	    if (receipt == null) {
	        return false;
	    }
	    
	    if (receipt.getRetailer() == null || receipt.getRetailer().isEmpty()) {
	    	return false;
	    }
	    
	    if (!receipt.getRetailer().matches("^[\\w\\s\\-&]+$") ) {
	    	return false;
	    }
	    
	    if (receipt.getTotal() == null || receipt.getTotal().isEmpty()) {
	    	return false;
	    }
	    
	    if (!receipt.getTotal().matches("^\\d+\\.\\d{2}$")) { 
	    	return false;
	    }
	    
	    if (receipt.getItemList() == null || receipt.getItemList().isEmpty()) {
	    	return false;
	    }
	    for(int i = 0; i < receipt.getItemList().size(); i++)
		{
			Item item = receipt.getItemList().get(i);
			if (item.getShortDescription() == null || item.getShortDescription().trim().isEmpty()) {
		        return false;
		    }
			if(!item.getPrice().matches("^\\d+\\.\\d{2}$"))
			{
				return false;
			}
		    double priceValue = Double.parseDouble(item.getPrice());
		    if (priceValue < 0) {
		        return false;
		    }
		}
	    
	    if (receipt.getPurchaseDate() == null || receipt.getPurchaseDate().isEmpty()) { 
	    	return false;
	    }
	    
	    if (!receipt.getPurchaseDate().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	    	return false;
	    }
	    
	    if (receipt.getPurchaseTime() == null || receipt.getPurchaseTime().isEmpty()) {
	    	return false;
	    }
	    
	    if (!receipt.getPurchaseTime().matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$") ) {
	        return false;
	    }
	    
	    return true;
	}
}