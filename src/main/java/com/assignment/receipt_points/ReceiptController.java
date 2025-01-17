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
	
	public ReceiptController(ReceiptPointsCalculator receiptPointsCalculator)
	{
		this.receiptPointsCalculator = receiptPointsCalculator;
	}
	
	@PostMapping("/process")
	public ResponseEntity<?> processReceipt(@RequestBody Receipt receipt)
	{
		this.validateReceipt(receipt);
		
		String receiptId = this.receiptPointsCalculator.processReceipt(receipt);
		return ResponseEntity.ok(Map.of("id", receiptId));
	}
	
	@GetMapping("/{id}/points")
    public ResponseEntity<?> getPoints(@PathVariable String id) {
        Long points = this.receiptPointsCalculator.getPoints(id);
        if (points == null) {
            return ResponseEntity.status(404).body(Map.of("error", "No receipt found for that ID."));
        }
        
        return ResponseEntity.ok(Map.of("points", points));
    }
	
	private void validateReceipt(Receipt receipt) {
	    if (receipt == null) {
	        throw new IllegalArgumentException("Receipt cannot be null.");
	    }
	    
	    if (receipt.getRetailer() == null || receipt.getRetailer().isEmpty()) {
	        throw new IllegalArgumentException("Retailer name is missing.");
	    }
	    
	    if (!receipt.getRetailer().matches("^[\\w\\s\\-&]+$") ) {
	        throw new IllegalArgumentException("Retailer name is in invalid format.");
	    }
	    
	    if (receipt.getTotal() == null || receipt.getTotal().isEmpty()) {
	        throw new IllegalArgumentException("Total is missing.");
	    }
	    
	    if (!receipt.getTotal().matches("^\\d+\\.\\d{2}$")) { 
	        throw new IllegalArgumentException("Total is in an invalid format.");
	    }
	    
	    if (receipt.getItemList() == null || receipt.getItemList().isEmpty()) {
	        throw new IllegalArgumentException("Item list cannot be empty.");
	    }
	    
	    if (receipt.getPurchaseDate() == null || receipt.getPurchaseDate().isEmpty()) { //^\d{4}-\d{2}-\d{2}$
	        throw new IllegalArgumentException("Purchase date is missing.");
	    }
	    
	    if (!receipt.getPurchaseDate().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	        throw new IllegalArgumentException("Purchase date is in an invalid format.");
	    }
	    
	    if (receipt.getPurchaseTime() == null || receipt.getPurchaseTime().isEmpty()) {
	        throw new IllegalArgumentException("Purchase time is missing.");
	    }
	    
	    if (!receipt.getPurchaseTime().matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$") ) {
	        throw new IllegalArgumentException("Purchase time is in an invalid format.");
	    }
	}
}
