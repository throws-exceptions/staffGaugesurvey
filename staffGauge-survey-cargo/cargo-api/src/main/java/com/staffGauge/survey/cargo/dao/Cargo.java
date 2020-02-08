package com.staffGauge.survey.cargo.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
public class Cargo implements Serializable {
    private static final long serialVersionUID = 8206656272105974746L;
    @Id
    @Column(name = "cargo_id")
    private Integer cargoId;

    @Column(name = "freighters_num")
    private String freightersNum;

    @Column(name = "start_weight")
    private Float startWeight;

    @Column(name = "end_weight")
    private Float endWeight;

    @Column(name = "goods_weight")
    private Float goodsWeight;

    private String person;

    private Date time;

    /**
     * @return cargo_id
     */
    public Integer getCargoId() {
        return cargoId;
    }

    /**
     * @param cargoId
     */
    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    /**
     * @return freighters_num
     */
    public String getFreightersNum() {
        return freightersNum;
    }

    /**
     * @param freightersNum
     */
    public void setFreightersNum(String freightersNum) {
        this.freightersNum = freightersNum;
    }

    /**
     * @return start_weight
     */
    public Float getStartWeight() {
        return startWeight;
    }

    /**
     * @param startWeight
     */
    public void setStartWeight(Float startWeight) {
        this.startWeight = startWeight;
    }

    /**
     * @return end_weight
     */
    public Float getEndWeight() {
        return endWeight;
    }

    /**
     * @param endWeight
     */
    public void setEndWeight(Float endWeight) {
        this.endWeight = endWeight;
    }

    /**
     * @return goods_weight
     */
    public Float getGoodsWeight() {
        return goodsWeight;
    }

    /**
     * @param goodsWeight
     */
    public void setGoodsWeight(Float goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    /**
     * @return person
     */
    public String getPerson() {
        return person;
    }

    /**
     * @param person
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }
}