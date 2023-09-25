package com.datasoft.LaborModuleAPI.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BerthShift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String shift_name;
    private Time shift_from;
    private Time shift_to;
    private String shift_for;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShift_name() {
        return shift_name;
    }

    public void setShift_name(String shift_name) {
        this.shift_name = shift_name;
    }

    public Time getShift_from() {
        return shift_from;
    }

    public void setShift_from(Time shift_from) {
        this.shift_from = shift_from;
    }

    public Time getShift_to() {
        return shift_to;
    }

    public void setShift_to(Time shift_to) {
        this.shift_to = shift_to;
    }

    public String getShift_for() {
        return shift_for;
    }

    public void setShift_for(String shift_for) {
        this.shift_for = shift_for;
    }
}
