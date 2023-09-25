package com.datasoft.LaborModuleAPI.Repository;

import com.datasoft.LaborModuleAPI.Model.LCLAssignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LCLAssignmentRepository extends CrudRepository<LCLAssignment, Long> {
    @Query(value = "SELECT argo_carrier_visit.gkey AS id,ib_vyg AS Import_Rotation_No,'' AS bl_no, '' AS cont_no,'' AS assignment_date,'' AS cont_loc_shed\n" +
            "FROM sparcsn4.argo_carrier_visit\n" +
            "INNER JOIN sparcsn4.argo_visit_details ON sparcsn4.argo_visit_details.gkey=sparcsn4.argo_carrier_visit.cvcvd_gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessel_visit_details ON sparcsn4.vsl_vessel_visit_details.vvd_gkey=sparcsn4.argo_visit_details.gkey\n" +
            "INNER JOIN sparcsn4.vsl_vessels ON sparcsn4.vsl_vessels.gkey=sparcsn4.vsl_vessel_visit_details.vessel_gkey\n" +
            "WHERE sparcsn4.argo_carrier_visit.phase IN('20INBOUND','40WORKING') \n" +
            "AND (ib_vyg NOT IN (SELECT rotation FROM ctmsmis.lm_assign_gang_to_workplace))", nativeQuery = true)
    public List<LCLAssignment> notAssignedRotationList();
}
