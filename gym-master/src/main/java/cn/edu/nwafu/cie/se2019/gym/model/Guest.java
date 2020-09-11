package cn.edu.nwafu.cie.se2019.gym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Guest", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Gtel"
        }),
        @UniqueConstraint(columnNames = {
                "Gmail"
        }),
})
@JsonIgnoreProperties(
		value = {"gpwd"},
		allowGetters = true
)

// 客户基本信息类
public class Guest {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gid;         // 客户编号

	@NotBlank
	@Size(max = 50)
	private String gname;    // 客户姓名

	@NotBlank
	@Size(max = 20)
	private String ggender;  // 客户性别

	@NotBlank
	@Size(max = 50)
	private String gtel;     // 客户电话

	@NotBlank
	@Size(max = 100)
	@Email
	private String gmail;    // 客户邮箱

	@NotBlank
	@Size(max = 1024)
	private String gpwd;     // 客户密码

	private boolean isVIP;   // 是否是VIP

	private Date vreg;       // VIP注册时间
	private Date vfin;       // VIP到期时间
	
	public Guest() { }

	public Guest(String gname, String ggender, String gtel, String gmail, String gpwd, boolean isVIP, Date vreg, Date vfin) {
		this.gname = gname;
		this.ggender = ggender;
		this.gtel = gtel;
		this.gmail = gmail;
		this.gpwd = gpwd;
		this.isVIP = isVIP;
		this.vreg = vreg;
		this.vfin = vfin;
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGgender() {
		return ggender;
	}

	public void setGgender(String ggender) {
		this.ggender = ggender;
	}

	public String getGtel() {
		return gtel;
	}

	public void setGtel(String gtel) {
		this.gtel = gtel;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getGpwd() {
		return gpwd;
	}

	public void setGpwd(String gpwd) {
		this.gpwd = gpwd;
	}

	public boolean isVIP() {
		return isVIP;
	}

	public void setVIP(boolean VIP) {
		isVIP = VIP;
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
