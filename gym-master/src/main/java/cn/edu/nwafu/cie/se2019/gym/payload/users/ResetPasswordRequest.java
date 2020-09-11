package cn.edu.nwafu.cie.se2019.gym.payload.users;

import javax.validation.constraints.NotNull;

public class ResetPasswordRequest {
    @NotNull
    private String tel;
    @NotNull
    private String email;
    @NotNull
    private String newPassword;

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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
