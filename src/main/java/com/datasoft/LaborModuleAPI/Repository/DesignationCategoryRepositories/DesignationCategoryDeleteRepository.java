package com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface DesignationCategoryDeleteRepository extends CrudRepository<DesignationCategory, Long> {

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_designation_category", nativeQuery = true)
    @Transactional
    Integer deleteAllDesignationCategory();

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor WHERE designation_category_id=:id", nativeQuery = true)
    Integer chkLaborInfo(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_designation_category WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteDesignationCategoryById(@Param("id") Long id);
}
