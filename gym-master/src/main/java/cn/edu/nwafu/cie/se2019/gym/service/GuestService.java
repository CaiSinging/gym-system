package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.exception.ResourceExistedException;
import cn.edu.nwafu.cie.se2019.gym.exception.ResourceNotFoundException;
import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public Guest getGuestInfo(Long gid){
        Guest guest;
        Optional<Guest> optionalGuest = guestRepository.findById(gid);
        if(optionalGuest.isPresent()){
            guest = optionalGuest.get();
            return guest;
        }
        return null;
    }

    public List<Guest> getAllGuest(){
        return guestRepository.findAll();
    }

    public void updateGuestInfo(Long gid,String gname,String ggender,String gtel,String gmail){
        Optional<Guest> optionalGuest = guestRepository.findById(gid);
        if(optionalGuest.isPresent()){
            Guest guest = optionalGuest.get();
            guest.setGname(gname);
            guest.setGgender(ggender);
            guest.setGtel(gtel);
            guest.setGmail(gmail);
            guestRepository.save(guest);
        }
    }

    public void resetGuestPwd(String gtel,String pwd){
        Guest guest = guestRepository.findByGtel(gtel)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "gtel", gtel));
        guest.setGpwd(pwd);
        guestRepository.save(guest);
    }

    public void guestRegister(String gname,String ggender,String gtel,String gmail,String gpwd) {
        if (guestRepository.existsByGtel(gtel))
            throw new ResourceExistedException("Guest", "gtel", gtel);
        if (guestRepository.existsByGmail(gmail))
            throw new ResourceExistedException("Guest", "gmain", gmail);
        Guest guest = new Guest(gname,ggender,gtel,gmail,gpwd,false,null,null);
        guestRepository.save(guest);
    }

    public void deleteGuest(Long gid){
        Optional<Guest> optionalGuest = guestRepository.findById(gid);
        if(optionalGuest.isPresent()){
            Guest guest = optionalGuest.get();
            guestRepository.delete(guest);
        }
    }

}
