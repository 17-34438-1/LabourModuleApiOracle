package com.datasoft.LaborModuleAPI.Repository.LaborRepositories;

import com.datasoft.LaborModuleAPI.Model.Labor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LaborDetailsListRepository extends CrudRepository<Labor, Long> {
    @Query(value = "SELECT ctmsmis.lm_labor.*,ctmsmis.lm_labor_type.name AS TYPE,ctmsmis.lm_designation_category.name AS designation\n" +
            "FROM ctmsmis.lm_labor\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
            "INNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id", nativeQuery = true)
    public List<Labor> laborList();

    @Query(value = "SELECT ctmsmis.lm_labor.*,ctmsmis.lm_labor_type.name AS TYPE,ctmsmis.lm_designation_category.name AS designation\n" +
            "FROM ctmsmis.lm_labor\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
            "INNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id\n" +
            "WHERE ctmsmis.lm_labor.id=:id", nativeQuery = true)
    public Labor laborInfoById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor_details_info WHERE labor_id=:laborId", nativeQuery = true)
    Integer laborChkById(@Param("laborId") String laborId);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor_assign_to_gang WHERE labor_info_id=:laborId", nativeQuery = true)
    Integer laborAssignmentChkById(@Param("laborId") String laborId);

    @Query(value = "SELECT ctmsmis.lm_labor_details_info.*,ctmsmis.lm_labor_category_info.labor_cat_name," +
            "ctmsmis.lm_desig_category_info.desig_cat_name\n" +
            "FROM ctmsmis.lm_labor_details_info\n" +
            "INNER JOIN ctmsmis.lm_labor_category_info ON ctmsmis.lm_labor_details_info.labor_cat_info_id=ctmsmis.lm_labor_category_info.id\n" +
            "INNER JOIN ctmsmis.lm_desig_category_info ON ctmsmis.lm_labor_details_info.labor_desig_info_id=ctmsmis.lm_desig_category_info.id\n" +
            "WHERE ctmsmis.lm_labor_details_info.labor_id=:laborId", nativeQuery = true)
    public Labor laborDetailsinfoListByLaborId(@Param("laborId") String laborId);

    @Query(value = "SELECT ctmsmis.lm_labor.*,ctmsmis.lm_labor_type.name AS TYPE,ctmsmis.lm_designation_category.name AS designation\n" +
            "FROM ctmsmis.lm_labor\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
            "INNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id\n" +
            "WHERE (ctmsmis.lm_labor.labor_type_id=:labor_type_id OR ctmsmis.lm_labor.labor_type_id IS NULL) AND " +
            "ctmsmis.lm_labor.designation_category_id=:designation_category_id AND ctmsmis.lm_labor.id NOT IN \n" +
            "(SELECT ctmsmis.lm_labor.id\n" +
            "FROM ctmsmis.lm_labor\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
            "INNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id\n" +
            "INNER JOIN ctmsmis.lm_assign_labor_to_gang ON ctmsmis.lm_labor.id=ctmsmis.lm_assign_labor_to_gang.labor_id\n" +
            "WHERE (ctmsmis.lm_labor.labor_type_id=:labor_type_id OR ctmsmis.lm_labor.labor_type_id IS NULL) AND " +
            "ctmsmis.lm_labor.designation_category_id=:designation_category_id)", nativeQuery = true)
    public List<Labor> getLaborInfoByTypeAndDesignation(@Param("labor_type_id") Long labor_type_id,
                                                        @Param("designation_category_id") Long designation_category_id);

    @Query(value = "SELECT ctmsmis.lm_labor.*,ctmsmis.lm_labor_type.name AS TYPE,ctmsmis.lm_designation_category.name AS designation\n" +
            "FROM ctmsmis.lm_labor\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
            "INNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id\n" +
            "WHERE (ctmsmis.lm_labor.labor_type_id=:labor_type_id OR ctmsmis.lm_labor.labor_type_id IS NULL) AND \n" +
            "ctmsmis.lm_labor.designation_category_id=:designation_category_id AND ctmsmis.lm_labor.berth_operator_id=:berth_operator_id AND \n" +
            "ctmsmis.lm_labor.id NOT IN (\n" +
            "SELECT ctmsmis.lm_assign_labor_to_gang.labor_id FROM ctmsmis.lm_assign_labor_to_gang WHERE gang_id=:gang_id\n" +
            ")", nativeQuery = true)
    public List<Labor> availableLaborList(@Param("labor_type_id") Long labor_type_id,
                                            @Param("designation_category_id") Long designation_category_id,
                                            @Param("berth_operator_id") Long berth_operator_id,
                                            @Param("gang_id") Integer gang_id);

//    @Query(value = "SELECT ctmsmis.lm_labor.*,ctmsmis.lm_labor_type.name AS TYPE,ctmsmis.lm_designation_category.name AS designation\n" +
//            "FROM ctmsmis.lm_labor\n" +
//            "INNER JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
//            "INNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id\n" +
//            "WHERE ctmsmis.lm_labor.labor_type_id=:labor_type_id AND ctmsmis.lm_labor.designation_category_id=:designation_category_id\n" +
//            "AND ctmsmis.lm_labor.id NOT IN (\n" +
//            "\tSELECT ctmsmis.lm_labor.id\n" +
//            "\tFROM ctmsmis.lm_labor\n" +
//            "\tINNER JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
//            "\tINNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id\n" +
//            "\tINNER JOIN ctmsmis.lm_assign_labor_to_gang ON ctmsmis.lm_labor.id=ctmsmis.lm_assign_labor_to_gang.labor_id\n" +
//            "\tWHERE ctmsmis.lm_labor.labor_type_id=:labor_type_id AND ctmsmis.lm_labor.designation_category_id=:designation_category_id\n" +
//            ")", nativeQuery = true)
//    public List<Labor> getLaborInfoByTypeAndDesignation(@Param("labor_type_id") Long labor_type_id,@Param("designation_category_id") Long designation_category_id);

    @Query(value = "SELECT ctmsmis.lm_labor.*,ctmsmis.lm_labor_type.name AS TYPE,ctmsmis.lm_designation_category.name AS designation\n" +
            "FROM ctmsmis.lm_labor\n" +
            "LEFT JOIN ctmsmis.lm_labor_type ON ctmsmis.lm_labor.labor_type_id=lm_labor_type.id\n" +
            "INNER JOIN ctmsmis.lm_designation_category ON ctmsmis.lm_labor.designation_category_id=lm_designation_category.id\n" +
            "WHERE ctmsmis.lm_labor.org_id=:org_id", nativeQuery = true)
    public List<Labor> getLaborListByOrg(@Param("org_id") Integer org_id);
}
