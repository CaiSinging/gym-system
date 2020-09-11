package cn.edu.nwafu.cie.se2019.gym.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PhysicalTest")
public class PhysicalTest {//体测结果表
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;//体测表编号
    private Long gid;//客户编号
    private Date time;//检测时间
    private Long tid;//教练编号
    private int height;//身高
    private float weight;//体重
    private float fat;//体脂
    private int vitalCapacity;//肺活量
    private float sitAndReach;//坐位体前屈
    private int sitUp;//仰卧起坐
    private float gripPower;//握力

    public PhysicalTest() {
    }

    public PhysicalTest(Long gid, Date time, Long tid, int height, float weight, float fat, int vitalCapacity, float sitAndReach, int sitUp, float gripPower) {
        this.gid = gid;
        this.time = time;
        this.tid = tid;
        this.height = height;
        this.weight = weight;
        this.fat = fat;
        this.vitalCapacity = vitalCapacity;
        this.sitAndReach = sitAndReach;
        this.sitUp = sitUp;
        this.gripPower = gripPower;
    }

    public Long getPno() {
        return pno;
    }

    public void setPno(Long pno) {
        this.pno = pno;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public int getVitalCapacity() {
        return vitalCapacity;
    }

    public void setVitalCapacity(int vitalCapacity) {
        this.vitalCapacity = vitalCapacity;
    }

    public float getSitAndReach() {
        return sitAndReach;
    }

    public void setSitAndReach(float sitAndReach) {
        this.sitAndReach = sitAndReach;
    }

    public int getSitUp() {
        return sitUp;
    }

    public void setSitUp(int sitUp) {
        this.sitUp = sitUp;
    }

    public float getGripPower() {
        return gripPower;
    }

    public void setGripPower(float gripPower) {
        this.gripPower = gripPower;
    }
}
