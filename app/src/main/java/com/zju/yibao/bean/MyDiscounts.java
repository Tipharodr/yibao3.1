package com.zju.yibao.bean;

import java.util.List;

/**
 * Created by Atlas on 16/2/2.
 */
public class MyDiscounts {

    /**
     * discountId : 1
     * discountCode : 123456
     * courseId : 1
     * courseName : 声乐1
     * mainImage : meiyou
     * teacherName : 陈红
     * organizationName : 新东方
     */

    private List<MyDiscountsEntity> myDiscounts;

    public void setMyDiscounts(List<MyDiscountsEntity> myDiscounts) {
        this.myDiscounts = myDiscounts;
    }

    public List<MyDiscountsEntity> getMyDiscounts() {
        return myDiscounts;
    }

    public static class MyDiscountsEntity {
        private int discountId;
        private String discountCode;
        private int courseId;
        private String courseName;
        private String mainImage;
        private String teacherName;
        private String organizationName;

        public void setDiscountId(int discountId) {
            this.discountId = discountId;
        }

        public void setDiscountCode(String discountCode) {
            this.discountCode = discountCode;
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

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public int getDiscountId() {
            return discountId;
        }

        public String getDiscountCode() {
            return discountCode;
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

        public String getOrganizationName() {
            return organizationName;
        }
    }
}
