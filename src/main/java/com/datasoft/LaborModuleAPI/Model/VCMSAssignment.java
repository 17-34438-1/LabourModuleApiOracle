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
public class VCMSAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unit_gkey;

    private String cont_no;

    public Long getUnit_gkey() {
        return unit_gkey;
    }

    public void setUnit_gkey(Long unit_gkey) {
        this.unit_gkey = unit_gkey;
    }

    public String getCont_no() {
        return cont_no;
    }

    public void setCont_no(String cont_no) {
        this.cont_no = cont_no;
    }
}
