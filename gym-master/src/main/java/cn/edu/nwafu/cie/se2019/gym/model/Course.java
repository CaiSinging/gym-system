package cn.edu.nwafu.cie.se2019.gym.model;

import cn.edu.nwafu.cie.se2019.gym.repository.CourseSelectionRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Course")
// 课程基本信息类
public class Course {
    @JsonIgnore
    @Transient
    private static CourseSelectionRepository courseSelectionRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;       // 课程编号

    @NotBlank
    @Size(max = 50)
    private String cname;  // 课程名

    private Long tid;       // 教练编号

    private int ccapacity;//选课人数上限

    @NotBlank
    @Size(max = 100)
    private String ctime; //当天上课时间(几点-几点）

    private Date cstart;   // 开课时间

    private Date cfinish;  // 结课时间

    public Course() {
    }

    public Course(String cname, Long tid, int ccapacity, String ctime, Date cstart, Date cfinish) {
        this.cname = cname;
        this.tid = tid;
        this.ccapacity = ccapacity;
        this.ctime = ctime;
        this.cstart = cstart;
        this.cfinish = cfinish;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public int getCcapacity() {
        return ccapacity;
    }

    public void setCcapacity(int ccapacity) {
        this.ccapacity = ccapacity;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Date getCstart() {
        return cstart;
    }

    public void setCstart(Date cstart) {
        this.cstart = cstart;
    }

    public Date getCfinish() {
        return cfinish;
    }

    public void setCfinish(Date cfinish) {
        this.cfinish = cfinish;
    }

}
