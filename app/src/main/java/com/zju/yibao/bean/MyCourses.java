package com.zju.yibao.bean;

import java.util.List;

/**
 * Created by Atlas on 16/2/1.
 */
public class MyCourses {

    private List<MyCourse> myCourses;

    public List<MyCourse> getMyCourses() {
        return myCourses;
    }

    public void setMyCourses(List<MyCourse> myCourses) {
        this.myCourses = myCourses;
    }

    public static class MyCourse {

        /**
         * couStuId : 1
         * courseId : 1
         * courseName : 声乐1
         * mainImage : meiyou
         * teacherName : 陈红
         * teachAddress : 宁波
         * organization : 新东方
         */

        private int couStuId;
        private int courseId;
        private String courseName;
        private String mainImage;
        private String teacherName;
        private String teachAddress;
        private String organization;

        public void setCouStuId(int couStuId) {
            this.couStuId = couStuId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setTeachAddress(String teachAddress) {
            this.teachAddress = teachAddress;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public int getCouStuId() {
            return couStuId;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public String getMainImage() {
            return mainImage;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public String getTeachAddress() {
            return teachAddress;
        }

        public String getOrganization() {
            return organization;
        }
    }

}
