package cn.edu.nwafu.cie.se2019.gym.payload.users;

import javax.validation.constraints.NotNull;

public class UpdatePasswordRequest {
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
