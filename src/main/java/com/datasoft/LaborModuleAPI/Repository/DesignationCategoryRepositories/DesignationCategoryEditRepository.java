package com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface DesignationCategoryEditRepository extends CrudRepository<DesignationCategory, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_designation_category " +
            "WHERE (name=:name AND labor_type_id=:labor_type_id) AND (id!=:id)", nativeQuery = true)
    Integer chkCategoryforEdit(@Param("id") Long id,@Param("labor_type_id") Long labor_type_id,@Param("name") String name);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_designation_category SET name=:name,labor_type_id=:labor_type_id,\n" +
            "updated_at=NOW() WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer updateDesignationCategory(@Param("id") Long id,@Param("labor_type_id") Long labor_type_id,@Param("name") String name);
}
