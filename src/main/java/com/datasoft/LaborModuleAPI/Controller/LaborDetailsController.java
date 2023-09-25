package com.datasoft.LaborModuleAPI.Controller;

import com.datasoft.LaborModuleAPI.Model.ResponseMessage;
import com.datasoft.LaborModuleAPI.Model.Labor;
import com.datasoft.LaborModuleAPI.Service.ExternalAPIService;
import com.datasoft.LaborModuleAPI.Service.LaborServices.LaborDetailsDeleteService;
import com.datasoft.LaborModuleAPI.Service.LaborServices.LaborDetailsEditService;
import com.datasoft.LaborModuleAPI.Service.LaborServices.LaborDetailsInsertService;
import com.datasoft.LaborModuleAPI.Service.LaborServices.LaborDetailsListService;
import com.datasoft.LaborModuleAPI.Utils.CommonDataUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/labor")
public class LaborDetailsController {
    @Autowired
    private LaborDetailsInsertService laborInsertService;

    @Autowired
    private LaborDetailsListService laborListService;

    @Autowired
    private LaborDetailsEditService laborEditService;

    @Autowired
    private LaborDetailsDeleteService LaborDeleteService;

    @Autowired
    private ExternalAPIService externalAPIService;


    ResponseMessage responseMessage;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ResponseMessage> index(@RequestBody Labor laborDetails) throws IOException{
        String name = laborDetails.getName();
        Long labor_type_id = laborDetails.getLabor_type_id();
        Long designation_category_id = laborDetails.getDesignation_category_id();
        Long berth_operator_id = laborDetails.getBerth_operator_id();
        Integer org_id = laborDetails.getOrg_id();
        String nid = laborDetails.getNid();
        String phone = laborDetails.getPhone();
        String entry_pass_no = laborDetails.getEntry_pass_no();
        Date expiration_date = laborDetails.getExpiration_date();
        String photo = laborDetails.getPhoto();
        String present_address = laborDetails.getPresent_address();
        String permanent_address = laborDetails.getPermanent_address();
        Date date_of_birth = laborDetails.getDate_of_birth();
        Date date_of_joining = laborDetails.getDate_of_joining();
        System.out.println("................" + org_id + ".....................");
        if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            responseMessage = new ResponseMessage( "Sorry! Work Labor Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>100){
            responseMessage = new ResponseMessage( "Sorry! Maximum Labor Name length is 100.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(labor_type_id == null || labor_type_id.equals("") || labor_type_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Labor Type ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(designation_category_id == null || designation_category_id.equals("") || designation_category_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Labor Designation ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(berth_operator_id == null || berth_operator_id.equals("") || berth_operator_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Berth Operator can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(org_id == null || org_id.equals("") || org_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Organization can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(nid == null || nid.equals("") || nid.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! NID number can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }

       /* else if(phone.length()>11 || phone.length()<11){
            responseMessage = new ResponseMessage( "Sorry! Maximum Contact Number length is 11.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }*/
        else if(entry_pass_no == null || entry_pass_no.equals("") || entry_pass_no.equals(" ") || entry_pass_no.isEmpty()){
            responseMessage = new ResponseMessage( "Sorry! Entry Pass Number can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            Boolean isExist = laborInsertService.isExist(nid,phone,entry_pass_no);
            Boolean laborCategoryExistence = laborInsertService.laborTypeExistence(labor_type_id);
            Boolean laborDesignationExistence = laborInsertService.laborDesignationExistence(designation_category_id);
            if(isExist){
                responseMessage = new ResponseMessage( "Sorry! Labor Information already exists.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            } else if(!laborCategoryExistence){
                responseMessage = new ResponseMessage( "Sorry! Invalid Labor Type.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            } else if(!laborDesignationExistence){
                responseMessage = new ResponseMessage( "Sorry! Invalid Designation Category ID.");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
            else {
                Boolean responseStatus = laborInsertService.laborDetailsInfoInsert(name,labor_type_id,designation_category_id,berth_operator_id,org_id,nid,phone,entry_pass_no,expiration_date,photo,present_address,permanent_address,date_of_birth,date_of_joining);
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Labor Info Added Successfully");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Add Labor Info.");
                    return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                }
            }
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Labor> laborList(){
        List<Labor> laborList = new ArrayList<Labor>();
        String exception = null;
        try
        {
            laborList = laborListService.laborList();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return laborList ;
    }

    @RequestMapping(value = "/listByOrg/{org_id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Labor> orgWiseLaborList(@PathVariable("org_id") Integer org_id){
        List<Labor> laborListByOrg = new ArrayList<Labor>();
        String exception = null;
        try
        {
            laborListByOrg = laborListService.laborListByOrg(org_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return laborListByOrg ;
    }

    @RequestMapping(value = "/availableLaborListByTypeAndDesignation/{labor_type_id}/{designation_category_id}", method = RequestMethod.GET)
    public @ResponseBody List<Labor> laborInfoByTypeAndDesignation(@PathVariable("labor_type_id") Long labor_type_id,@PathVariable("designation_category_id") Long designation_category_id){
        List<Labor> laborInfoByTypeAndDesignation = new ArrayList<Labor>();
        String exception = null;
        try
        {
            laborInfoByTypeAndDesignation = laborListService.getLaborInfoByTypeAndDesignation(labor_type_id,designation_category_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return laborInfoByTypeAndDesignation ;
    }

    @RequestMapping(value = "/availableLaborList/{labor_type_id}/{designation_category_id}/{berth_operator_id}/{gang_id}", method = RequestMethod.GET)
    public @ResponseBody List<Labor> availableLaborList(@PathVariable("labor_type_id") Long labor_type_id,
                                                        @PathVariable("designation_category_id") Long designation_category_id,
                                                        @PathVariable("berth_operator_id") Long berth_operator_id,
                                                        @PathVariable("gang_id") Integer gang_id){
        List<Labor> availableLaborList = new ArrayList<Labor>();
        String exception = null;
        try
        {
            availableLaborList = laborListService.availableLaborList(labor_type_id,designation_category_id,berth_operator_id,gang_id);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            exception = ex.getMessage();
        }
        return availableLaborList ;
    }

    @RequestMapping(value = "/laborById/{id}", method = RequestMethod.GET)
    public ResponseEntity<Labor> laborById(@PathVariable("id") Long id){
        Labor laborById = laborListService.laborInfoById(id);
        return new ResponseEntity<>(laborById,HttpStatus.OK);
    }

    @RequestMapping(value = "/getLaborByEntryPassNo/{entry_pass_no}/{org_id}", method = RequestMethod.GET)
    public @ResponseBody Object index(@PathVariable("entry_pass_no") String entry_pass_no, @PathVariable("org_id") Integer org_id){
        Labor laborInfo = new Labor();
        List<Object> laborCount = new ArrayList<Object>();
        ResponseEntity<ResponseMessage> responseMsg ;

        CommonDataUtils objCommonDataUtils = new CommonDataUtils();

        String exception = null;

        laborCount = laborListService.laborCount(entry_pass_no);

        String cardNumber = "";
        String validity = "";
        String date_of_birth = "";
        String nid = "";
        String agency_name = "";
        String agent_name = "";
        String agent_type_name = "";
        String mobile_no = "";
        String agency_code = "";
        String jsl_no = "";
        String present_address = "";
        String permanent_address = "";
        String photobase64 = "";
        String biometricData = externalAPIService.consumeBiometricAPI(entry_pass_no);

        if(laborCount.size() == 0){

            if(biometricData == null){
                responseMessage = new ResponseMessage( "Sorry! Invalid Card Number");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            } else {
                try {
                    //System.out.println("API");
                    JSONObject jsonObject = new JSONObject(biometricData);

                    cardNumber = (String) jsonObject.get("CARDNUMBER");
                    nid = (String) jsonObject.get("NATIONALID");
                    agency_name = (String) jsonObject.get("AGENCY_NAME");
                    agent_name = (String) jsonObject.get("AGENT_NAME");
                    agent_type_name = (String) jsonObject.get("AGENT_TYPE_NAME");
                    mobile_no = (String) jsonObject.get("MOBILE");
                    agency_code = (String) jsonObject.get("AGENT_CODE");
                    jsl_no = (String) jsonObject.get("JSLNO");
                    validity = (String) jsonObject.get("VALID_TILL");
                    date_of_birth = (String) jsonObject.get("DOB");
                    permanent_address = (String) jsonObject.get("PER_ADDRESS");
                    present_address = (String) jsonObject.get("LOCAL_ADDRESS");
                    photobase64 = (String) jsonObject.get("photobase64");

                    String sqlCountOrg = "SELECT COUNT(*) as rtnValue FROM organization_profiles WHERE Organization_Name='"+agency_name+"'";
                    Integer cntOrg = objCommonDataUtils.getCountValue("IGM",sqlCountOrg);
                    if(cntOrg.equals(0) || cntOrg.equals("0") || cntOrg==0){
                        responseMessage = new ResponseMessage( "Sorry! No Organization Found.");
                        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                    } else {
                        String sqlGetOrgId = "SELECT id as rtnValue FROM organization_profiles WHERE Organization_Name='"+agency_name+"'";
                        Integer igmOrgId = objCommonDataUtils.getCountValue("IGM",sqlGetOrgId);

                        if(igmOrgId.equals(org_id) || igmOrgId == org_id){

                            //...Converting & Writing Image Starts.........
                            String[] strings = photobase64.split(",");
                            String extension;
                            switch (strings[0]) {//check image's extension
                                case "data:image/jpeg;base64":
                                    extension = "jpeg";
                                    break;
                                case "data:image/png;base64":
                                    extension = "png";
                                    break;
                                default:                            //should write cases for more image types if found
                                    extension = "jpg";
                                    break;
                            }
                            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
                            //new File("C:\\SpringBootProjects\\Images").mkdir();
                            //String innerFolder = "C:\\SpringBootProjects\\Images\\"+entry_pass_no;
                            String innerFolder = "\\\\192.168.16.42\\CTMS_Dev_SMB\\biometricPhoto\\"+entry_pass_no;
                            new File(innerFolder).mkdir();
                            String path = innerFolder + "\\" + entry_pass_no + "." + extension;
                            File file = new File(path);
                            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                                outputStream.write(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //...Converting & Writing Image Ends...........

                            String[] splitDateArr = date_of_birth.split("-");

                            String month = objCommonDataUtils.getMonth(splitDateArr[1]);

                            String string = month+" "+ splitDateArr[0]+", "+ splitDateArr[2];//"January 2, 2010";

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
                            LocalDate dob = LocalDate.parse(string, formatter).plusDays(1);

                            ZoneId defaultZoneId = ZoneId.systemDefault();
                            Date dateBirth = Date.from(dob.atStartOfDay(defaultZoneId).toInstant());
                            //System.out.println(string); // 2010-01-02
                            validity = objCommonDataUtils.getMysqlDbDate(validity);

                            String sqlCntAgency = "SELECT COUNT(*) as rtnValue FROM vcms_vehicle_agency WHERE agency_name='"+agency_name+"'";
                            Integer cntAgency = objCommonDataUtils.getCountValue("IGM",sqlCntAgency);
                            if(cntAgency.equals(0) || cntAgency == 0){
                                String sqlInsertAgency = "INSERT INTO cchaportdb.vcms_vehicle_agency(agency_name,agency_code) " +
                                        "VALUES('"+agency_name+"','"+agency_code+"')";
                                Integer responseInsertAgency = objCommonDataUtils.DataInsert("IGM",sqlInsertAgency);
                            }

                            String sqlAgencyId = "SELECT id AS rtnValue FROM vcms_vehicle_agency WHERE agency_name='"+agency_name+"'";
                            Integer agency_id = objCommonDataUtils.getCountValue("IGM",sqlAgencyId);
                            String agent_photo = entry_pass_no+"."+extension;

                            String sqlInsert = "INSERT INTO cchaportdb.vcms_vehicle_agent(agency_id,card_number,nid_number,agent_name,agent_type,mobile_number,agent_code,agent_photo,valid_till_dt)\n" +
                                    "VALUES('"+agency_id+"','" +cardNumber+ "','" +nid+ "','" +agent_name+ "','" +agent_type_name+ "','" +mobile_no+ "','" +jsl_no+ "','"+agent_photo+"','" +validity+ "')";
                            Integer responseInsert = objCommonDataUtils.DataInsert("IGM",sqlInsert);

                            laborInfo = laborListService.laborInfo(entry_pass_no);
                            laborInfo.setDate_of_birth(dateBirth);
                            laborInfo.setPermanent_address(permanent_address);
                            laborInfo.setPresent_address(present_address);
                        } else {
                            responseMessage = new ResponseMessage( "Sorry! You are not authorized for this labor.");
                            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
                        }
                    }


                }catch (JSONException | SQLException err){
                    //Log.d("Error", err.toString());
                }
                return laborInfo ;
            }
        } else {
            try {
                Date dateBirth = null;
                if(biometricData == null){
                    dateBirth = null;
                    permanent_address ="";
                    present_address ="";
                } else {
                    JSONObject jsonObject = new JSONObject(biometricData);

                    date_of_birth = (String) jsonObject.get("DOB");
                    permanent_address = (String) jsonObject.get("PER_ADDRESS");
                    present_address = (String) jsonObject.get("LOCAL_ADDRESS");


                    String[] splitDateArr = date_of_birth.split("-");
                    System.out.println(splitDateArr[0]);
                    System.out.println(splitDateArr[1]);
                    System.out.println(splitDateArr[2]);

                    String month = objCommonDataUtils.getMonth(splitDateArr[1]);

                    String string = month+" "+ splitDateArr[0]+", "+ splitDateArr[2];//"January 2, 2010";
                    System.out.println(string);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
                    LocalDate dob = LocalDate.parse(string, formatter).plusDays(1);

                    ZoneId defaultZoneId = ZoneId.systemDefault();
                    dateBirth = Date.from(dob.atStartOfDay(defaultZoneId).toInstant());
                }

                System.out.println(dateBirth);
                laborInfo = laborListService.laborInfo(entry_pass_no);
                laborInfo.setDate_of_birth(dateBirth);
                laborInfo.setPermanent_address(permanent_address);
                laborInfo.setPresent_address(present_address);
            }catch(Exception ex) {
                ex.printStackTrace();
                exception = ex.getMessage();
            }
            return laborInfo ;
        }
    }

    @RequestMapping(value = "/laborAvailabilityByLaborId/{id}", method = RequestMethod.GET)
    public Object laborAvailabilityByLaborId(@PathVariable("id") String laborId) throws IOException {
        Integer laborChk = laborListService.laborChkById(laborId);
        if(laborChk == 0){
            responseMessage = new ResponseMessage("Sorry! Invalid Labor ID.");
            return responseMessage;
        } else {
            Integer laborAssignmentChk = laborListService.laborAssignmentChkById(laborId);
            if(laborAssignmentChk == 0){
                Labor labor_by_id = laborListService.laborDetailsinfoListByLaborId(laborId);
                return labor_by_id;
            } else {
                responseMessage = new ResponseMessage("Sorry! Invalid Labor ID.");
                return responseMessage;
            }
        }
    }

    @RequestMapping(value = "/editLabor", method = RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<ResponseMessage> editLaborDetails(@RequestBody Labor laborDetails) throws IOException{
        Long id = laborDetails.getId();
        String name = laborDetails.getName();
        Long labor_type_id = laborDetails.getLabor_type_id();
        Long designation_category_id = laborDetails.getDesignation_category_id();
        Long berth_operator_id = laborDetails.getBerth_operator_id();
        Integer org_id = laborDetails.getOrg_id();
        String nid = laborDetails.getNid();
        String phone = laborDetails.getPhone();
        String entry_pass_no = laborDetails.getEntry_pass_no();
        Date expiration_date = laborDetails.getExpiration_date();
        String photo = laborDetails.getPhoto();
        String present_address = laborDetails.getPresent_address();
        String permanent_address = laborDetails.getPermanent_address();
        Date date_of_birth = laborDetails.getDate_of_birth();
        Date date_of_joining = laborDetails.getDate_of_joining();

        if(id == null || id.equals("") || id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Labor ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name == null || name.equals("") || name.equals(" ") || name.isEmpty()){
            responseMessage = new ResponseMessage( "Sorry! Work Labor Name can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(name.length()>100){
            responseMessage = new ResponseMessage( "Sorry! Maximum Labor Name length is 100.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(labor_type_id == null || labor_type_id.equals("") || labor_type_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Labor Type ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(designation_category_id == null || designation_category_id.equals("") || designation_category_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Labor Designation ID can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(berth_operator_id == null || berth_operator_id.equals("") || berth_operator_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Berth Operator can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(org_id == null || org_id.equals("") || org_id.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! Organization can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(nid == null || nid.equals("") || nid.equals(" ")){
            responseMessage = new ResponseMessage( "Sorry! NID number can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
      /* else if(phone == null || phone.equals("") || phone.equals(" ") || phone.isEmpty()){
            responseMessage = new ResponseMessage( "Sorry! Phone/Contact Number can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }*/
       /* else if(phone.length()>11 || phone.length()<11){
            responseMessage = new ResponseMessage( "Sorry! Maximum Contact Number length is 11.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }*/
        else if(entry_pass_no == null || entry_pass_no.equals("") || entry_pass_no.equals(" ") || entry_pass_no.isEmpty()){
            responseMessage = new ResponseMessage( "Sorry! Entry Pass Number can not be null or empty.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else if(entry_pass_no.length()>15){
            responseMessage = new ResponseMessage( "Sorry! Maximum Entry Pass Number length is 15.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        }
        else{
            String validityResponse = laborEditService.isValidUpdate(id,nid,phone,entry_pass_no);
            if(validityResponse == "VALID"){
                Boolean responseStatus = laborEditService.laborUpdate(id,name,labor_type_id,designation_category_id,berth_operator_id,org_id,nid,phone,entry_pass_no,expiration_date,photo,present_address,permanent_address,date_of_birth,date_of_joining);
                if(responseStatus){
                    responseMessage = new ResponseMessage( "Updated Labor Information Successfully.");
                    return new ResponseEntity(responseMessage, HttpStatus.OK);
                } else {
                    responseMessage = new ResponseMessage( "Sorry! Could not Update Labor Information.");
                    return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
                }
            } else {
                responseMessage = new ResponseMessage(validityResponse);
                return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
            }


//            Boolean isExist = laborEditService.isExist(nid,phone,entry_pass_no);
//            Boolean laborTypeExistence = laborEditService.laborTypeExistence(labor_type_id);
//            Boolean laborDesignationExistence = laborEditService.laborDesignationExistence(designation_category_id);
//            if(isExist){
//                responseMessage = new ResponseMessage( "Sorry! Labor information already exists.");
//                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//            } else if(!laborTypeExistence){
//                responseMessage = new ResponseMessage( "Sorry! Invalid Labor Type ID.");
//                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//            } else if(!laborDesignationExistence){
//                responseMessage = new ResponseMessage( "Sorry! Invalid Designation Category ID.");
//                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
//            }
//            else {
//                Boolean responseStatus = laborEditService.laborUpdate(id,name,labor_type_id,designation_category_id,berth_operator_id,nid,phone,entry_pass_no,expiration_date,photo,present_address,permanent_address,date_of_birth,date_of_joining);
//                if(responseStatus){
//                    responseMessage = new ResponseMessage( "Updated Labor Information Successfully.");
//                    return new ResponseEntity(responseMessage, HttpStatus.OK);
//                } else {
//                    responseMessage = new ResponseMessage( "Sorry! Could not Update Labor Information.");
//                    return new ResponseEntity(responseMessage, HttpStatus.EXPECTATION_FAILED);
//                }
//            }
        }
    }

//    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//    public JsonResponse deleteAll(){
//        Integer delete_all_labor = LaborDeleteService.deleteAllLabor();
//        if(delete_all_labor >= 1){
//            responseMessage = new JsonResponse(200, "Deleted Labor Information Successfully");
//        } else {
//            responseMessage = new JsonResponse(402, "Sorry! Could not Delete Labor Information");
//        }
//        return responseMessage;
//    }

    @DeleteMapping("/deleteLabor/{id}")
    public ResponseEntity<ResponseMessage> deleteLabor(@PathVariable Long id) throws IOException{
        Boolean chkLaborAssignToGang = LaborDeleteService.chkLaborAssignToGang(id);
        Boolean chkLaborAssignToWorkplace = LaborDeleteService.chkLaborAssignToWorkplace(id);
        if(chkLaborAssignToGang){
            responseMessage = new ResponseMessage( "Sorry! This Work Labor can't be deleted because this person has already been assigned to some gangs.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        } else if (chkLaborAssignToWorkplace){
            responseMessage = new ResponseMessage( "Sorry! This Work Labor can't be deleted because this person has already been assigned to some gangs or workplaces.");
            return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
        } else {
            Boolean resonseStatus = LaborDeleteService.deleteLabor(id);
            if(resonseStatus){
                responseMessage = new ResponseMessage( "Deleted Labor Information Successfully");
                return new ResponseEntity(responseMessage, HttpStatus.OK);
            } else {
                responseMessage = new ResponseMessage( "Sorry! Could not Delete Labor Information");
                return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
            }
        }
    }



    //This method will be called automatically if MySQLIntegrityConstraintViolationException is found...
    @ExceptionHandler(value = MySQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> invalidDataExceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! Invalid Labor Category or Designation Category ID.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }

    //This method will be called automatically if MySQLIntegrityConstraintViolationException is found...
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseMessage> exceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! Valid format for Date of Birth and Date of Joining is 'YYYY-mm-dd'.");
        return new ResponseEntity("", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ResponseMessage> numberFormatExceptionHandler() throws IOException{
        responseMessage = new ResponseMessage( "Sorry! ID must be a number.");
        return new ResponseEntity(responseMessage, HttpStatus.BAD_REQUEST);
    }

}
