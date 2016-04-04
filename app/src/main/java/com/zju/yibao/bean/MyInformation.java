package com.zju.yibao.bean;

/**
 * Created by Atlas on 16/3/1.
 */
public class MyInformation {

    /**
     * studentId : 1
     * studentName : hardor
     * email : 123@126.com
     * headPortraits : null
     * studentAge : 22
     * phone : 1872882
     * preference : 下棋
     */

    private int studentId;
    private String studentName;
    private String email;
    private Object headPortraits;
    private int studentAge;
    private String phone;
    private String preference;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getHeadPortraits() {
        return headPortraits;
    }

    public void setHeadPortraits(Object headPortraits) {
        this.headPortraits = headPortraits;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
