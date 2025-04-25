package com.lotus.wms.service;

import com.lotus.wms.modal.ItemDetail;
import com.lotus.wms.modal.ProductResponse;
import com.lotus.wms.repository.CubicScanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CubicScanService {

    private final CubicScanRepository cubicScanRepository;

    public CubicScanService(CubicScanRepository cubicScanRepository) {
        this.cubicScanRepository = cubicScanRepository;
    }

    /**
     * Fetch item details using depot ID and scanned barcode.
     */
    public ProductResponse getItemDetails(String depotId, String barCode) {
        List<ItemDetail> itemDetails = cubicScanRepository.getItemDetails(depotId, barCode);
        return ProductResponse.builder()
                .depotId(depotId)
                .barcode(barCode)
                .products(itemDetails)
                .build();
    }

    /**
     * Insert item dimensions into the system.
     */
    public String insertItemDimensions(String depotId, String barCode,
                                       double length, double width,
                                       double height, double weight) {
        return cubicScanRepository.insertItemDimensions(depotId, barCode, length, width, height, weight);
    }

}
