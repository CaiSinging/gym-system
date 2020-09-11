package cn.edu.nwafu.cie.se2019.gym.payload.testOrder;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class NewTestRequest {
    @JsonFormat(pattern = "yyyy/MM/dd HH",timezone = "GMT+8")
    private Date selectedStartDate;

    public Date getSelectedStartDate() {
        return selectedStartDate;
    }

    public void setSelectedStartDate(Date selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
    }
}
