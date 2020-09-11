package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.exception.ResourceExistedException;
import cn.edu.nwafu.cie.se2019.gym.exception.ResourceNotFoundException;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    public Teacher getTeacherInfo(Long tid){
        Teacher teacher;
        Optional<Teacher> optionalTeacher = teacherRepository.findById(tid);
        if(optionalTeacher.isPresent()){
            teacher = optionalTeacher.get();
            return teacher;
        }
        return null;
    }

    public List<Teacher> getAllTeacher(){
        return teacherRepository.findAll();
    }

    public void updateTeacherInfo(Long tid,String tname,String tgender,String ttel,String tmail){
        Optional<Teacher> optionalTeacher = teacherRepository.findById(tid);
        if(optionalTeacher.isPresent()){
            Teacher teacher = optionalTeacher.get();
            teacher.setTname(tname);
            teacher.setTgender(tgender);
            teacher.setTtel(ttel);
            teacher.setTmail(tmail);
            teacherRepository.save(teacher);
        }
    }

    public void resetTeacherPwd(String ttel,String pwd){
        Teacher teacher = teacherRepository.findByTtel(ttel)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "ttel", ttel));
        teacher.setTpwd(pwd);
        teacherRepository.save(teacher);
    }

    public void addNewteacher(String tname,String tgender,String ttel,String tmail,String tpwd) {
        if (teacherRepository.existsByTtel(ttel))
            throw new ResourceExistedException("Teacher", "ttel", ttel);
        if (teacherRepository.existsByTmail(tmail))
            throw new ResourceExistedException("Teacher", "tmail", tmail);
        Teacher teacher = new Teacher(tname,tgender,ttel,tmail,tpwd);
        teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long tid){
        Optional<Teacher> optionalTeacher = teacherRepository.findById(tid);
        if(optionalTeacher.isPresent()){
            Teacher teacher = optionalTeacher.get();
            teacherRepository.delete(teacher);
        }
    }
}
