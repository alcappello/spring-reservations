package se.cappello.hotel.web.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.cappello.hotel.aspect.Loggable;
import se.cappello.hotel.business.domain.RoomReservation;
import se.cappello.hotel.business.service.ReservationService;

import java.util.List;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    @Loggable
    public String getReservations(@RequestParam(value = "date", required = false)
                                              String date, Model model) {


        List<RoomReservation> roomReservationList = this.reservationService.getRoomReservationForDate(date);

        model.addAttribute("roomReservations", roomReservationList);
        return "reservations";
    }
}
