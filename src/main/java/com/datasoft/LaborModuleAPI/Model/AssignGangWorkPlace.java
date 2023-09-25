package com.datasoft.LaborModuleAPI.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="lm_assign_gang_to_workplace" )
public class AssignGangWorkPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
    @Column(name=" actual_end_time")
    private Date actualEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
}
