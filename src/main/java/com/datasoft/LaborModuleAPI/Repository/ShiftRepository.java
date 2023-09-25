package com.datasoft.LaborModuleAPI.Repository;

import com.datasoft.LaborModuleAPI.Model.BerthShift;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShiftRepository extends CrudRepository<BerthShift, Long> {
    @Query(value = "SELECT * FROM ctmsmis.lm_berth_shift_dtl WHERE shift_for=:shift_for", nativeQuery = true)
    public List<BerthShift> shiftListByOrg(@Param("shift_for") String shift_for);
}
