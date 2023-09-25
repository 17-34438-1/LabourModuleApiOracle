package com.datasoft.LaborModuleAPI.Repository.WorkCategoryRepositories;

import com.datasoft.LaborModuleAPI.Model.WorkCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface WorkCategoryInsertRepository extends CrudRepository<WorkCategory, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_category WHERE name=:name", nativeQuery = true)
    Integer isExist(@Param("name") String name);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_work_category(name,created_at) VALUES(:name,NOW())", nativeQuery = true)
    @Transactional
    Integer workCategoryInsertQuery(@Param("name") String name);
}
