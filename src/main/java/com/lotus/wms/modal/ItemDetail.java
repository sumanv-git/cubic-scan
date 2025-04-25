package com.lotus.wms.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDetail {
    private int sno;
    private String productId;
    private String description;
    private int casepack;
}
