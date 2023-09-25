package com.datasoft.LaborModuleAPI.Repository.DesignationCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.DesignationCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DesignationCategoryListRepository extends CrudRepository<DesignationCategory, Long> {
    @Query(value = "SELECT ctmsmis.lm_designation_category.*,ctmsmis.lm_labor_type.name AS labor_type_name\n" +
            "FROM ctmsmis.lm_designation_category\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_designation_category.labor_type_id=ctmsmis.lm_labor_type.id", nativeQuery = true)
    public List<DesignationCategory> DesignationCategoryListQuery();

    @Query(value = "SELECT ctmsmis.lm_designation_category.*,ctmsmis.lm_labor_type.name AS labor_type_name\n" +
            "FROM ctmsmis.lm_designation_category\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_designation_category.labor_type_id=ctmsmis.lm_labor_type.id\n" +
            "WHERE ctmsmis.lm_designation_category.id=:id", nativeQuery = true)
    public DesignationCategory getDesignationCategoryById(@Param("id") Long id);

    @Query(value = "SELECT ctmsmis.lm_designation_category.*,ctmsmis.lm_labor_type.name AS labor_type_name\n" +
            "FROM ctmsmis.lm_designation_category\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_designation_category.labor_type_id=ctmsmis.lm_labor_type.id\n" +
            "WHERE ctmsmis.lm_designation_category.labor_type_id=:labor_type_id", nativeQuery = true)
    public List<DesignationCategory> designationCategoryListByLaborTypeId(@Param("labor_type_id") Long labor_type_id);

    @Query(value = "SELECT ctmsmis.lm_designation_category.*,ctmsmis.lm_labor_type.name AS labor_type_name\n" +
            "FROM ctmsmis.lm_designation_category\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_designation_category.labor_type_id=ctmsmis.lm_labor_type.id\n" +
            "WHERE ctmsmis.lm_designation_category.id=:id", nativeQuery = true)
    public List<DesignationCategory> laborTypeByDesignationId(@Param("id") Long id);
}
