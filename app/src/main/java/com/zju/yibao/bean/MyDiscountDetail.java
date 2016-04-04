package com.zju.yibao.bean;

/**
 * Created by Atlas on 16/2/2.
 */
public class MyDiscountDetail {

    /**
     * courseName : 声乐1
     * discountCode : 123456
     * discountUseInfo : 1、购买商品时（除特例商品外），购物券可抵购物券券面显示的现金价值；
     2、原则上每张订单只能使用一张购物券，且不得与其他优惠方式同时使用；
     3、本券不得兑换现金不设找零
     4、退货时，本券价值不予退还现金；
     最后一般都会有最终解释权归XX所有，还应该有使用期限
     */

    private String courseName;
    private String discountCode;
    private String discountUseInfo;

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public void setDiscountUseInfo(String discountUseInfo) {
        this.discountUseInfo = discountUseInfo;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public String getDiscountUseInfo() {
        return discountUseInfo;
    }
}
