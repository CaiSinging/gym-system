package cn.edu.nwafu.cie.se2019.gym.model;

import javax.persistence.*;

@Entity
@Table(name = "CourserSelection")

public class CourseSelection {//选课记录表

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;//选课记录编号

    private Long gid;//客户编号

    private Long cid;//课程编号

    public CourseSelection() {
    }

    public CourseSelection(Long gid, Long cid) {
        this.gid = gid;
        this.cid = cid;
    }

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
