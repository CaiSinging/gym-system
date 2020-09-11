package cn.edu.nwafu.cie.se2019.gym.payload.users.guest;

import cn.edu.nwafu.cie.se2019.gym.model.Guest;

import java.util.Date;

public class GuestInformationResponse {
    private Long id;
    private Boolean vip;
    private String name, gender, tel, email;
    private Date vreg, vfin;

    public GuestInformationResponse() {
    }

    public GuestInformationResponse(Guest guest) {
        setId(guest.getGid());
        setName(guest.getGname());
        setEmail(guest.getGmail());
        setGender(guest.getGgender());
        setTel(guest.getGtel());
        setVip(guest.isVIP());
        setVreg(guest.getVreg());
        setVfin(guest.getVfin());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
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
