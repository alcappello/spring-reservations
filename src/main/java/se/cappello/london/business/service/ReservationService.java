package se.cappello.london.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.cappello.london.business.domain.RoomReservation;
import se.cappello.london.data.entity.Guest;
import se.cappello.london.data.entity.Reservation;
import se.cappello.london.data.entity.Room;
import se.cappello.london.data.repository.GuestRepository;
import se.cappello.london.data.repository.ReservationRepository;
import se.cappello.london.data.repository.RoomRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService {
    private RoomRepository roomRepository;
    private GuestRepository guestRepository;
    private ReservationRepository reservationRepository;

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationForDate(String dateString) {

        Date date = createDateFromDateString(dateString);

        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();

        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });

        Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
        if (null != reservations) {
            reservations.forEach(reservation -> {
                Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());
                if (guestResponse.isPresent()) {
                    Guest guest = guestResponse.get();
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
                    roomReservation.setDate(date);
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                    roomReservation.setGuestId(guest.getId());
                }
            });
        }

        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long roomId : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(roomId));
        }
        return roomReservations;
    }

    private Date createDateFromDateString(String dateString){
        Date date = null;
        if(null!=dateString) {
            try {
                date = DATE_FORMAT.parse(dateString);
            }catch(ParseException pe){
                date = new Date();
            }
        }else{
            date = new Date();
        }
        return date;
    }
}
