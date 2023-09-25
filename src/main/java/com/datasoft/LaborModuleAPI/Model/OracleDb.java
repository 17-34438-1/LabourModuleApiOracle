package com.datasoft.LaborModuleAPI.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@NoArgsConstructor

@Transactional
@Repository
public class OracleDb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private Long gkey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getGkey() {
        return gkey;
    }

    public void setGkey(Long gkey) {
        this.gkey = gkey;
    }
}
