package com.lotus.wms.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDimensionRequest {
    private String depotId;
    private String barCode;
    private double length;
    private double width;
    private double height;
    private double weight;
}
