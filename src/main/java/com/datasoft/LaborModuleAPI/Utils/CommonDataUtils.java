package com.datasoft.LaborModuleAPI.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommonDataUtils {
    String dbUrlCTMSMIS = "jdbc:mysql://10.1.1.21:3306/ctmsmis";
    String userCTMSMIS = "sparcsn4";
    String passCTMSMIS = "sparcsn4";

    String getDbUrlIGM = "jdbc:mysql://192.168.16.42:3306/cchaportdb";
    String userIGM = "user1";
    String passIGM = "user1test";

    String getDbUrlSparcsn4 = "jdbc:oracle:thin:@172.16.10.120:1521:sparcsn4";
    String userSparcsn4 = "navisuser";
    String passSparcsn4 = "admin123";

    private Connection dbcon=null;

    Integer cntValue;
    Integer insertSt;

    public Integer getCountValue(String conName,String query)
    {
        Connection con = null;
        String dburl = "";
        String dbuser = "";
        String dbpass = "";
        if(conName.equals("CTMSMIS"))
        {
            dbuser = this.userCTMSMIS;
            dbpass = this.passCTMSMIS;
            dburl = "jdbc:mysql://10.1.1.21:3306/ctmsmis";
        }
        else if(conName.equals("IGM"))
        {
            dbuser = this.userIGM;
            dbpass = this.passIGM;
            dburl = "jdbc:mysql://172.16.10.21:3306/cchaportdb";
        }

        else if(conName.equals("SPARCSN4"))
        {
            dbuser = this.userSparcsn4;
            dbpass = this.passSparcsn4;
            dburl = "jdbc:oracle:thin:@172.16.10.120:1521:sparcsn4";
        }

        try
        {
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            while (rs.next())
            {
                this.cntValue = rs.getInt("rtnValue");
            }
            con.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return cntValue;
    }

    public Integer DataInsert(String conName,String query)
    {
        Connection con = null;
        String dburl = "";
        String dbuser = "";
        String dbpass = "";
        if(conName.equals("CTMSMIS"))
        {
            dbuser = this.userCTMSMIS;
            dbpass = this.passCTMSMIS;
            dburl = "jdbc:mysql://10.1.1.21:3306/ctmsmis";
        }
        else if(conName.equals("IGM"))
        {
            dbuser = this.userIGM;
            dbpass = this.passIGM;
            dburl = "jdbc:mysql://192.168.16.42:3306/cchaportdb";
        }
        else if(conName.equals("SPARCSN4"))
        {
            dbuser = this.userSparcsn4;
            dbpass = this.passSparcsn4;
            dburl = "jdbc:oracle:thin:@172.16.10.120:1521:sparcsn4";
        }

        try
        {
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
            Statement stmt = con.createStatement();
            insertSt = stmt.executeUpdate(query);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return insertSt;
    }

    Connection getDatbaseConnecton(String conName)
    {
        Connection con = null;
        String dburl = "";
        String dbuser = "";
        String dbpass = "";

        if(conName.equals("CTMSMIS"))
        {
            dbuser = this.userCTMSMIS;
            dbpass = this.passCTMSMIS;
            dburl = this.dbUrlCTMSMIS;
        }
        else if(conName.equals("IGM"))
        {
            dbuser = this.userIGM;
            dbpass = this.passIGM;
            dburl = this.getDbUrlIGM;
        }
        else if(conName.equals("SPARCSN4"))
        {
            dbuser = this.userSparcsn4;
            dbpass = this.passSparcsn4;
            dburl = this.getDbUrlSparcsn4;
        }

        try
        {
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return con;
    }

    public String getMysqlDbDate(String dt)
    {
        String[] strArr = dt.split("-");
        String mnt = "";
        if (strArr[1].equals("jan") || strArr[1].equals("JAN") || strArr[1].equals("Jan")){
            mnt = "01";
        } else if(strArr[1].equals("feb") || strArr[1].equals("FEB") || strArr[1].equals("Feb")){
            mnt = "02";
        } else if(strArr[1].equals("mar") || strArr[1].equals("MAR") || strArr[1].equals("Mar")){
            mnt = "03";
        } else if(strArr[1].equals("apr") || strArr[1].equals("APR") || strArr[1].equals("Apr")){
            mnt = "04";
        } else if(strArr[1].equals("may") || strArr[1].equals("MAY") || strArr[1].equals("May")){
            mnt = "05";
        } else if(strArr[1].equals("jun") || strArr[1].equals("JUN") || strArr[1].equals("Jun")){
            mnt = "06";
        } else if(strArr[1].equals("jul") || strArr[1].equals("JUL") || strArr[1].equals("Jul")){
            mnt = "07";
        } else if(strArr[1].equals("aug") || strArr[1].equals("AUG") || strArr[1].equals("Aug")){
            mnt = "08";
        } else if(strArr[1].equals("sep") || strArr[1].equals("SEP") || strArr[1].equals("Sep")){
            mnt = "09";
        } else if(strArr[1].equals("oct") || strArr[1].equals("OCT") || strArr[1].equals("Oct")){
            mnt = "10";
        } else if(strArr[1].equals("nov") || strArr[1].equals("NOV") || strArr[1].equals("Nov")){
            mnt = "11";
        } else if(strArr[1].equals("dec") || strArr[1].equals("DEC") || strArr[1].equals("Dec")){
            mnt = "12";
        }

        String date = "20"+strArr[2]+"-"+mnt+"-"+strArr[0];
        return date;
    }

    public String getMonth(String month){
        String monthNameFull = "";
        if (month.equals("jan") || month.equals("JAN") || month.equals("Jan")){
            monthNameFull = "January";
        } else if(month.equals("feb") || month.equals("FEB") || month.equals("Feb")){
            monthNameFull = "February";
        } else if(month.equals("mar") || month.equals("MAR") || month.equals("Mar")){
            monthNameFull = "March";
        } else if(month.equals("apr") || month.equals("APR") || month.equals("Apr")){
            monthNameFull = "April";
        } else if(month.equals("may") || month.equals("MAY") || month.equals("May")){
            monthNameFull = "May";
        } else if(month.equals("jun") || month.equals("JUN") || month.equals("Jun")){
            monthNameFull = "June";
        } else if(month.equals("jul") || month.equals("JUL") || month.equals("Jul")){
            monthNameFull = "July";
        } else if(month.equals("aug") || month.equals("AUG") || month.equals("Aug")){
            monthNameFull = "August";
        } else if(month.equals("sep") || month.equals("SEP") || month.equals("Sep")){
            monthNameFull = "September";
        } else if(month.equals("oct") || month.equals("OCT") || month.equals("Oct")){
            monthNameFull = "October";
        } else if(month.equals("nov") || month.equals("NOV") || month.equals("Nov")){
            monthNameFull = "November";
        } else if(month.equals("dec") || month.equals("DEC") || month.equals("Dec")){
            monthNameFull = "December";
        }
        return monthNameFull;
    }
}
