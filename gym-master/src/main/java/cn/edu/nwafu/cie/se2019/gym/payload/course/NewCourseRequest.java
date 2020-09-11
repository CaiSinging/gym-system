package cn.edu.nwafu.cie.se2019.gym.payload.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class NewCourseRequest {
    @NotBlank
    private String cname;
    private long tid;
    private int ccapacity;
    @NotBlank
    private String ctime;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date selectedStartDate, selectedEndDate;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
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

    public Date getSelectedStartDate() {
        return selectedStartDate;
    }

    public void setSelectedStartDate(Date selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
    }

    public Date getSelectedEndDate() {
        return selectedEndDate;
    }

    public void setSelectedEndDate(Date selectedEndDate) {
        this.selectedEndDate = selectedEndDate;
    }
}
