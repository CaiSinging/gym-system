package cn.edu.nwafu.cie.se2019.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Admin", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Atel"
        }),
        @UniqueConstraint(columnNames = {
                "Amail"
        }),
})
@JsonIgnoreProperties(
        value = {"apwd"},
        allowGetters = true
)

// 管理员基本信息类
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;       // 管理员编号

    @NotBlank
    @Size(max = 50)
    private String aname;  // 管理员姓名

    @NotBlank
    @Size(max = 50)
    private String atel;   // 管理员电话

    @NotBlank
    @Size(max = 100)
    @Email
    private String amail;  // 管理员邮箱

    @NotBlank
    @Size(max = 1024)
    private String apwd;   // 管理员密码

    public Admin() {
    }

    public Admin(String aname, String atel, String amail, String apwd) {
        this.aname = aname;
        this.atel = atel;
        this.amail = amail;
        this.apwd = apwd;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAtel() {
        return atel;
    }

    public void setAtel(String atel) {
        this.atel = atel;
    }

    public String getAmail() {
        return amail;
    }

    public void setAmail(String amail) {
        this.amail = amail;
    }

    public String getApwd() {
        return apwd;
    }

    public void setApwd(String apwd) {
        this.apwd = apwd;
    }
}
