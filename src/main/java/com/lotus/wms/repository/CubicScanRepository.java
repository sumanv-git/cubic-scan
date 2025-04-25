package com.lotus.wms.repository;

import com.lotus.wms.modal.ItemDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.dialect.OracleTypes;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CubicScanRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ItemDetail> getItemDetails(String depotId, String barCode) {
        List<ItemDetail> results = new ArrayList<>();

        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement cs = connection.prepareCall(
                    "{ call cpl_cubicscan_interface.get_item_details(?, ?, ?, ?) }")) {

                cs.setString(1, depotId);
                cs.setString(2, barCode);
                cs.registerOutParameter(3, OracleTypes.CURSOR); // requires oracle.jdbc.OracleTypes
                cs.registerOutParameter(4, Types.VARCHAR);

                cs.execute();

                ResultSet rs = (ResultSet) cs.getObject(3);
                while (rs.next()) {
                    ItemDetail item = new ItemDetail();
                    item.setSno(rs.getInt("sno"));
                    item.setProductId(rs.getString("productId"));
                    item.setDescription(rs.getString("description"));
                    item.setCasepack(rs.getInt("casePack"));
                    results.add(item);
                }

                String status = cs.getString(4);
                System.out.println("Status: " + status);
            }
        });

        return results;
    }


    public String insertItemDimensions(String depotId, String barCode,
                                       double length, double width, double height, double weight) {
        final String[] status = {""};

        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (CallableStatement cs = connection.prepareCall(
                    "{ call cpl_cubicscan_interface.ins_item_dimensions(?, ?, ?, ?, ?, ?, ?) }")) {

                cs.setString(1, depotId);
                cs.setString(2, barCode);
                cs.setDouble(3, length);
                cs.setDouble(4, width);
                cs.setDouble(5, height);
                cs.setDouble(6, weight);
                cs.registerOutParameter(7, Types.VARCHAR);

                cs.execute();
                status[0] = cs.getString(7);
            }
        });

        return status[0];
    }

}
