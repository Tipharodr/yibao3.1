package com.zju.yibao.bean;

import java.util.List;

/**
 * Created by Atlas on 16/2/5.
 */
public class MyOrders {
    /**
     * ordersCarId : 9
     * courseId : 1
     * courseName : 声乐1
     * teacherName : 陈红
     * organizationName : 新东方
     */

    private List<OrdersEntity> orders;

    public List<OrdersEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersEntity> orders) {
        this.orders = orders;
    }

    public static class OrdersEntity {
        private int ordersCarId;
        private int courseId;
        private String courseName;
        private String teacherName;
        private String organizationName;

        public int getOrdersCarId() {
            return ordersCarId;
        }

        public void setOrdersCarId(int ordersCarId) {
            this.ordersCarId = ordersCarId;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }
    }

    /**
     * ordersCarId  : 23123123
     * studentId  : 34534534
     * courseName  : 声乐课程
     * teacherName  : 王老师
     * teacherAge  : 23
     * organizationName  : 科瑞教育
     * organizationAddr  : 浙江省宁波市
     * education  : 硕士
     * time  : 2016-02-05 15:22:20
     * count  : 2
     * totalPrice  : 300
     */


}