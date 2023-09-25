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
public class LaborAssignToGang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long gang_id;
    private Long labor_id;

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
    private String labor_name;

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

    public Long getLabor_id() {
        return labor_id;
    }

    public void setLabor_id(Long labor_id) {
        this.labor_id = labor_id;
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

    public String getGang_name() {
        return gang_name;
    }

    public void setGang_name(String gang_name) {
        this.gang_name = gang_name;
    }

    public String getLabor_name() { return labor_name;}

    public void setLabor_name(String labor_name) {this.labor_name = labor_name;}
}
