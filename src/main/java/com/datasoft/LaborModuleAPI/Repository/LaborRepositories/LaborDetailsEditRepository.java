package com.datasoft.LaborModuleAPI.Repository.LaborRepositories;

import com.datasoft.LaborModuleAPI.Model.Labor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface LaborDetailsEditRepository extends CrudRepository<Labor, Long> {
    @Query(value = "SELECT id FROM ctmsmis.lm_labor WHERE (nid=:nid)", nativeQuery = true)
    Long findLaborIdByNID(@Param("nid") String nid);

    @Query(value = "SELECT id FROM ctmsmis.lm_labor WHERE (phone=:phone)", nativeQuery = true)
    Long findLaborIdByPhone(@Param("phone") String phone);

    @Query(value = "SELECT id FROM ctmsmis.lm_labor WHERE (entry_pass_no=:entryPassNo)", nativeQuery = true)
    Long findLaborIdByEntryPassNo(@Param("entryPassNo") String entryPassNo);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor " +
            "WHERE (nid=:nid OR phone=:phone OR entry_pass_no=:entry_pass_no) AND (id!=:id)", nativeQuery = true)
    Integer isExist(@Param("nid") String nid,@Param("phone") String phone,@Param("entry_pass_no") String entry_pass_no);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor_type WHERE id=:labor_type_id", nativeQuery = true)
    Integer laborTypeExistence(@Param("labor_type_id") Long labor_type_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_designation_category WHERE id=:designation_category_id", nativeQuery = true)
    Integer laborDesignationExistence(@Param("designation_category_id") Long designation_category_id);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_labor SET name=:name, labor_type_id=:labor_type_id,designation_category_id=:designation_category_id,\n" +
            "berth_operator_id=:berth_operator_id,org_id=:org_id,nid=:nid,phone=:phone,entry_pass_no=:entry_pass_no,expiration_date=:expiration_date,\n" +
            "photo=:photo,present_address=:present_address,permanent_address=:permanent_address,date_of_birth=:date_of_birth,date_of_joining=:date_of_joining,\n" +
            "updated_at=NOW() WHERE ctmsmis.lm_labor.id=:id", nativeQuery = true)
    @Transactional
    Integer laborUpdate(@Param("id") Long id,
                        @Param("name") String name,
                        @Param("labor_type_id") Long labor_type_id,
                        @Param("designation_category_id") Long designation_category_id,
                        @Param("berth_operator_id") Long berth_operator_id,
                        @Param("org_id") Integer org_id,
                        @Param("nid") String nid,
                        @Param("phone") String phone,
                        @Param("entry_pass_no") String entry_pass_no,
                        @Param("expiration_date") Date expiration_date,
                        @Param("photo") String photo,
                        @Param("present_address") String present_address,
                        @Param("permanent_address") String permanent_address,
                        @Param("date_of_birth") Date date_of_birth,
                        @Param("date_of_joining") Date date_of_joining);
}
