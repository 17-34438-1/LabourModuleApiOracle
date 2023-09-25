package com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.WorkCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkCategoryListRepository extends CrudRepository<WorkCategory, Long> {
    @Query(value = "SELECT * FROM ctmsmis.lm_work_category", nativeQuery = true)
    public List<WorkCategory> workCategoryList();

    @Query(value = "SELECT * FROM ctmsmis.lm_work_category WHERE id=:id", nativeQuery = true)
    public WorkCategory workCategoryListById(@Param("id") Long id);
}
