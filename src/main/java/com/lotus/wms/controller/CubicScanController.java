package com.lotus.wms.controller;

import com.lotus.wms.modal.ItemDetail;
import com.lotus.wms.modal.ItemDimensionRequest;
import com.lotus.wms.modal.ProductResponse;
import com.lotus.wms.service.CubicScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cubic-scan")
public class CubicScanController {

    private final CubicScanService cubicScanService;

    @Autowired
    public CubicScanController(CubicScanService cubicScanService) {
        this.cubicScanService = cubicScanService;
    }

    @GetMapping("/item-details")
    public ResponseEntity<ProductResponse>  getItemDetails(
            @RequestParam String depotId,
            @RequestParam String barCode) {
        try {
            ProductResponse response = cubicScanService.getItemDetails(depotId, barCode);

            if (response == null || response.getProducts().isEmpty()) {

                ProductResponse notFoundResponse =  ProductResponse.builder().build();
                return ResponseEntity
                        .status(404)
                        .body(notFoundResponse);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Optional: log exception
            return ResponseEntity
                    .status(500)
                    .body(new ProductResponse());
        }
    }

    @PostMapping("/saveItemDimensions")
    public ResponseEntity<String>  insertItemDimensions(@RequestBody ItemDimensionRequest request) {
        try {
            String status = cubicScanService.insertItemDimensions(
                    request.getDepotId(),
                    request.getBarCode(),
                    request.getLength(),
                    request.getWidth(),
                    request.getHeight(),
                    request.getWeight()
            );

            if ("SUCCESS".equalsIgnoreCase(status)) {
                return ResponseEntity.ok("Item dimensions inserted successfully.");
            } else {
                return ResponseEntity
                        .badRequest()
                        .body("Failed to insert item dimensions. Reason: " + status);
            }

        } catch (Exception e) {
            // Log the error here if needed
            return ResponseEntity
                    .status(500)
                    .body("Internal server error: " + e.getMessage());
        }
    }

}
