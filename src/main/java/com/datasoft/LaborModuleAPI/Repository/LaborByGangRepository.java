package com.datasoft.LaborModuleAPI.Repository;

import com.datasoft.LaborModuleAPI.Model.LaborByGang;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LaborByGangRepository extends CrudRepository<LaborByGang, Long> {

    @Query(value = "SELECT ctmsmis.lm_labor.id AS labor_id,lm_labor.name AS labor_name,lm_labor.entry_pass_no\n" +
            "FROM ctmsmis.lm_assign_labor_to_gang\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_labor_to_gang.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_labor ON ctmsmis.lm_assign_labor_to_gang.labor_id=ctmsmis.lm_labor.id\n" +
            "WHERE ctmsmis.lm_assign_labor_to_gang.gang_id=:gang_id", nativeQuery = true)
    public List<LaborByGang> laborListByGangId(@Param("gang_id") Long gang_id);
}
