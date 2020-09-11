package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.exception.OrderException;
import cn.edu.nwafu.cie.se2019.gym.model.Course;
import cn.edu.nwafu.cie.se2019.gym.model.CourseSelection;
import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.repository.CourseSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseSelectionService {

    @Autowired
    CourseSelectionRepository courseSelectionRepository;

    public int getCourseGuestNum(Long cid){
        return courseSelectionRepository.findByCid(cid).size();
    }

    public List<Course> GuestSelectCourseInfo(Long Gid){
        CourseService courseService = new CourseService();
        List<Course> ListCourse = new ArrayList<>();
        List<CourseSelection> ListCourseSelection = courseSelectionRepository.findByGid(Gid);
        if(!ListCourseSelection.isEmpty()){
            for(CourseSelection cs:ListCourseSelection){
                ListCourse.add(courseService.getCourseInfo(cs.getCid()));
            }
        }
        return ListCourse;
    }

    public List<Guest> TeacherSelectGuestByCourse(Long Cid){
        GuestService guestService = new GuestService();
        List<Guest> ListGuest = new ArrayList<>();
        List<CourseSelection> ListCourseSelection = courseSelectionRepository.findByCid(Cid);
        if(!ListCourseSelection.isEmpty()){
            for(CourseSelection cs:ListCourseSelection){
                ListGuest.add(guestService.getGuestInfo(cs.getGid()));
            }
        }
        return ListGuest;
    }

    public  void addCourseSelection(Long gid,Long cid) throws OrderException {
        List<CourseSelection> courseSelectionList = courseSelectionRepository.findByGid(gid);
        for(CourseSelection cs:courseSelectionList){
            if(cs.getCid()==cid){
                throw new OrderException("请勿重复选课！");
            }
        }
        CourseSelection courseSelection = new CourseSelection(gid,cid);
        courseSelectionRepository.save(courseSelection);
    }

    public  void  deleteCourseSelection(Long cno){
        Optional<CourseSelection> optionalCourseSelection = courseSelectionRepository.findById(cno);
        if(optionalCourseSelection.isPresent()){
            CourseSelection courseSelection = optionalCourseSelection.get();
            courseSelectionRepository.delete(courseSelection);
        }
    }

}
