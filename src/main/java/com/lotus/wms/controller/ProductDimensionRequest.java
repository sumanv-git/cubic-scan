package com.lotus.wms.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDimensionRequest {
    float length;
    float width;
    float height;
    float weight;
    Long productId;
}
