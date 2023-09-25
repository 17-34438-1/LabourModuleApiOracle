package com.datasoft.LaborModuleAPI.Repository.GangRepositories;

import com.datasoft.LaborModuleAPI.Model.Gang;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GangListRepository extends CrudRepository<Gang, Long> {
    @Query(value = "SELECT ctmsmis.lm_gang.*,ctmsmis.lm_work_category.name AS work_category_name\n" +
            "FROM ctmsmis.lm_gang\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_gang.work_category_id=ctmsmis.lm_work_category.id", nativeQuery = true)
    public List<Gang> gangList();

    @Query(value = "SELECT ctmsmis.lm_gang.*,ctmsmis.lm_work_category.name AS work_category_name\n" +
            "FROM ctmsmis.lm_gang\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_gang.work_category_id=ctmsmis.lm_work_category.id\n" +
            "WHERE ctmsmis.lm_gang.berth_operator_id=:org_id", nativeQuery = true)
    public List<Gang> gangListByBerthOperator(@Param("org_id") Integer org_id);

    @Query(value = "SELECT ctmsmis.lm_gang.*,ctmsmis.lm_work_category.name  AS work_category_name\n" +
            "FROM ctmsmis.lm_gang\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_gang.work_category_id=ctmsmis.lm_work_category.id\n" +
            "WHERE ctmsmis.lm_gang.id=:id", nativeQuery = true)
    public Gang gangById(@Param("id") Long id);

    @Query(value = "SELECT ctmsmis.lm_gang.*,ctmsmis.lm_work_category.name AS work_category_name\n" +
            "FROM ctmsmis.lm_gang\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_gang.work_category_id=ctmsmis.lm_work_category.id\n" +
            "WHERE ctmsmis.lm_gang.berth_operator_id=:berth_operator_id", nativeQuery = true)
    public List<Gang> gangListByOrg(@Param("berth_operator_id") Long berth_operator_id);

    @Query(value = "SELECT ctmsmis.lm_gang.*,ctmsmis.lm_work_category.name AS work_category_name\n" +
            "FROM ctmsmis.lm_gang\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_gang.work_category_id=ctmsmis.lm_work_category.id\n" +
            "WHERE ctmsmis.lm_gang.berth_operator_id=:berth_operator_id AND work_category_id=:work_category_id", nativeQuery = true)
    public List<Gang> gangListByOrgAndWorkCategory(@Param("berth_operator_id") Long berth_operator_id,
                                                   @Param("work_category_id") Long work_category_id);
}
