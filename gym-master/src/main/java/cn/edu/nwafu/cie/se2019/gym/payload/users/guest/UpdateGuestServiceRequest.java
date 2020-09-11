package cn.edu.nwafu.cie.se2019.gym.payload.users.guest;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UpdateGuestServiceRequest {
    private boolean isVip;
    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    private Date vreg, vfin;

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public Date getVreg() {
        return vreg;
    }

    public void setVreg(Date vreg) {
        this.vreg = vreg;
    }

    public Date getVfin() {
        return vfin;
    }

    public void setVfin(Date vfin) {
        this.vfin = vfin;
    }
}
