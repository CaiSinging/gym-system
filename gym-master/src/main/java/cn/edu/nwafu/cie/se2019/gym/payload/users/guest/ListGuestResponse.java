package cn.edu.nwafu.cie.se2019.gym.payload.users.guest;

import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class ListGuestResponse {

    private List<GuestInformationResponse> guests;

    public List<GuestInformationResponse> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestInformationResponse> guests) {
        this.guests = guests;
    }
}
