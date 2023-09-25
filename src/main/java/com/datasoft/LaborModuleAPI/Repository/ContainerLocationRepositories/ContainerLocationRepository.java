package com.datasoft.LaborModuleAPI.Repository.ContainerLocationRepositories;

import com.datasoft.LaborModuleAPI.Model.ContainerDetailInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContainerLocationRepository extends CrudRepository<ContainerDetailInfo, Long> {

    @Query(value = "select category from sparcsn4.inv_unit where id=:cont_number order by sparcsn4.inv_unit.gkey desc limit 1", nativeQuery = true)
    public String getCategory(@Param("cont_number") String cont_number);

    @Query(value = "SELECT desti.destination,(SELECT ctmsmis.offdoc.name FROM ctmsmis.offdoc WHERE id=desti.destination) AS dest_name\t\t\t\t\n" +
            "FROM sparcsn4.inv_unit inv  \n" +
            "INNER JOIN sparcsn4.inv_goods desti ON desti.gkey=inv.goods\n" +
            "WHERE inv.id =:cont_number\n" +
            "ORDER BY inv.gkey DESC\n" +
            "LIMIT 1", nativeQuery = true)
    public List[] getContDest(@Param("cont_number") String cont_number);

    @Query(value = "SELECT inv.id,fcyVisit.transit_state,fcyVisit.time_in,inv.freight_kind,\n" +
            "(CASE WHEN   \n" +
            "fcyVisit.last_pos_loctype ='YARD'   \n" +
            "THEN   \n" +
            "CONCAT('Yard:',IFNULL(ctmsmis.cont_yard(fcyVisit.last_pos_slot),''), \n" +
            "',Block:',IFNULL(ctmsmis.cont_block(fcyVisit.last_pos_slot,ctmsmis.cont_yard(fcyVisit.last_pos_slot)),''),  \n" +
            "',Pos:',CONVERT(fcyVisit.last_pos_slot USING utf8), \n" +
            "',Ctgry:',inv.category,',Status:',inv.freight_kind, \n" +
            "',Dest:',IFNULL(desti.destination,''), \n" +
            "',MLO:',g.id,\n" +
            "',ISO:',IFNULL(sparcsn4.ref_equip_type.id,''), \n" +
            "',AssnType:',IFNULL((SELECT sparcsn4.inv_unit.flex_string01 FROM sparcsn4.inv_unit WHERE sparcsn4.inv_unit.gkey=inv.gkey),''), \n" +
            "',AssnDate:',IFNULL(fcyVisit.flex_date01,''),  \n" +
            "',DisTime:',IFNULL(fcyVisit.time_in,''),\n" +
            "', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id\n" +
            ")   \n" +
            "WHEN   \n" +
            "fcyVisit.last_pos_loctype ='VESSEL'    \n" +
            "THEN  \n" +
            "CONCAT('Position : ',IFNULL(CONVERT(fcyVisit.last_pos_name USING utf8),''),',Vessel Name : ',sparcsn4.vsl_vessels.name,',Category : ',inv.category,', MLO : ',g.id,', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id)\n" +
            "ELSE \n" +
            "IFNULL(CONCAT('Position : ',CONVERT(fcyVisit.last_pos_name USING utf8),',Vessel Name : ',sparcsn4.vsl_vessels.name,',Category : ',inv.category,',\n" +
            "MLO : ',g.id,', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id),'NO CONTAINER FOUND')  \n" +
            "END) \n" +
            "AS dsc,\n" +
            "IFNULL(ctmsmis.cont_block(fcyVisit.last_pos_slot,ctmsmis.cont_yard(fcyVisit.last_pos_slot)),'') AS block\t\t\t\n" +
            "\n" +
            "FROM sparcsn4.inv_unit inv  \n" +
            "INNER JOIN sparcsn4.inv_unit_fcy_visit fcyVisit ON fcyVisit.unit_gkey=inv.gkey\n" +
            "INNER JOIN sparcsn4.argo_carrier_visit ON (argo_carrier_visit.gkey=inv.declrd_ib_cv OR argo_carrier_visit.gkey=inv.cv_gkey)\n" +
            "INNER JOIN sparcsn4.argo_visit_details ON sparcsn4.argo_visit_details.gkey=sparcsn4.argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessel_visit_details ON sparcsn4.vsl_vessel_visit_details.vvd_gkey=sparcsn4.argo_visit_details.gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessel_classes ON vsl_vessel_classes.gkey=vsl_vessels.vesclass_gkey\n" +
            "INNER JOIN sparcsn4.inv_unit_equip ON inv.gkey=inv_unit_equip.unit_gkey \n" +
            "INNER JOIN  sparcsn4.ref_bizunit_scoped g ON inv.line_op = g.gkey\n" +
            "INNER JOIN sparcsn4.ref_equipment ON inv_unit_equip.eq_gkey=ref_equipment.gkey\n" +
            "INNER JOIN sparcsn4.ref_equip_type ON ref_equipment.eqtyp_gkey=ref_equip_type.gkey \n" +
            "INNER JOIN sparcsn4.inv_goods desti ON desti.gkey=inv.goods\n" +
            "WHERE inv.id =:cont_number ORDER BY inv.gkey DESC LIMIT 1", nativeQuery = true)
    public List[] getLocationForIMPRT1(@Param("cont_number") String cont_number);

    @Query(value = "SELECT sparcsn4.inv.id,fcyVisit.transit_state,fcyVisit.time_in,inv.freight_kind,\n" +
            "(CASE WHEN   \n" +
            "fcyVisit.last_pos_loctype ='YARD'   \n" +
            "THEN   \n" +
            "CONCAT('<b>','Position:','Inside Port','</b><br>',\t\t\n" +
            "',DisTime:',IFNULL(CAST(fcyVisit.time_in AS char),''))   \n" +
            "WHEN   \n" +
            "fcyVisit.last_pos_loctype ='VESSEL'    \n" +
            "THEN  \n" +
            "CONCAT('Position : ',IFNULL(CONVERT(fcyVisit.last_pos_name USING utf8),''),'<br>',',Vessel Name : ',sparcsn4.vsl_vessels.name,',Category : ',inv.category,', MLO : ',g.id,', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id)\n" +
            "ELSE \n" +
            "IFNULL(CONCAT('Position : ',CONVERT(fcyVisit.last_pos_name USING utf8),'<br>',',Vessel Name : ',sparcsn4.vsl_vessels.name,',Category : ',inv.category,',\n" +
            "MLO : ',g.id,', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id),'NO CONTAINER FOUND')\n" +
            "END)\n" +
            "AS dsc,\n" +
            "IFNULL(ctmsmis.cont_block(fcyVisit.last_pos_slot,ctmsmis.cont_yard(fcyVisit.last_pos_slot)),'') AS block\t\t\t\n" +
            "FROM sparcsn4.inv_unit inv  \n" +
            "INNER JOIN sparcsn4.inv_unit_fcy_visit fcyVisit ON fcyVisit.unit_gkey=inv.gkey\n" +
            "INNER JOIN sparcsn4.argo_carrier_visit ON (argo_carrier_visit.gkey=inv.declrd_ib_cv OR argo_carrier_visit.gkey=inv.cv_gkey)\n" +
            "INNER JOIN sparcsn4.argo_visit_details ON sparcsn4.argo_visit_details.gkey=sparcsn4.argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessel_visit_details ON sparcsn4.vsl_vessel_visit_details.vvd_gkey=sparcsn4.argo_visit_details.gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessel_classes ON vsl_vessel_classes.gkey=vsl_vessels.vesclass_gkey\n" +
            "INNER JOIN sparcsn4.inv_unit_equip ON inv.gkey=inv_unit_equip.unit_gkey \n" +
            "INNER JOIN  sparcsn4.ref_bizunit_scoped g ON inv.line_op = g.gkey\n" +
            "INNER JOIN sparcsn4.ref_equipment ON inv_unit_equip.eq_gkey=ref_equipment.gkey\n" +
            "INNER JOIN sparcsn4.ref_equip_type ON ref_equipment.eqtyp_gkey=ref_equip_type.gkey \n" +
            "INNER JOIN sparcsn4.inv_goods desti ON desti.gkey=inv.goods\n" +
            "WHERE inv.id =:cont_number ORDER BY inv.gkey DESC LIMIT 1", nativeQuery = true)
    public List[] getLocationForIMPRT2(@Param("cont_number") String cont_number);

    @Query(value = "SELECT sparcsn4.inv.id,fcyVisit.transit_state,fcyVisit.time_in,freight_kind,\n" +
            "(CASE WHEN   \n" +
            "fcyVisit.last_pos_loctype ='YARD'   \n" +
            "THEN   \n" +
            "CONCAT('Yard:',IFNULL(ctmsmis.cont_yard(fcyVisit.last_pos_slot),''),  \n" +
            "', Block:',IFNULL(ctmsmis.cont_block(fcyVisit.last_pos_slot,ctmsmis.cont_yard(fcyVisit.last_pos_slot)),''), \n" +
            "',Position:',IFNULL(CONVERT(fcyVisit.last_pos_slot USING utf8),''),'<br>',\n" +
            "', MLO:',g.id,',Status : ',inv.freight_kind,\n" +
            "',Category:',inv.category,',Gate In:',IFNULL(CAST(fcyVisit.time_in AS char),''),',\n" +
            "Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id) \n" +
            "WHEN   \n" +
            "fcyVisit.last_pos_loctype ='VESSEL'    \n" +
            "THEN  \n" +
            "CONCAT('Position : ',IFNULL(CONVERT(fcyVisit.last_pos_name USING utf8),''),'<br>',',Vessel Name : ',sparcsn4.vsl_vessels.name,', Category : ',inv.category,', Load Time : ',IFNULL(CAST(fcyVisit.time_load AS char),''),', MLO : ',g.id,', Status : ',inv.freight_kind,', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),', ISO Code : ',sparcsn4.ref_equip_type.id)\n" +
            "ELSE \n" +
            "CONCAT('PRE ADVISED : ',IFNULL(CONVERT(fcyVisit.last_pos_name USING utf8),''), \n" +
            "',Category : ',CONCAT (inv.category ,', ', \n" +
            "IFNULL((SELECT CONCAT (CASE WHEN sub_type='DE' THEN 'Dray Off'   \n" +
            "WHEN sub_type='DI' THEN 'Delivery Import'   \n" +
            "WHEN sub_type='DM' THEN 'Delivery EMPTY'   \n" +
            "WHEN sub_type='RE' THEN 'INBOUND'   \n" +
            "END  ,' to Offdock :', NAME) AS d FROM sparcsn4.road_truck_transactions   \n" +
            "INNER JOIN sparcsn4.ref_bizunit_scoped ON road_truck_transactions.trkco_id=ref_bizunit_scoped.id   \n" +
            "WHERE unit_gkey=inv.gkey LIMIT 1),'') \n" +
            "),',MLO : ',g.id,',Status : ',inv.freight_kind,', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id   \n" +
            ")  \n" +
            "END) \n" +
            "AS dsc  ,\n" +
            "IFNULL(ctmsmis.cont_block(fcyVisit.last_pos_slot,ctmsmis.cont_yard(fcyVisit.last_pos_slot)),'') AS block\n" +
            "FROM sparcsn4.inv_unit inv  \n" +
            "INNER JOIN sparcsn4.inv_unit_fcy_visit fcyVisit ON fcyVisit.unit_gkey=inv.gkey\n" +
            "INNER JOIN sparcsn4.argo_carrier_visit ON (argo_carrier_visit.gkey=inv.declrd_ib_cv OR argo_carrier_visit.gkey=inv.cv_gkey)\n" +
            "INNER JOIN sparcsn4.argo_visit_details ON sparcsn4.argo_visit_details.gkey=sparcsn4.argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessel_visit_details ON sparcsn4.vsl_vessel_visit_details.vvd_gkey=sparcsn4.argo_visit_details.gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessels ON vsl_vessels.gkey=vsl_vessel_visit_details.vessel_gkey\n" +
            "INNER JOIN sparcsn4.inv_unit_equip ON inv.gkey=inv_unit_equip.unit_gkey \n" +
            "INNER JOIN  sparcsn4.ref_bizunit_scoped g ON inv.line_op = g.gkey\n" +
            "INNER JOIN sparcsn4.ref_equipment ON inv_unit_equip.eq_gkey=ref_equipment.gkey\n" +
            "INNER JOIN sparcsn4.ref_equip_type ON ref_equipment.eqtyp_gkey=ref_equip_type.gkey \n" +
            "INNER JOIN sparcsn4.inv_goods desti ON desti.gkey=inv.goods\n" +
            "WHERE inv.id =:cont_number ORDER BY inv.gkey DESC LIMIT 1", nativeQuery = true)
    public List[] getLocationForEXPRT(@Param("cont_number") String cont_number);

    @Query(value = "SELECT sparcsn4.inv.id,fcyVisit.transit_state,fcyVisit.time_in,inv.freight_kind,\n" +
            "CONCAT('Position:',fcyVisit.last_pos_name,'<br>',\n" +
            "',Category:',inv.category,\n" +
            "',Freight Kind:',inv.freight_kind, \n" +
            "',Time Move:',IFNULL(CAST(fcyVisit.time_move AS char),''),', MLO : ',g.id,', Height : ',\n" +
            "RIGHT(sparcsn4.ref_equip_type.nominal_height,2)/10,', Size : ',RIGHT(sparcsn4.ref_equip_type.nominal_length,2),',ISO Code : ',sparcsn4.ref_equip_type.id)       \n" +
            "AS dsc,'' AS block \n" +
            "FROM sparcsn4.inv_unit inv  \n" +
            "INNER JOIN sparcsn4.inv_unit_fcy_visit fcyVisit ON fcyVisit.unit_gkey=inv.gkey\n" +
            "INNER JOIN sparcsn4.inv_unit_equip ON inv.gkey=inv_unit_equip.unit_gkey \n" +
            "INNER JOIN  sparcsn4.ref_bizunit_scoped g ON inv.line_op = g.gkey\n" +
            "INNER JOIN sparcsn4.ref_equipment ON inv_unit_equip.eq_gkey=ref_equipment.gkey\n" +
            "INNER JOIN sparcsn4.ref_equip_type ON ref_equipment.eqtyp_gkey=ref_equip_type.gkey \n" +
            "INNER JOIN sparcsn4.inv_goods desti ON desti.gkey=inv.goods\n" +
            "WHERE inv.id =:cont_number ORDER BY inv.gkey DESC LIMIT 1", nativeQuery = true)
    public List[] getLocation(@Param("cont_number") String cont_number);

    @Query(value = "SELECT \n" +
            "(SELECT COUNT(*) FROM sparcsn4.srv_event WHERE sparcsn4.srv_event.applied_to_gkey=sparcsn4.inv_unit.gkey AND event_type_gkey=30) AS rtnValue\n" +
            "FROM sparcsn4.inv_unit WHERE id=:cont_number AND category='IMPRT' ORDER BY sparcsn4.inv_unit.gkey DESC LIMIT 1", nativeQuery = true)
    public List[] getEVT(@Param("cont_number") String cont_number);
}

