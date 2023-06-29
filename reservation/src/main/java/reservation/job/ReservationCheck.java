package reservation.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reservation.client.AuthClient;
import reservation.pojo.Reservation;
import reservation.service.ReservationService;
import reservation.utils.ConstantData;

import java.util.List;

@Component
public class ReservationCheck {
    @Autowired
    ReservationService reservationService;
    @Autowired
    AuthClient authClient;

    //30秒执行一次
    @Scheduled(cron = "*/30000 * * * * ?")
    public void raw2tobesigned(){
        long currentTime = System.currentTimeMillis();
        List<Reservation> reservations = reservationService.getRawReservations();
        if(reservations.size() == 0){
            return;
        }
        Reservation mostRecent = reservations.get(0);
        if (mostRecent.getStartTime()-currentTime<=1000*60*10L){
            mostRecent.setStatus(ConstantData.STATUS_TO_BE_SIGNED);
            reservationService.updateReservation(mostRecent);
        }
    }
    //30秒执行一次
    @Scheduled(cron = "*/30000 * * * * ?")
    public void tobesigned2break(){
        long currentTime = System.currentTimeMillis();
        List<Reservation> reservations = reservationService.getToBeSignedReservations();
        if(reservations.size() == 0){
            return;
        }
        Reservation mostRecent = reservations.get(0);
        if (mostRecent.getStartTime()-currentTime<0){
            mostRecent.setStatus(ConstantData.STATUS_BREAK);
            reservationService.updateReservation(mostRecent);
            authClient.userBreak(mostRecent.getUserId());
        }
    }
}
