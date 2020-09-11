package cn.edu.nwafu.cie.se2019.gym.payload.leaveOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class NewLeaveRequest {
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date selectedStartDate, selectedEndDate;

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
