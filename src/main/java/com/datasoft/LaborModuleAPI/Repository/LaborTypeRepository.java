package com.datasoft.LaborModuleAPI.Repository;

import com.datasoft.LaborModuleAPI.Model.LaborType;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LaborTypeRepository extends CrudRepository<LaborType, Long> {



    @Query(value = "SELECT * FROM ctmsmis.lm_labor_type", nativeQuery = true)
    public List<LaborType> laborTypeListQuery();

    @Query(value = "SELECT * FROM ctmsmis.lm_labor_type WHERE id=:id", nativeQuery = true)
    public List<LaborType> laborTypeListByIdQuery(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor_type " +
            "WHERE name=:name", nativeQuery = true)
    Integer isExists(@Param("name") String name);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_labor_type(name,created_at) \n" +
            "VALUES(:name,NOW())", nativeQuery = true)
    @Transactional
    Integer insertTypeQuery(@Param("name") String name);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor_type " +
                    "WHERE (name=:name) AND (id!=:id)", nativeQuery = true)
    Integer isUnique(@Param("id") Long id,@Param("name") String name);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_labor_type SET name=:name,\n" +
                    "updated_at=NOW() WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer updateLaborType(@Param("id") Long id,@Param("name") String name);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_labor_type", nativeQuery = true)
    @Transactional
    Integer deleteAllLaborType();

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_labor WHERE labor_type_id=:id", nativeQuery = true)
    Integer chkLaborDtlsInfo(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_designation_category WHERE labor_type_id=:id", nativeQuery = true)
    Integer chkDesignation(@Param("id") Long id);

    @Modifying()
    @Query(value = "DELETE FROM ctmsmis.lm_labor_type WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer deleteLaborTypeById(@Param("id") Long id);

    @Query(value = "SELECT * FROM ctmsmis.lm_labor_type WHERE id=:id", nativeQuery = true)
    public LaborType getLaborTypeById(@Param("id") Long id);

}
