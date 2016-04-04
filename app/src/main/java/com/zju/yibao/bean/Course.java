package com.zju.yibao.bean;

import java.util.List;

/**
 * Created by Atlas on 16/1/30.
 */
public class Course {

    /**
     * courseId : 1
     * courseName : 声乐1
     * teacherName : 陈红
     * mainImage : meiyou
     * courseDesc : 声乐1
     * courseStatus : 1002
     * education : 本科
     * seniority : 6
     * description : 很漂亮
     * teacherHeadPortraits : null
     * organizationName : 新东方
     * organizationDescription : 新东方教育
     * organizationImageId : null
     * courseCommentViews : [{"studentName":"hardor","starLevel":"5","comment":"真好","studentHeadPortraits":null},{"studentName":"xiaoli","starLevel":"6","comment":"很棒","studentHeadPortraits":null}]
     */

    private int courseId;
    private String courseName;
    private String teacherName;
    private String mainImage;
    private String courseDesc;
    private int courseStatus;
    private String education;
    private int seniority;
    private String description;
    private Object teacherHeadPortraits;
    private String organizationName;
    private String organizationDescription;
    private Object organizationImageId;
    /**
     * studentName : hardor
     * starLevel : 5
     * comment : 真好
     * studentHeadPortraits : null
     */

    private List<CourseCommentViewsEntity> courseCommentViews;

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public void setCourseStatus(int courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTeacherHeadPortraits(Object teacherHeadPortraits) {
        this.teacherHeadPortraits = teacherHeadPortraits;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public void setOrganizationImageId(Object organizationImageId) {
        this.organizationImageId = organizationImageId;
    }

    public void setCourseCommentViews(List<CourseCommentViewsEntity> courseCommentViews) {
        this.courseCommentViews = courseCommentViews;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getMainImage() {
        return mainImage;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public int getCourseStatus() {
        return courseStatus;
    }

    public String getEducation() {
        return education;
    }

    public int getSeniority() {
        return seniority;
    }

    public String getDescription() {
        return description;
    }

    public Object getTeacherHeadPortraits() {
        return teacherHeadPortraits;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public Object getOrganizationImageId() {
        return organizationImageId;
    }

    public List<CourseCommentViewsEntity> getCourseCommentViews() {
        return courseCommentViews;
    }

    public static class CourseCommentViewsEntity {
        private String studentName;
        private String starLevel;
        private String comment;
        private Object studentHeadPortraits;

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public void setStarLevel(String starLevel) {
            this.starLevel = starLevel;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setStudentHeadPortraits(Object studentHeadPortraits) {
            this.studentHeadPortraits = studentHeadPortraits;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getStarLevel() {
            return starLevel;
        }

        public String getComment() {
            return comment;
        }

        public Object getStudentHeadPortraits() {
            return studentHeadPortraits;
        }
    }
}
