package com.datasoft.LaborModuleAPI.Repository.GangEnd;

import com.datasoft.LaborModuleAPI.Model.AssignGangWorkPlace;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface GangEndRepository extends CrudRepository<AssignGangWorkPlace, Integer> {
    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_assign_gang_to_workplace\n" +
            "SET actual_end_time=NOW()\n" +
            "WHERE id=:id",nativeQuery = true)
    @Transactional
    public Integer updateActualEndTime(@Param("id") Integer id);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_gang_assignment_wise_labor SET end_time=NOW() WHERE gang_assignment_id=:id AND end_time IS NULL",nativeQuery = true)
    @Transactional
    public Integer updateLaborEndTime(@Param("id") Integer id);
}
