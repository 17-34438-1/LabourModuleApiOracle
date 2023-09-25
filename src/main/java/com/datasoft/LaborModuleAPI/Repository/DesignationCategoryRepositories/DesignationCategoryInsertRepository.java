package com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface DesignationCategoryInsertRepository extends CrudRepository<DesignationCategory, Long> {

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor_type WHERE id=:labor_type_id", nativeQuery = true)
    Integer isLaborTypeExists(@Param("labor_type_id") Long labor_type_id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_designation_category WHERE name=:name AND labor_type_id=:labor_type_id", nativeQuery = true)
    Integer isExists(@Param("name") String name,@Param("labor_type_id") Long labor_type_id);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_designation_category(name,labor_type_id,created_at) \n" +
            "VALUES(:name,:labor_type_id,NOW())", nativeQuery = true)
    @Transactional
    Integer insertDesigCatQuery(@Param("name") String name,@Param("labor_type_id") Long labor_type_id);
}
