package cn.edu.nwafu.cie.se2019.gym.payload.users.teacher;

import cn.edu.nwafu.cie.se2019.gym.model.Teacher;

import java.util.Optional;

public class TeacherInformationResponse {
    private Long id;
    private String name, gender, tel, email;

    public TeacherInformationResponse() {
    }

    public TeacherInformationResponse(Teacher t){
        setId(t.getTid());
        setName(t.getTname());
        setGender(t.getTgender());
        setTel(t.getTtel());
        setEmail(t.getTmail());
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
}
