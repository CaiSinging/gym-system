package cn.edu.nwafu.cie.se2019.gym.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "LeaveOrder")
public class LeaveOrder {//请假表
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;//请假表序号

    private Long tid;//教练编号

    private Date lstart;//请假开始时间

    private Date lend;//请假结束时间

    private Long aid;//审批人（管理员）编号

    @NotBlank
    @Size(max = 50)
    private String lstate;//审批状态（待审批，批准，不批准）

    public LeaveOrder() {
    }

    public LeaveOrder(Long tid, Date lstart, Date lend) {
        this.tid = tid;
        this.lstart = lstart;
        this.lend = lend;
        this.lstate = "待审批";
        this.aid = (long) -1;
    }

    public Long getLno() {
        return lno;
    }

    public void setLno(Long lno) {
        this.lno = lno;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Date getLstart() {
        return lstart;
    }

    public void setLstart(Date lstart) {
        this.lstart = lstart;
    }

    public Date getLend() {
        return lend;
    }

    public void setLend(Date lend) {
        this.lend = lend;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getLstate() {
        return lstate;
    }

    public void setLstate(String lstate) {
        this.lstate = lstate;
    }
}
