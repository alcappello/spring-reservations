package se.cappello.london.web.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.cappello.london.business.domain.RoomReservation;
import se.cappello.london.business.service.ReservationService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getReservations(@RequestParam(value = "date", required = false)
                                              String date, Model model) {


        List<RoomReservation> roomReservationList = this.reservationService.getRoomReservationForDate(date);

        model.addAttribute("roomReservations", roomReservationList);
        return "reservations";
    }
}
