package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.model.PhysicalTest;
import cn.edu.nwafu.cie.se2019.gym.repository.PhysicalTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PhysicalTestService {

    @Autowired
    PhysicalTestRepository physicalTestRepository;

    public List<PhysicalTest> getAllPhysicalTest() {
       return physicalTestRepository.findAll();
    }

    public List<PhysicalTest> getGuestPhysicalTest(Long gid){
         return physicalTestRepository.findByGid(gid);
    }

    public List<PhysicalTest> getTeacherPhysicalTest(Long tid){
        return physicalTestRepository.findByTid(tid);
    }

    public void updatePhysicalTest(Long pno,Long gid, Date time, Long tid, int height, float weight, float fat, int vitalCapacity, float sitAndReach, int sitUp, float gripPower){
        Optional<PhysicalTest> optionalPhysicalTest = physicalTestRepository.findById(pno);
        if (optionalPhysicalTest.isPresent()) {
            PhysicalTest physicalTest = optionalPhysicalTest.get();
            physicalTest.setGid(gid);
            physicalTest.setTime(time);
            physicalTest.setTid(tid);
            physicalTest.setHeight(height);
            physicalTest.setWeight(weight);
            physicalTest.setFat(fat);
            physicalTest.setVitalCapacity(vitalCapacity);
            physicalTest.setSitAndReach(sitAndReach);
            physicalTest.setSitUp(sitUp);
            physicalTest.setGripPower(gripPower);
            physicalTestRepository.save(physicalTest);
        }
    }

    public  void addPhysicalTest(Long gid, Date time, Long tid, int height, float weight, float fat, int vitalCapacity, float sitAndReach, int sitUp, float gripPower){
        PhysicalTest physicalTest = new PhysicalTest(gid,time,tid,height,weight,fat,vitalCapacity,sitAndReach,sitUp,gripPower);
        physicalTestRepository.save(physicalTest);
    }

    public void  deletePhysicalTest(Long pno){
        Optional<PhysicalTest> optionalPhysicalTest = physicalTestRepository.findById(pno);
        if(optionalPhysicalTest.isPresent()){
            PhysicalTest physicalTest = optionalPhysicalTest.get();
            physicalTestRepository.delete(physicalTest);
        }
    }
}
