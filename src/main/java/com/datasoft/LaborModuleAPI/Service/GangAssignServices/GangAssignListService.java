package com.datasoft.LaborModuleAPI.Service.GangAssignServices;

import com.datasoft.LaborModuleAPI.Model.GangAssign;
import com.datasoft.LaborModuleAPI.Repository.GangAssignRepositories.GangAssignmentListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GangAssignListService {
    @Autowired
    private GangAssignmentListRepository gang_assign_list_repo;

    List<GangAssign> gangAssignments;

    public List<GangAssign> gangAssignmentList(){
        gangAssignments =new ArrayList<>();
        List assignmentList[] = gang_assign_list_repo.gangAssignmentList();
        for (int i = 0; i < assignmentList.length; i++) {
            GangAssign assignments = new GangAssign();

            String bl = "";
            String rotation = "";
            String crane = "";
            String shed = "";
            String yard = "";


            String assignment_id = assignmentList[i].get(0).toString();
            String gang_id = assignmentList[i].get(1).toString();
            String work_location_id = assignmentList[i].get(2).toString();
            String work_category_id = assignmentList[i].get(26).toString();
            String berth_operator_id = assignmentList[i].get(22).toString();
            String shift = assignmentList[i].get(4).toString();
            String shift_name = assignmentList[i].get(28).toString();
            String start_time = assignmentList[i].get(14).toString();

            if(assignmentList[i].get(5) != null){
                rotation = assignmentList[i].get(5).toString();
            }

            if(assignmentList[i].get(7) != null){
                bl = assignmentList[i].get(7).toString();
            }

            if(assignmentList[i].get(8) != null){
                crane = assignmentList[i].get(8).toString();
            }

            if(assignmentList[i].get(9) != null){
                shed = assignmentList[i].get(9).toString();
            }

            if(assignmentList[i].get(10) != null){
                yard = assignmentList[i].get(10).toString();
            }

            if(assignmentList[i].get(14) != null){
                start_time = assignmentList[i].get(14).toString();
            }

            Date end_time = (Date) assignmentList[i].get(15);
            Date actual_end_time = (Date) assignmentList[i].get(16);

            assignments.setId((long) Integer.parseInt(assignment_id));
            assignments.setGang_id((long) Integer.parseInt(gang_id));
            assignments.setWork_location_id((long) Integer.parseInt(work_location_id));
            assignments.setWork_category_id((long) Integer.parseInt(work_category_id));
            assignments.setBerth_operator_id((long) Integer.parseInt(berth_operator_id));
            assignments.setShift(Integer.parseInt(shift));
            assignments.setShift_name(shift_name);
            assignments.setRotation(rotation);
            assignments.setBl(bl);
            assignments.setCrane(crane);
            assignments.setShed(shed);
            assignments.setYard(yard);
            assignments.setStart_time(start_time);
            assignments.setEnd_time(end_time);
            assignments.setActual_end_time(actual_end_time);

            List containers[] = gang_assign_list_repo.getContainerByAssignment(Integer.parseInt(assignment_id));
            String[] x = new String[containers.length];
            for (int j = 0; j < containers.length; j++) {
                String container = containers[j].get(2).toString();
                x[j]=container;
                assignments.setContainer(x);
            }
            gangAssignments.add(assignments);
        }
        return gangAssignments;
    }

    public List<GangAssign> gangAssignmentByOrg(Long berth_id){
        gangAssignments =new ArrayList<>();
        List assignmentList[] = gang_assign_list_repo.gangAssignmentListByOrg(berth_id);
        for (int i = 0; i < assignmentList.length; i++) {
            GangAssign assignments = new GangAssign();

            String bl = "";
            String rotation = "";
            String crane = "";
            String shed = "";
            String yard = "";
            String start_time = "";

            String assignment_id = assignmentList[i].get(0).toString();
            String gang_id = assignmentList[i].get(1).toString();
            String work_location_id = assignmentList[i].get(2).toString();
            String work_category_id = assignmentList[i].get(26).toString();
            String berth_operator_id = assignmentList[i].get(23).toString();
            String shift = assignmentList[i].get(4).toString();
            String shift_name = assignmentList[i].get(28).toString();
            String gang_name = assignmentList[i].get(21).toString();
            String work_location_name = assignmentList[i].get(24).toString();
            String work_category_name = assignmentList[i].get(27).toString();

            if(assignmentList[i].get(5) != null){
                rotation = assignmentList[i].get(5).toString();
            }

            if(assignmentList[i].get(7) != null){
                bl = assignmentList[i].get(7).toString();
            }

            if(assignmentList[i].get(8) != null){
                crane = assignmentList[i].get(8).toString();
            }

            if(assignmentList[i].get(9) != null){
                shed = assignmentList[i].get(9).toString();
            }

            if(assignmentList[i].get(10) != null){
                yard = assignmentList[i].get(10).toString();
            }

            if(assignmentList[i].get(14) != null){
                start_time = assignmentList[i].get(14).toString();
            }

            Date end_time = (Date) assignmentList[i].get(15);
            Date actual_end_time = (Date) assignmentList[i].get(16);

            assignments.setId((long) Integer.parseInt(assignment_id));
            assignments.setGang_id((long) Integer.parseInt(gang_id));
            assignments.setWork_location_id((long) Integer.parseInt(work_location_id));
            assignments.setWork_category_id((long) Integer.parseInt(work_category_id));
            assignments.setBerth_operator_id((long) Integer.parseInt(berth_operator_id));
            assignments.setShift(Integer.parseInt(shift));
            assignments.setShift_name(shift_name);
            assignments.setRotation(rotation);
            assignments.setBl(bl);
            assignments.setCrane(crane);
            assignments.setShed(shed);
            assignments.setYard(yard);
            assignments.setStart_time(start_time);
            assignments.setEnd_time(end_time);
            assignments.setGang_name(gang_name);
            assignments.setWork_location_name(work_location_name);
            assignments.setWork_category_name(work_category_name);
            assignments.setStart_time(start_time);
            assignments.setActual_end_time(actual_end_time);

            List containers[] = gang_assign_list_repo.getContainerByAssignment(Integer.parseInt(assignment_id));
            String[] x = new String[containers.length];
            for (int j = 0; j < containers.length; j++) {
                String container = containers[j].get(2).toString();
                x[j]=container;
                assignments.setContainer(x);
            }
            gangAssignments.add(assignments);
        }
        return gangAssignments;
    }

    public ResponseEntity<GangAssign> gangAssignmentById(Long id){
        gangAssignments =new ArrayList<>();
        GangAssign assignments = new GangAssign();
        List assignmentList[] = gang_assign_list_repo.gangAssignmentById(id);
        for (int i = 0; i < assignmentList.length; i++) {
            String bl = "";
            String rotation = "";
            String shed = "";
            String yard = "";
            String crane = "";
            String start_time = "";

            String assignment_id = assignmentList[i].get(0).toString();
            String gang_id = assignmentList[i].get(1).toString();
            String work_location_id = assignmentList[i].get(2).toString();
            String work_category_id = assignmentList[i].get(26).toString();
            String berth_operator_id = assignmentList[i].get(22).toString();
            String shift = assignmentList[i].get(4).toString();
            String shift_name = assignmentList[i].get(28).toString();

            if(assignmentList[i].get(5) != null){
                rotation = assignmentList[i].get(5).toString();
            }

            if(assignmentList[i].get(7) != null){
                bl = assignmentList[i].get(7).toString();
            }

            if(assignmentList[i].get(8) != null){
                crane = assignmentList[i].get(8).toString();
            }

            if(assignmentList[i].get(9) != null){
                shed = assignmentList[i].get(9).toString();
            }

            if(assignmentList[i].get(10) != null){
                yard = assignmentList[i].get(10).toString();
            }

            if(assignmentList[i].get(14) != null){
                start_time = assignmentList[i].get(14).toString();
            }

            Date end_time = (Date) assignmentList[i].get(15);
            Date actual_end_time = (Date) assignmentList[i].get(16);

            assignments.setId((long) Integer.parseInt(assignment_id));
            assignments.setGang_id((long) Integer.parseInt(gang_id));
            assignments.setWork_location_id((long) Integer.parseInt(work_location_id));
            assignments.setWork_category_id((long) Integer.parseInt(work_category_id));
            assignments.setBerth_operator_id((long) Integer.parseInt(berth_operator_id));
            assignments.setShift(Integer.parseInt(shift));
            assignments.setShift_name(shift_name);
            assignments.setRotation(rotation);
            assignments.setBl(bl);
            assignments.setCrane(crane);
            assignments.setShed(shed);
            assignments.setYard(yard);
            assignments.setStart_time(start_time);
            assignments.setEnd_time(end_time);
            assignments.setActual_end_time(actual_end_time);

            List containers[] = gang_assign_list_repo.getContainerByAssignment(Integer.parseInt(assignment_id));
            String[] x = new String[containers.length];
            for (int j = 0; j < containers.length; j++) {
                String container = containers[j].get(2).toString();
                x[j]=container;
                assignments.setContainer(x);
            }
        }
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    public List<GangAssign> runningAssignmentListByGang(Long gang_id){ return gang_assign_list_repo.runningAssignmentListByGang(gang_id); }

}
