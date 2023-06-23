package reservation.utils;

import reservation.pojo.Reservation;

import java.util.List;

public class Utils {
    public static boolean isOccupied(List<Reservation> reservations, Reservation reservation) {
        if (reservations.size() == 0
                || reservation.getEndTime() < reservation.getStartTime()
                || reservation.getStartTime() > reservations.get(reservations.size() - 1).getEndTime()) {
            return true;
        } else if (reservations.size() == 1) {
            return reservation.getEndTime() < reservations.get(0).getStartTime()
                    || reservation.getStartTime() > reservations.get(0).getEndTime();
        } else {
            for (int i = 0; i < reservations.size(); i++) {
                if (reservation.getEndTime() < reservations.get(i).getStartTime()) {
                    if (i == 0 || reservation.getStartTime() > reservations.get(i - 1).getEndTime()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
