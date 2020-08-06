/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartattendancesystem.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import oracle.sql.*;
//import oracle.jdbc.*;
/**
 *
 * @author Rajesh
 */
public class DBOperations {

    //----------------------------------------------------------------------
    //                               Login Related
    //----------------------------------------------------------------------
    public UsermasterBean authenticateUser(String userName, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsermasterBean objBean = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    objBean = new UsermasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setPassword(rs.getString("Password"));
                    objBean.setUserType(rs.getString("User_Type"));
                    objBean.setUserStatus(rs.getString("User_Status"));
                    objBean.setSecurityQuestion(rs.getString("Security_Question"));
                    objBean.setSecurityAnswer(rs.getString("Security_Answer"));
                }
            }
        } catch (Exception e) {
            System.out.println("authenticateUser(String userName, String password) : of DBoperations" + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("authenticateUser(String userName, String password) : of DBoperations" + e);
            }
        }
        return objBean;
    }

    public UsermasterBean getUserDetailByUsername(String userName) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsermasterBean objBean = null;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                objBean = new UsermasterBean();
                objBean.setUserId(rs.getInt("User_ID"));
                objBean.setUsername(rs.getString("Username"));
                objBean.setPassword(rs.getString("Password"));
                objBean.setUserType(rs.getString("User_Type"));
                objBean.setUserStatus(rs.getString("User_Status"));
                objBean.setSecurityQuestion(rs.getString("Security_Question"));
                objBean.setSecurityAnswer(rs.getString("Security_Answer"));
            }
        } catch (Exception e) {
            System.out.println("getUserDetailByUsername(String userName)  of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getUserDetailByUsername(String userName) of DBoperations : " + e);
            }
        }
        return objBean;
    }

    public String getEmailByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select email from usermaster um,userpersonaldetail upd where um.user_id=upd.user_id and um.username=?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getString("email");
            }
        } catch (Exception e) {
            System.out.println("in getEmailByUsername(username) in dboperation" + e);
            return result;
        } finally {
            try {

                rs.close();
                pstmt.close();
                conn.close();

            } catch (Exception e) {
                System.out.println("in getEmailByUsername(username) in dboperation finally" + e);
                return result;
            }
        }
        return result;
    }

    public int addUserActivity(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int userActivityId = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("insert into useractivitymaster ( User_ID ,Login_Time) values(?,?) ");
            pstmt.setInt(1, userId);
            pstmt.setString(2, getCurrentDateTime());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                pstmt = conn.prepareStatement("select max(Activity_ID) from useractivitymaster");
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    userActivityId = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("addUserActivity(int userId) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("addUserActivity(int userId) of DBoperations : " + e);
            }
        }
        return userActivityId;
    }

    public void updateUserActivity(int userActivityId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("update useractivitymaster set Logout_Time = ? where Activity_ID=?");
            pstmt.setString(1, getCurrentDateTime());
            pstmt.setInt(2, userActivityId);

            int i = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("addUserActivity(int userId) of DBoperations : " + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("addUserActivity(int userId) of DBoperations : " + e);
            }
        }
    }

    public ArrayList getAllUserActivityDetailList() {
        Connection conn = null;
        ArrayList alstUserActivity = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select uam.Activity_ID,uam.User_ID,um.Username, uam.Login_Time,uam.Logout_Time  from usermaster um,useractivitymaster uam where um.User_Id=uam.User_Id");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UserActivityMasterBean objBean = new UserActivityMasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setActivityId(rs.getInt("Activity_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setLoginTime(rs.getString("Login_Time"));
                    objBean.setLogoutTime(rs.getString("Logout_Time"));
                    alstUserActivity.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("getAllUserActivityDetailList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserActivityDetailList() of DBoperations : " + e);
            }
        }
        return alstUserActivity;
    }

    public ArrayList getAllUserActivityDetailListByUsername(String username) {
        Connection conn = null;
        ArrayList alstUserActivity = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select uam.Activity_ID,uam.User_ID,um.Username, uam.Login_Time,uam.Logout_Time  from usermaster um,useractivitymaster uam where um.User_Id=uam.User_Id and um.Username=?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UserActivityMasterBean objBean = new UserActivityMasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setActivityId(rs.getInt("Activity_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setLoginTime(rs.getString("Login_Time"));
                    objBean.setLogoutTime(rs.getString("Logout_Time"));
                    alstUserActivity.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("getAllUserActivityDetailListByUsername(String username)  of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserActivityDetailListByUsername(String username)  of DBoperations : " + e);
            }
        }
        return alstUserActivity;
    }
    //-------------------------------------------------------------------------
    //                                 UserAccountDetail Related
    //-------------------------------------------------------------------------

    public ArrayList getAllUserNameList() {
        Connection conn = null;
        ArrayList alstUser = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select Username from usermaster");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                alstUser.add(rs.getString("Username"));
            }
        } catch (Exception e) {
            System.out.println("getAllUserList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserList() of DBoperations : " + e);
            }
        }
        return alstUser;
    }

    public ArrayList getAllUserDetailList() {
        Connection conn = null;
        ArrayList alstUser = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select um.User_ID,Username,Password,User_Type,User_Status,Security_Question,Security_Answer from usermaster um,userpersonaldetail upd where um.User_Id=upd.User_Id");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UsermasterBean objBean = new UsermasterBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setPassword(rs.getString("Password"));
                    objBean.setUserType(rs.getString("User_Type"));
                    objBean.setUserStatus(rs.getString("User_Status"));
                    objBean.setSecurityQuestion(rs.getString("Security_Question"));
                    objBean.setSecurityAnswer(rs.getString("Security_Answer"));
                    alstUser.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("getAllUserDetailList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getAllUserDetailList() of DBoperations : " + e);
            }
        }
        return alstUser;
    }

    public UsermasterBean getUserAccountDetailByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsermasterBean objBean = new UsermasterBean();
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select User_ID,Username,Password,User_Type,User_Status,Security_Question,Security_Answer from usermaster where User_Id = ?");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                {

                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setUsername(rs.getString("Username"));
                    objBean.setPassword(rs.getString("Password"));
                    objBean.setUserType(rs.getString("User_Type"));
                    objBean.setUserStatus(rs.getString("User_Status"));
                    objBean.setSecurityQuestion(rs.getString("Security_Question"));
                    objBean.setSecurityAnswer(rs.getString("Security_Answer"));
                }
            }
        } catch (Exception e) {
            System.out.println("getUserDetailByUserId(int userId) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getUserDetailByUserId(int userId) of DBoperations : " + e);
            }
        }
        return objBean;
    }

    public int getMaxUserId() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxUserID = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select max(User_ID) from usermaster");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maxUserID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getMaxUserId() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getMaxUserId() of DBoperations : " + e);
            }
        }
        return maxUserID;
    }

    public String addUserAccountDetail(UsermasterBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ?");
            pstmt.setString(1, objBean.getUsername());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = "exists";
            } else {

                pstmt = conn.prepareStatement("insert into usermaster ( User_ID ,Username ,Password,User_Type, User_Status ,Security_Question , Security_Answer) values(?,?,?,?,?,?,?) ");
                pstmt.setInt(1, objBean.getUserId());
                pstmt.setString(2, objBean.getUsername());
                pstmt.setString(3, objBean.getPassword());
                pstmt.setString(4, objBean.getUserType());
                pstmt.setString(5, objBean.getUserStatus());
                pstmt.setString(6, objBean.getSecurityQuestion());
                pstmt.setString(7, objBean.getSecurityAnswer());

                System.out.println(pstmt.toString());
                int i = pstmt.executeUpdate();
                if (i > 0) {
                    pstmt = conn.prepareStatement("insert into userpersonaldetail ( User_ID) values(?) ");
                    pstmt.setInt(1, objBean.getUserId());
                    int j = pstmt.executeUpdate();
                    if (j > 0) {
                        result = "added";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("addUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("addUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
            }
        }
        return result;
    }

    public String updateUserAccountDetail(UsermasterBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from usermaster where Username = ? and User_ID !=?");
            pstmt.setString(1, objBean.getUsername());
            pstmt.setInt(2, objBean.getUserId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = "exists";
            } else {
                pstmt = conn.prepareStatement("update usermaster set Username = ?,Password=?,User_Type =?, User_Status = ? ,Security_Question = ?, Security_Answer = ? where User_ID=?");
                pstmt.setString(1, objBean.getUsername());
                pstmt.setString(2, objBean.getPassword());
                pstmt.setString(3, objBean.getUserType());
                pstmt.setString(4, objBean.getUserStatus());
                pstmt.setString(5, objBean.getSecurityQuestion());
                pstmt.setString(6, objBean.getSecurityAnswer());
                pstmt.setInt(7, objBean.getUserId());
                System.out.println(pstmt.toString());
                int i = pstmt.executeUpdate();
                if (i > 0) {
                    result = "updated";
                }
            }
        } catch (Exception e) {
            System.out.println("updateUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("updateUserAccountDetail(UsermasterBean objBean) of DBoperations : " + e);
            }
        }
        return result;
    }

    //---------------------------------------------------------------------------------------
    //        UserPersonalDetail Related
    //-----------------------------------------------------------------------------------------------
    public ArrayList getAllUserPersonalDetailList() {
        Connection conn = null;
        ArrayList alstUser = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select upd.User_ID,Name,Date_Of_Birth,Address,Phone,Mobile,Email from usermaster um,userpersonaldetail upd where um.User_Id=upd.User_Id");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    UserPersonalDetailBean objBean = new UserPersonalDetailBean();
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setName(rs.getString("Name"));
                    objBean.setDateOfBirth(rs.getString("Date_Of_Birth"));
                    objBean.setAddress(rs.getString("Address"));
                    objBean.setPhone(rs.getString("Phone"));
                    objBean.setMobile(rs.getString("Mobile"));
                    objBean.setEmail(rs.getString("Email"));
                    alstUser.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in getAllUserPersonalDetailList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getAllUserPersonalDetailList() finally of DBoperations : " + e);
            }
        }
        return alstUser;
    }

    public UserPersonalDetailBean getUserPersonalDetailByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserPersonalDetailBean objBean = new UserPersonalDetailBean();
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select User_ID,Name,Date_Of_Birth,Address,Phone,Mobile,Email from userpersonaldetail where User_Id=?");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                {
                    objBean.setUserId(rs.getInt("User_ID"));
                    objBean.setName(rs.getString("Name"));
                    objBean.setDateOfBirth(rs.getString("Date_Of_Birth"));
                    objBean.setAddress(rs.getString("Address"));
                    objBean.setPhone(rs.getString("Phone"));
                    objBean.setMobile(rs.getString("Mobile"));
                    objBean.setEmail(rs.getString("Email"));
                }
            }
        } catch (Exception e) {
            System.out.println("getUserPersonalDetailByUserId(int userId) of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getUserPersonalDetailByUserId(int userId) of DBoperations : " + e);
            }
        }
        return objBean;
    }

    public String updateUserPersonalDetail(UserPersonalDetailBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("update userpersonaldetail set Name = ?,Date_Of_Birth=?,Address =?, Phone = ? ,Mobile = ?, Email= ? where User_ID =?");
            pstmt.setString(1, objBean.getName());
            pstmt.setString(2, objBean.getDateOfBirth());
            pstmt.setString(3, objBean.getAddress());
            pstmt.setString(4, objBean.getPhone());
            pstmt.setString(5, objBean.getMobile());
            pstmt.setString(6, objBean.getEmail());
            pstmt.setInt(7, objBean.getUserId());
            System.out.println(pstmt.toString());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                result = "updated";
            }
        } catch (Exception e) {
            System.out.println("updateUserPersonalDetail(UserPersonalDetailBean objBean) of DBoperations : " + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("updateUserPersonalDetail(UserPersonalDetailBean objBean) of DBoperations : " + e);
            }
        }
        return result;
    }

    /*
     * --------------Client Personal Detail Related-------
     */
    public ArrayList getAllStudentDetailList() {
        Connection conn = null;
        ArrayList alstUser = new ArrayList();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select * from studentdetail order by STUDENT_ID");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                {
                    StudentDetailBean objBean = new StudentDetailBean();
                    objBean.setStudId(rs.getInt("STUDENT_ID"));
                    objBean.setName(rs.getString("Name"));
                    objBean.setDateOfBirth(rs.getString("Date_Of_Birth"));
                    objBean.setAddress(rs.getString("Address"));
                    objBean.setPhone(rs.getString("Phone"));
                    objBean.setMobile(rs.getString("Mobile"));
                    objBean.setEmail(rs.getString("Email"));
                    objBean.setGender(rs.getString("gender"));
                    objBean.setCourse(rs.getString("Course"));
                    objBean.setBranch(rs.getString("Branch"));
                    objBean.setEmail(rs.getString("s_year"));
                    objBean.setEmail(rs.getString("Semester"));
                    alstUser.add(objBean);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in getAllStudentDetailList() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in getAllStudentDetailList() finally of DBoperations : " + e);
            }
        }
        return alstUser;
    }

    public int getMaxStudentId() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxClientID = 0;
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("select max(student_ID) from studentdetail");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maxClientID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getMaxStudentId() of DBoperations : " + e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("getMaxStudentId() of DBoperations : " + e);
            }
        }
        return maxClientID;
    }

    public String addStudentDetail(StudentDetailBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("insert into  studentdetail (student_ID,Name,Address, Phone ,Mobile, Email,Gender,Course,Branch,s_year,Semester) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, objBean.getStudId());
            pstmt.setString(2, objBean.getName());
            pstmt.setString(3, objBean.getDateOfBirth());
            pstmt.setString(4, objBean.getAddress());
            pstmt.setString(5, objBean.getPhone());
            pstmt.setString(6, objBean.getMobile());
            pstmt.setString(7, objBean.getEmail());
            pstmt.setString(8, objBean.getGender());
            pstmt.setString(9, objBean.getCourse());
            pstmt.setString(10, objBean.getBranch());
            pstmt.setString(11, objBean.getYear());
            pstmt.setString(12, objBean.getSemester());
            System.out.println(pstmt.toString());
            int i = pstmt.executeUpdate();
            /*if (i > 0) {
                pstmt = conn.prepareStatement("insert into  Student (Client_ID) values (?)");
                pstmt.setInt(1, objBean.getStudId());
                int j = pstmt.executeUpdate();
                if (j > 0) {
                    pstmt = conn.prepareStatement("insert into  clientprofessional (Client_ID) values (?)");
                    pstmt.setInt(1, objBean.getStudId());
                    int k = pstmt.executeUpdate();
                    if (k > 0) {
                        result = "Added";
                    }
                }
            }*/
        } catch (Exception e) {
            System.out.println("Exception in addStudentDetail(StudentDetailBean objBean) of DBoperations : " + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in addStudentDetail(StudentDetailBean objBean) finally of DBoperations : " + e);
            }
        }
        return result;
    }

    public String updateStudentDetail(StudentDetailBean objBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String result = "failed";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement("update studentdetail set Name = ?,Address =?, Phone = ? ,Mobile = ?, email= ? where Client_ID =?");
            pstmt.setString(1, objBean.getName());

            pstmt.setString(2, objBean.getAddress());
            pstmt.setString(3, objBean.getPhone());
            pstmt.setString(4, objBean.getMobile());
            pstmt.setString(5, objBean.getEmail());
            pstmt.setInt(6, objBean.getStudId());
            System.out.println(pstmt.toString());
            int i = pstmt.executeUpdate();
            if (i > 0) {
                result = "updated";
            }
        } catch (Exception e) {
            System.out.println("Exception in updateClientPersonalDetail(ClientPersonalBean objBean) of DBoperations : " + e);
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Exception in updateClientPersonalDetail(ClientPersonalBean objBean) finally of DBoperations : " + e);
            }
        }
        return result;
    }

    //---------------------------------------------------------------------------------------
    //          Common Methods
    //-----------------------------------------------------------------------------------------------
    public String getCurrentDateTime() {
        java.util.Date dd = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        String strDate = sdf.format(dd);
        return strDate;
    }
}
