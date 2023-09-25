package com.datasoft.LaborModuleAPI.Repository.LaborAssignToGangRepository;

import com.datasoft.LaborModuleAPI.Model.LaborAssignToGang;
import com.datasoft.LaborModuleAPI.Model.LaborByGang;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LaborAssignmentListRepository extends CrudRepository<LaborAssignToGang, Long> {
    @Query(value = "SELECT ctmsmis.lm_assign_labor_to_gang.*,lm_gang.name AS gang_name,lm_labor.name AS labor_name\n" +
            "FROM ctmsmis.lm_assign_labor_to_gang\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_labor_to_gang.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_labor ON ctmsmis.lm_assign_labor_to_gang.labor_id=ctmsmis.lm_labor.id", nativeQuery = true)
    public List<LaborAssignToGang> laborAssignmentList();

    @Query(value = "SELECT ctmsmis.lm_assign_labor_to_gang.*,lm_gang.name AS gang_name,lm_labor.name AS labor_name\n" +
            "FROM ctmsmis.lm_assign_labor_to_gang\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_labor_to_gang.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_labor ON ctmsmis.lm_assign_labor_to_gang.labor_id=ctmsmis.lm_labor.id\n" +
            "WHERE ctmsmis.lm_assign_labor_to_gang.id=:id", nativeQuery = true)
    public LaborAssignToGang laborAssignmentListById(@Param("id") Long id);

    @Query(value = "SELECT ctmsmis.lm_assign_labor_to_gang.*,lm_gang.name AS gang_name,lm_labor.name AS labor_name\n" +
            "FROM ctmsmis.lm_assign_labor_to_gang\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_labor_to_gang.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_labor ON ctmsmis.lm_assign_labor_to_gang.labor_id=ctmsmis.lm_labor.id\n" +
            "WHERE ctmsmis.lm_assign_labor_to_gang.gang_id=:gang_id", nativeQuery = true)
    public List<LaborAssignToGang> laborAssignmentListByGangId(@Param("gang_id") Long gang_id);


}
