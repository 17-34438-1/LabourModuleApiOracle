package com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository;

import com.datasoft.LaborModuleAPI.Model.WorkCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface WorkLocationInsertRepository extends CrudRepository<WorkCategory, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_location WHERE name=:name", nativeQuery = true)
    Integer isExists(@Param("name") String name);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_work_location(name,work_category_id,created_at) VALUES(:name,:work_category_id,NOW())", nativeQuery = true)
    @Transactional
    Integer workLocationInsert(@Param("name") String name,@Param("work_category_id") Integer work_category_id);
}
