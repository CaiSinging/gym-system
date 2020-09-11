package cn.edu.nwafu.cie.se2019.gym.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TestOrder")
public class TestOrder {//体测预约表

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;//预约表编号
    private Long gid;//客户编号
    private Long tid;//教练编号
    private Date tstart;//预约开始时间

    public TestOrder() {
    }

    public TestOrder(Long gid, Long tid, Date tstart) {
        this.gid = gid;
        this.tid = tid;
        this.tstart = tstart;
    }

    public Long getTno() {
        return tno;
    }

    public void setTno(Long tno) {
        this.tno = tno;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Date getTstart() {
        return tstart;
    }

    public void setTstart(Date tstart) {
        this.tstart = tstart;
    }

}
