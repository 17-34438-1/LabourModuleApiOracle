package com.datasoft.LaborModuleAPI.Service;

import com.datasoft.LaborModuleAPI.Model.OracleDb;
import com.datasoft.LaborModuleAPI.Repository.OracleDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OracleDbServices {

    @Autowired
    private OracleDbRepository order_repo;

    @Autowired
    @Qualifier("jdbcTemplateOracle")
    private JdbcTemplate OracleDbTemplate;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    public List OracleDbList() throws SQLException {

        String sqlContInfo = "Select id,gkey from inv_unit fetch first 100 rows only";

        List OracleList = OracleDbTemplate.query(sqlContInfo, new OracleDbList());
        List listAll = (List) OracleList.stream().collect(Collectors.toList());

        return listAll;
    }
    class OracleDbList implements RowMapper {

        @Override
        public OracleDb mapRow(ResultSet rs, int rowNum) throws SQLException {
            OracleDb oracleDb = new OracleDb();
            oracleDb.setId(rs.getString("id"));
            oracleDb.setGkey(rs.getLong("gkey"));
            return oracleDb;
        }
    }

}
