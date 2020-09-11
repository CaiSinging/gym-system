package cn.edu.nwafu.cie.se2019.gym.payload.users.admin;

import cn.edu.nwafu.cie.se2019.gym.model.Admin;

public class AdminInformationResponse {
    private Long id;
    private String name, tel, email;

    public AdminInformationResponse() {
    }

    public AdminInformationResponse(Admin admin){
        setId(admin.getAid());
        setName(admin.getAname());
        setTel(admin.getAtel());
        setEmail(admin.getAmail());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
