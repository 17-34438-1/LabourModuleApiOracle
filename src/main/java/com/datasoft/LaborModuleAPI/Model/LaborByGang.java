package com.datasoft.LaborModuleAPI.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LaborByGang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long labor_id;
    private String entry_pass_no;
    private String labor_name;

    public Long getLabor_id() {
        return labor_id;
    }

    public void setLabor_id(Long labor_id) {
        this.labor_id = labor_id;
    }

    public String getEntry_pass_no() {
        return entry_pass_no;
    }

    public void setEntry_pass_no(String entry_pass_no) {
        this.entry_pass_no = entry_pass_no;
    }

    public String getLabor_name() {
        return labor_name;
    }

    public void setLabor_name(String labor_name) {
        this.labor_name = labor_name;
    }
}
