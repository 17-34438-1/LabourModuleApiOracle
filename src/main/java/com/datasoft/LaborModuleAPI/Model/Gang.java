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
public class Gang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer size;

    private Long work_category_id;
    private String work_category_name;
    private Long berth_operator_id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getWork_category_id() { return work_category_id; }

    public void setWork_category_id(Long work_category_id) { this.work_category_id = work_category_id; }

    public String getWork_category_name() { return work_category_name; }

    public void setWork_category_name(String work_category_name) {
        this.work_category_name = work_category_name;
    }

    public Long getBerth_operator_id() {
        return berth_operator_id;
    }

    public void setBerth_operator_id(Long berth_operator_id) {
        this.berth_operator_id = berth_operator_id;
    }

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
}
