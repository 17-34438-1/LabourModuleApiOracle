package com.datasoft.LaborModuleAPI.Repository.WorkLocationRepository;

import com.datasoft.LaborModuleAPI.Model.WorkLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkLocationListRepository extends CrudRepository<WorkLocation, Long> {
    @Query(value = "SELECT ctmsmis.lm_work_location.*,ctmsmis.lm_work_category.name AS work_category_name\n" +
            "FROM ctmsmis.lm_work_location \n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_work_location.work_category_id=ctmsmis.lm_work_category.id", nativeQuery = true)
    public List<WorkLocation> workLocationList();

    @Query(value = "SELECT ctmsmis.lm_work_location.*,ctmsmis.lm_work_category.name AS work_category_name\n" +
            "FROM ctmsmis.lm_work_location \n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_work_location.work_category_id=ctmsmis.lm_work_category.id\n" +
            "WHERE ctmsmis.lm_work_location.id=:id", nativeQuery = true)
    public WorkLocation workLocationById(@Param("id") Long id);

    @Query(value = "SELECT ctmsmis.lm_work_location.*,ctmsmis.lm_work_category.id AS work_category_id,ctmsmis.lm_work_category.name AS work_category_name\n" +
            "FROM ctmsmis.lm_work_location\n" +
            "INNER JOIN ctmsmis.lm_work_category ON ctmsmis.lm_work_location.work_category_id=ctmsmis.lm_work_category.id\n" +
            "WHERE ctmsmis.lm_work_location.work_category_id=:work_category_id", nativeQuery = true)
    public List<WorkLocation> workLocationListByWorkCategory(@Param("work_category_id") Long work_category_id);

    @Query(value = "SELECT work_category_id FROM ctmsmis.lm_gang WHERE ctmsmis.lm_gang.id=:gang_id", nativeQuery = true)
    public Long getWorkCategorybyGangId(@Param("gang_id") Long gang_id);
}
