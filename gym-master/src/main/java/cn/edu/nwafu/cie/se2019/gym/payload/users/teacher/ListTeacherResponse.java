package cn.edu.nwafu.cie.se2019.gym.payload.users.teacher;

import java.util.List;

public class ListTeacherResponse {
    private List<TeacherInformationResponse> teachers;

    public List<TeacherInformationResponse> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherInformationResponse> teachers) {
        this.teachers = teachers;
    }
}
