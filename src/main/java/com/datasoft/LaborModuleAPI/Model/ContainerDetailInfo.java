package com.datasoft.LaborModuleAPI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
//@JsonIgnoreProperties(value = { "id" })
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ContainerDetailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String container_number;
    private String import_Rotation_No;
    private String vessel_name;
    private String voyage_no;
    private String port_of_shipment;
    private Date file_clearence_date;
    private String offdock;
    private String location_description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainer_number() {
        return container_number;
    }

    public void setContainer_number(String container_number) {
        this.container_number = container_number;
    }

    public String getImport_Rotation_No() {
        return import_Rotation_No;
    }

    public void setImport_Rotation_No(String import_Rotation_No) {
        this.import_Rotation_No = import_Rotation_No;
    }

    public String getVessel_name() {
        return vessel_name;
    }

    public void setVessel_name(String vessel_name) {
        this.vessel_name = vessel_name;
    }

    public String getVoyage_no() {
        return voyage_no;
    }

    public void setVoyage_no(String voyage_no) {
        this.voyage_no = voyage_no;
    }

    public String getPort_of_shipment() {
        return port_of_shipment;
    }

    public void setPort_of_shipment(String port_of_shipment) {
        this.port_of_shipment = port_of_shipment;
    }

    public Date getFile_clearence_date() {
        return file_clearence_date;
    }

    public void setFile_clearence_date(Date file_clearence_date) {
        this.file_clearence_date = file_clearence_date;
    }

    public String getOffdock() {
        return offdock;
    }

    public void setOffdock(String offdock) {
        this.offdock = offdock;
    }

    public String getLocation_description() { return location_description; }

    public void setLocation_description(String location_description) { this.location_description = location_description; }
}
