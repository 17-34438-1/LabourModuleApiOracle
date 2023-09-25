package com.datasoft.LaborModuleAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LCLAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String import_rotation_no;


//    private String rot;
//    private ArrayList<LCLRotation> lclRotations;

    private String bl_no;
    private String cont_no;
    private String assignment_date;
    private String cont_loc_shed;



//    public String getRot() {
//        return rot;
//    }
//
//    public void setRot(String rot) {
//        this.rot = rot;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImport_rotation_no() {
        return import_rotation_no;
    }

    public void setImport_rotation_no(String import_rotation_no) {
        this.import_rotation_no = import_rotation_no;
    }

    public String getBl_no() {
        return bl_no;
    }

    public void setBl_no(String bl_no) {
        this.bl_no = bl_no;
    }

    public String getCont_no() {
        return cont_no;
    }

    public void setCont_no(String cont_no) {
        this.cont_no = cont_no;
    }

    public String getAssignment_date() {
        return assignment_date;
    }

    public void setAssignment_date(String assignment_date) {
        this.assignment_date = assignment_date;
    }

    public String getCont_loc_shed() {
        return cont_loc_shed;
    }

    public void setCont_loc_shed(String cont_loc_shed) {
        this.cont_loc_shed = cont_loc_shed;
    }
}
