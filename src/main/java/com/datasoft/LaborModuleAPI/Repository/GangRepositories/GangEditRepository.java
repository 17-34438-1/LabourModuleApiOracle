package com.datasoft.LaborModuleAPI.Repository.GangRepositories;

import com.datasoft.LaborModuleAPI.Model.Gang;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface GangEditRepository extends CrudRepository<Gang, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_gang WHERE (name=:name) AND (id!=:id)", nativeQuery = true)
    Integer isExist(@Param("id") Long id,@Param("name") String name);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_gang SET name=:name,work_category_id=:work_category_id," +
            "berth_operator_id=:berth_operator_id,updated_at=NOW() WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer gangEdit(@Param("id") Long id,
                     @Param("name") String name,
                     @Param("work_category_id") Long work_category_id,
                     @Param("berth_operator_id") Long berth_operator_id);
}
