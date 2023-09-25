package com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories;

import com.datasoft.LaborModuleAPI.Model.GangAssign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface GangAssignmentListRepository extends CrudRepository<GangAssign, Long> {
    @Query(value = "SELECT ctmsmis.lm_assign_gang_to_workplace.*,ctmsmis.lm_gang.name AS gang_name,ctmsmis.lm_gang.size,\n" +
            "ctmsmis.lm_gang.berth_operator_id,ctmsmis.lm_work_location.name AS work_location_name,\n" +
            "'' AS labor_name,ctmsmis.lm_work_category.id AS work_category_id,\n" +
            "ctmsmis.lm_work_category.name AS work_category_name,ctmsmis.lm_berth_shift_dtl.shift_name\n" +
            "FROM ctmsmis.lm_assign_gang_to_workplace\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_gang_to_workplace.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_work_location ON ctmsmis.lm_assign_gang_to_workplace.work_location_id=ctmsmis.lm_work_location.id\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_work_location.work_category_id=ctmsmis.lm_work_category.id\n" +
            "INNER JOIN ctmsmis.lm_berth_shift_dtl ON ctmsmis.lm_assign_gang_to_workplace.shift=ctmsmis.lm_berth_shift_dtl.id\n" +
            "ORDER BY lm_assign_gang_to_workplace.id DESC", nativeQuery = true)
    public List[] gangAssignmentList();

    @Query(value = "SELECT * FROM ctmsmis.lm_gang_assignment_wise_container WHERE gang_assignment_to_workplace_id=:assignment_id", nativeQuery = true)
    public List[] getContainerByAssignment(@Param("assignment_id") Integer assignment_id);

    @Query(value = "SELECT ctmsmis.lm_assign_gang_to_workplace.*,ctmsmis.lm_gang.name AS gang_name,ctmsmis.lm_gang.size,\n" +
            "ctmsmis.lm_gang.berth_operator_id,ctmsmis.lm_work_location.name AS work_location_name,\n" +
            "'' AS labor_name,ctmsmis.lm_work_category.id AS work_category_id,\n" +
            "ctmsmis.lm_work_category.name AS work_category_name,ctmsmis.lm_berth_shift_dtl.shift_name\n" +
            "FROM ctmsmis.lm_assign_gang_to_workplace\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_gang_to_workplace.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_work_location ON ctmsmis.lm_assign_gang_to_workplace.work_location_id=ctmsmis.lm_work_location.id\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_work_location.work_category_id=ctmsmis.lm_work_category.id\n" +
            "INNER JOIN ctmsmis.lm_berth_shift_dtl ON ctmsmis.lm_assign_gang_to_workplace.shift=ctmsmis.lm_berth_shift_dtl.id\n" +
            "WHERE ctmsmis.lm_assign_gang_to_workplace.id=:id\n" +
            "ORDER BY lm_assign_gang_to_workplace.id DESC", nativeQuery = true)
    public List[] gangAssignmentById(@Param("id") Long id);

    @Query(value = "SELECT ctmsmis.lm_assign_gang_to_workplace.*,ctmsmis.lm_gang.name AS gang_name,ctmsmis.lm_gang.size,\n" +
            "ctmsmis.lm_gang.berth_operator_id,ctmsmis.lm_work_location.name AS work_location_name,\n" +
            "'' AS labor_name,ctmsmis.lm_work_category.id AS work_category_id,\n" +
            "ctmsmis.lm_work_category.name AS work_category_name,ctmsmis.lm_berth_shift_dtl.shift_name\n" +
            "FROM ctmsmis.lm_assign_gang_to_workplace\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_gang_to_workplace.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_work_location ON ctmsmis.lm_assign_gang_to_workplace.work_location_id=ctmsmis.lm_work_location.id\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_work_location.work_category_id=ctmsmis.lm_work_category.id\n" +
            "INNER JOIN ctmsmis.lm_berth_shift_dtl ON ctmsmis.lm_assign_gang_to_workplace.shift=ctmsmis.lm_berth_shift_dtl.id\n" +
            "WHERE ctmsmis.lm_gang.berth_operator_id=:berth_operator_id\n" +
            "ORDER BY lm_assign_gang_to_workplace.id DESC", nativeQuery = true)
    public List[] gangAssignmentListByOrg(@Param("berth_operator_id") Long berth_operator_id);

    @Query(value = "SELECT ctmsmis.lm_assign_gang_to_workplace.*,ctmsmis.lm_gang.name AS gang_name,ctmsmis.lm_gang.size,\n" +
            "ctmsmis.lm_gang.berth_operator_id,ctmsmis.lm_work_location.name AS work_location_name,\n" +
            "'' AS labor_name,ctmsmis.lm_work_category.id AS work_category_id,\n" +
            "ctmsmis.lm_work_category.name AS work_category_name,ctmsmis.lm_berth_shift_dtl.shift_name\n" +
            "FROM ctmsmis.lm_assign_gang_to_workplace\n" +
            "INNER JOIN ctmsmis.lm_gang ON ctmsmis.lm_assign_gang_to_workplace.gang_id=ctmsmis.lm_gang.id\n" +
            "INNER JOIN ctmsmis.lm_work_location ON ctmsmis.lm_assign_gang_to_workplace.work_location_id=ctmsmis.lm_work_location.id\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_work_location.work_category_id=ctmsmis.lm_work_category.id\n" +
            "INNER JOIN ctmsmis.lm_berth_shift_dtl ON ctmsmis.lm_assign_gang_to_workplace.shift=ctmsmis.lm_berth_shift_dtl.id\n" +
            "WHERE gang_id=:gang_id AND actual_end_time IS NULL\n" +
            "ORDER BY lm_assign_gang_to_workplace.id DESC", nativeQuery = true)
    public List<GangAssign> runningAssignmentListByGang(@Param("gang_id") Long gang_id);

}
