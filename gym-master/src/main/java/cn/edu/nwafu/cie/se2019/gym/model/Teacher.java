package cn.edu.nwafu.cie.se2019.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Teacher", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Ttel"
        }),
        @UniqueConstraint(columnNames = {
                "Tmail"
        }),
})
@JsonIgnoreProperties(
        value = {"tpwd"},
        allowGetters = true
)

// 教练员基本信息类
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;        // 教练编号

    @NotBlank
    @Size(max = 50)
    private String tname;   // 教练姓名

    @NotBlank
    @Size(max = 20)
    private String tgender; // 教练性别

    @NotBlank
    @Size(max = 50)
    private String ttel;    // 教练电话

    @NotBlank
    @Size(max = 100)
    @Email
    private String tmail;   // 教练邮箱

    @NotBlank
    @Size(max = 1024)
    private String tpwd;    // 教练密码

    public Teacher() {
    }

    public Teacher(String tname, String tgender, String ttel, String tmail, String tpwd) {
        this.tname = tname;
        this.tgender = tgender;
        this.ttel = ttel;
        this.tmail = tmail;
        this.tpwd = tpwd;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTgender() {
        return tgender;
    }

    public void setTgender(String tgender) {
        this.tgender = tgender;
    }

    public String getTtel() {
        return ttel;
    }

    public void setTtel(String ttel) {
        this.ttel = ttel;
    }

    public String getTmail() {
        return tmail;
    }

    public void setTmail(String tmail) {
        this.tmail = tmail;
    }

    public String getTpwd() {
        return tpwd;
    }

    public void setTpwd(String tpwd) {
        this.tpwd = tpwd;
    }
}
