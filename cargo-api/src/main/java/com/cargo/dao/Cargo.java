package com.cargo.dao;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(schema = "tb_user")
public class Cargo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -5569524542070784512L;
    @Column(name = "cargo_id")
    private int cargoId;
    @Column(name = "freighters_num")
    private String freightersNum;
    @Column(name = "start_weight")
    private float startWeight;
    @Column(name = "end_weight")
    private float endWeight;
    @Column(name = "goods_weight")
    private float goodsWeight;
    @Column(name = "person")
    private String person;

    private String time;

    public int getCargoId() {
        return cargoId;
    }

    public void setId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getFreightersNum() {
        return freightersNum;
    }

    public void setFreightersNum(String freightersNum) {
        this.freightersNum = freightersNum;
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        this.startWeight = startWeight;
    }

    public float getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(float endWeight) {
        this.endWeight = endWeight;
    }

    public float getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(float goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
