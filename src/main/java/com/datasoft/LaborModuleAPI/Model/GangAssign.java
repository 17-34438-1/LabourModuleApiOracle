package com.datasoft.LaborModuleAPI.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GangAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long gang_id;
    private Long work_location_id;
    private Long work_category_id;
    private Long labor_id;
    private Long berth_operator_id;
    private Integer size;
    private Integer shift;
    private String rotation;
    private String bl;
    private String crane;
    private String shed;
    private String yard;
    private String vsl_name;
    private String vvd_gkey;

    String[] container;
    private String unit_gkey;
    private String ib_vyg;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd")
    private String start_time;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date end_time;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private Date actual_end_time;
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
   private Date actual_end_time;


    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date created_at;

    @JsonIgnore
    private String created_by;

    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date updated_at;

    @JsonIgnore
    private String updated_by;

    private String gang_name;
    private String work_location_name;
    private String labor_name;
    private String work_category_name;
    private String shift_name;

    public String getIb_vyg() {
        return ib_vyg;
    }

    public void setIb_vyg(String ib_vyg) {
        this.ib_vyg = ib_vyg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGang_id() {
        return gang_id;
    }

    public void setGang_id(Long gang_id) {
        this.gang_id = gang_id;
    }

    public Long getWork_location_id() {
        return work_location_id;
    }

    public void setWork_location_id(Long work_location_id) {
        this.work_location_id = work_location_id;
    }

    public Long getWork_category_id() {
        return work_category_id;
    }

    public void setWork_category_id(Long work_category_id) {
        this.work_category_id = work_category_id;
    }

    public Long getLabor_id() {
        return labor_id;
    }

    public void setLabor_id(Long labor_id) {
        this.labor_id = labor_id;
    }

    public Integer getShift() { return shift; }

    public void setShift(Integer shift) { this.shift = shift; }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String[] getContainer() {
        return container;
    }

    public void setContainer(String[] container) {
        this.container = container;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getCrane() {
        return crane;
    }

    public void setCrane(String crane) {
        this.crane = crane;
    }

    public String getShed() {
        return shed;
    }

    public void setShed(String shed) {
        this.shed = shed;
    }

    public String getYard() { return yard; }

    public void setYard(String yard) { this.yard = yard; }

    public String getVsl_name() {
        return vsl_name;
    }

    public void setVsl_name(String vsl_name) {
        this.vsl_name = vsl_name;
    }

    public String getVvd_gkey() {
        return vvd_gkey;
    }

    public void setVvd_gkey(String vvd_gkey) {
        this.vvd_gkey = vvd_gkey;
    }

    public String getUnit_gkey() {
        return unit_gkey;
    }

    public void setUnit_gkey(String unit_gkey) {
        this.unit_gkey = unit_gkey;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getActual_end_time() { return actual_end_time; }

    public void setActual_end_time(Date actual_end_time) { this.actual_end_time = actual_end_time; }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getGang_name() {
        return gang_name;
    }

    public void setGang_name(String gang_name) {
        this.gang_name = gang_name;
    }

    public String getWork_location_name() {
        return work_location_name;
    }

    public void setWork_location_name(String work_location_name) {
        this.work_location_name = work_location_name;
    }

    public String getLabor_name() {
        return labor_name;
    }

    public void setLabor_name(String labor_name) {
        this.labor_name = labor_name;
    }

    public String getShift_name() { return shift_name; }

    public void setShift_name(String shift_name) { this.shift_name = shift_name; }
}
