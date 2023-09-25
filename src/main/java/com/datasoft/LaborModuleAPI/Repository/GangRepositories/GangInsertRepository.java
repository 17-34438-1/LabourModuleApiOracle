package com.datasoft.LaborModuleAPI.Repository.GangRepositories;

import com.datasoft.LaborModuleAPI.Model.Gang;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface GangInsertRepository extends CrudRepository<Gang, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_gang WHERE name=:name", nativeQuery = true)
    Integer isExist(@Param("name") String name);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_work_category WHERE id=:work_category_id", nativeQuery = true)
    Integer isWorkCategoryExist(@Param("work_category_id") Long work_category_id);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_gang(name,work_category_id,berth_operator_id,created_at) VALUES(:name,:work_category_id,:berth_operator_id,NOW())", nativeQuery = true)
    @Transactional
    Integer gangInsertQuery(@Param("name") String name,
                            @Param("work_category_id") Long work_category_id,
                            @Param("berth_operator_id") Long berth_operator_id);
}
