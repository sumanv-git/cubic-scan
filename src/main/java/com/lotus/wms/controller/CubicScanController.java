package com.lotus.wms.controller;

import com.lotus.wms.modal.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.PrimitiveIterator;

@RestController
@RequestMapping("/cubic")
public class CubicScanController {

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable("productId") String productId){

        ProductResponse productResponse = ProductResponse.builder()
                .depotId("910")
                .ItemId(productId)
                .description("Test Item Description")
                .build();

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/saveDimensions")
    public ResponseEntity<String> saveProductDimensions(@RequestBody ProductDimensionRequest productDimensionRequest){
        System.out.println("input dimensions are "+ productDimensionRequest);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

}
