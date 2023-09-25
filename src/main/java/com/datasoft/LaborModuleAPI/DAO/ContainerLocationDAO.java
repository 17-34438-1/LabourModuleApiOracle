package com.datasoft.LaborModuleAPI.DAO;

import com.datasoft.LaborModuleAPI.Model.ContainerDetailInfo;
import com.datasoft.LaborModuleAPI.Repository.ContainerLocationRepositories.ContainerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
public class ContainerLocationDAO {
    @Autowired
    @Qualifier("jdbcTemplatePrimary")
    private JdbcTemplate primaryDBTemplate;

    @Autowired
    @Qualifier("jdbcTemplateSecondary")
    private JdbcTemplate secondaryDBTemplate;

    @Autowired
    private ContainerLocationRepository cont_loc_repo;

    public List getContainerLocation(String cont_number) {
        String sqlContInfo = "SELECT cchaportdb.igm_masters.id,igm_masters.Import_Rotation_No AS import_Rotation_No,\n" +
                "cont_number AS container_number,Vessel_Name AS vessel_name,Voy_No AS voyage_no," +
                "Port_of_Shipment AS port_of_shipment,\n" +
                "igm_masters.file_clearence_date,\n" +
                "(SELECT CONCAT(IFNULL(Organization_Name,' '),' ',IFNULL(Address_1,' '),' ',IFNULL(Address_2,' ')) \n" +
                "FROM cchaportdb.organization_profiles \n" +
                "WHERE cchaportdb.organization_profiles.id=cchaportdb.igm_detail_container.off_dock_id) AS offdock \n" +
                "FROM cchaportdb.igm_detail_container \n" +
                "INNER JOIN cchaportdb.igm_details ON cchaportdb.igm_details.id=cchaportdb.igm_detail_container.igm_detail_id \n" +
                "INNER JOIN cchaportdb.igm_masters ON cchaportdb.igm_masters.id=cchaportdb.igm_details.IGM_id \n" +
                "WHERE cont_number='"+cont_number+"'\n" +
                "ORDER BY file_clearence_date DESC \n" +
                "LIMIT 1";
        List contInfoList = secondaryDBTemplate.query(sqlContInfo, new containerInfoMapper());
        List listAll = (List) contInfoList.stream().collect(Collectors.toList());

        return listAll;
    }

    class containerInfoMapper implements RowMapper {

        @Override
        public ContainerDetailInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            String offdockCode = "";
            String contDest = "";
            String contDestName = "";
            String transit_state = "";
            String time_in = "";
            String freight_kind = "";
            String dsc = "";
            String block = "";
            String evt = "";
            String stripSt = "";
            String location_description = "";
            List[] location;
            List[] evtRslt;
            ContainerDetailInfo cont_location = new ContainerDetailInfo();

            cont_location.setImport_Rotation_No(rs.getString("import_Rotation_No"));
            cont_location.setContainer_number(rs.getString("container_number"));
            cont_location.setVessel_name(rs.getString("vessel_name"));
            cont_location.setVoyage_no(rs.getString("voyage_no"));
            cont_location.setPort_of_shipment(rs.getString("port_of_shipment"));
            cont_location.setFile_clearence_date(rs.getDate("file_clearence_date"));
            cont_location.setOffdock(rs.getString("offdock"));

            String cat = cont_loc_repo.getCategory(rs.getString("container_number"));
            List containerdestination[] = cont_loc_repo.getContDest(rs.getString("container_number"));

            for(int cnt=0;cnt<containerdestination.length;cnt++){
                if(containerdestination[cnt].get(0) != null){
                    contDest = containerdestination[cnt].get(0).toString(); //First Column
                }
                if(containerdestination[cnt].get(1) != null){
                    contDestName = containerdestination[cnt].get(1).toString(); //Second Column
                }
            }

            if(cat.equals("IMPRT")){
                if(cat.equals(offdockCode)){
                    System.out.println("Category......................................." + cat);
                    location = cont_loc_repo.getLocationForIMPRT1(rs.getString("container_number"));
                } else {
                    System.out.println("Category........................................." + cat);
                    location = cont_loc_repo.getLocationForIMPRT2(rs.getString("container_number"));
                }
            } else if(cat.equals("EXPRT")){
                System.out.println("Category...................................." + cat);
                location = cont_loc_repo.getLocationForEXPRT(rs.getString("container_number"));
            } else{
                System.out.println("Category................................." + cat);
                location = cont_loc_repo.getLocation(rs.getString("container_number"));
            }

            for(int cnt=0;cnt<location.length;cnt++){
                if(location[cnt].get(3) != null){
                    freight_kind = location[cnt].get(3).toString();
                }

                if(location[cnt].get(4) != null){
                    dsc = location[cnt].get(4).toString();
                }

                if(location[cnt].get(5) != null) {
                    block = location[cnt].get(5).toString();
                }
            }

//            System.out.println("transit_state " + transit_state);
//            System.out.println("time_in " + time_in);
//            System.out.println("freight_kind " + freight_kind);
//            System.out.println("dsc " + dsc);
//            System.out.println("block " + block);

            if(freight_kind.equals("LCL")){
                evtRslt = cont_loc_repo.getEVT(rs.getString("container_number"));
                for(int c=0;c<evtRslt.length;c++){
                    evt = evtRslt[c].get(0).toString();
                }
            }
            if(evt.equals("1")){
                stripSt = "Stripped";
            }

            if(!cat.equals(null) || !cat.equals("") || !cat.equals(" ")){
                if(!dsc.equals(null) && !block.equals(null) && !block.equals("Y6")){
                    location_description = stripSt + " " + dsc;
                } else if(!dsc.equals(null) && block.equals("Y6")){
                    location_description = stripSt + " " + dsc;
                } else {
                    location_description = stripSt + " " + dsc;
                }
            } else {
                location_description = "";
            }
            location_description = location_description.replaceAll("<br>"," ").replaceAll("<b>"," ").replaceAll("</b>","").trim();
            cont_location.setLocation_description(location_description);

            return cont_location;
        }
    }
}