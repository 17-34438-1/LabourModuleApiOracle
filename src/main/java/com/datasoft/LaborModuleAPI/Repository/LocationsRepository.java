package com.datasoft.LaborModuleAPI.Repository;

import com.datasoft.LaborModuleAPI.Model.Locations;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface LocationsRepository extends CrudRepository<Locations, Long> {
    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_locations WHERE name=:name", nativeQuery = true)
    Integer isExists(@Param("name") String name);

    @Modifying()
    @Query(value = "INSERT INTO ctmsmis.lm_locations(name,created_at) VALUES(:name,NOW())", nativeQuery = true)
    @Transactional
    Integer workLocationsInsert(@Param("name") String name);

    @Query(value = "SELECT * FROM ctmsmis.lm_locations", nativeQuery = true)
    public List<Locations> workLocationsList();

    @Query(value = "SELECT * FROM ctmsmis.lm_locations WHERE ctmsmis.lm_locations.id=:id", nativeQuery = true)
    public Locations workLocationById(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM ctmsmis.lm_locations WHERE (name=:name) AND (id!=:id)", nativeQuery = true)
    Integer isUnique(@Param("id") Long id,@Param("name") String name);

    @Modifying()
    @Query(value = "UPDATE ctmsmis.lm_locations SET name=:name, updated_at=NOW() WHERE id=:id", nativeQuery = true)
    @Transactional
    Integer WorkLocationUpdate(@Param("id") Long id,@Param("name") String name);
}
