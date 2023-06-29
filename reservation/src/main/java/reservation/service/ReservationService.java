package reservation.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reservation.mapper.CommentMapper;
import reservation.mapper.ReservationMapper;
import reservation.pojo.*;
import reservation.utils.ConstantData;
import reservation.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    ReservationMapper reservationMapper;
    @Autowired
    CommentMapper commentMapper;
    public void check(Integer id){
        Reservation reservation = reservationMapper.selectById(id);
        if (Objects.equals(reservation.getStatus(), ConstantData.STATUS_TO_BE_SIGNED)){
            reservation.setStatus(ConstantData.STATUS_SIGNED);
            reservationMapper.updateById(reservation);
        }
    }
    public Info getInfo(Integer facilityId){
        Integer rates = 0;
        List<Comment> comments = commentMapper.selectList(Wrappers.lambdaQuery(Comment.class)
                .eq(Comment::getFacilityId,facilityId));
        List<CommentVue> commentInfos = new ArrayList<>();
        for(Comment comment: comments){
            commentInfos.add(new CommentVue(comment));
            rates = rates+comment.getRate();
        }
        Integer avgRate;
        if (comments.size() == 0){
            avgRate = 0;
        }
        else {
            avgRate = rates/comments.size();
        }
        return new Info(avgRate,null,commentInfos);
    }

    public List<Reservation> getReservations(Integer userId) {
        List<Reservation> reservations = reservationMapper.selectList(Wrappers.lambdaQuery(Reservation.class)
                .eq(Reservation::getUserId, userId));
        return reservations.stream()
                .sorted(Comparator.comparing(Reservation::getStartTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Reservation> getReservationInfo(Integer facilityId, Long currentTime) {
        List<Reservation> reservations = reservationMapper.selectList(Wrappers.lambdaQuery(Reservation.class)
                .eq(Reservation::getFacilityId, facilityId));
        return reservations.stream()
                .filter(x -> x.getEndTime() > currentTime)
                .filter(x -> !Objects.equals(x.getStatus(), ConstantData.STATUS_CANCELED))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .collect(Collectors.toList());
    }

    public boolean reserve(Reservation reservation, Facility facility) {
        long start_relative = reservation.getStartTime() % 86400000 + 8 * 60 * 60 * 1000L;
        long end_relative = reservation.getEndTime() % 86400000 + 8 * 60 * 60 * 1000L;
        long duration = reservation.getEndTime() - reservation.getStartTime();
        long currentTime = System.currentTimeMillis();
        //只能预约一周之内的时间
        //开始及结束时间要在指定范围内
        //持续时间要在指定范围内
        if (reservation.getStartTime() - currentTime >= 604800000L
                || reservation.getStartTime() < currentTime
                || reservation.getStartTime() >= reservation.getEndTime()
                || start_relative <= facility.getStartTime()
                || end_relative >= facility.getEndTime()
                || duration < facility.getDurationMin()
                || duration > facility.getDurationMax()) {
            return false;
        } else {
            List<Reservation> reservations = getReservationInfo(reservation.getFacilityId(), System.currentTimeMillis());
            if (Utils.isOccupied(reservations, reservation)) {
                reservation.setStatus(ConstantData.STATUS_WAIT);
                reservationMapper.insert(reservation);
                return true;
            } else {
                return false;
            }
        }

    }

    public List<Reservation> getRawReservations() {
        List<Reservation> reservations = reservationMapper.selectList(Wrappers.lambdaQuery(Reservation.class));
        return reservations.stream()
                .filter(x -> x.getStatus().equals(ConstantData.STATUS_WAIT))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .collect(Collectors.toList());
    }

    public boolean updateReservation(Reservation reservation) {
        return reservationMapper.updateById(reservation) > 0;
    }

    public List<Reservation> getToBeSignedReservations() {
        List<Reservation> reservations = reservationMapper.selectList(Wrappers.lambdaQuery(Reservation.class));
        return reservations.stream()
                .filter(x -> x.getStatus().equals(ConstantData.STATUS_TO_BE_SIGNED))
                .sorted(Comparator.comparing(Reservation::getStartTime))
                .collect(Collectors.toList());
    }

    public boolean cancelReservation(Integer reservationId) {
        Reservation reservation = reservationMapper.selectById(reservationId);
        if (reservation.getStatus().equals(ConstantData.STATUS_WAIT)) {
            reservation.setStatus(ConstantData.STATUS_CANCELED);
            return reservationMapper.updateById(reservation) > 0;
        } else {
            return false;
        }
    }

    public boolean putComment(Integer userId, Integer facilityId,Integer reservationId, Integer rate, String content) {
        Comment comment;
        comment = commentMapper.selectOne(Wrappers.lambdaQuery(Comment.class)
                .eq(Comment::getUserId, userId)
                .eq(Comment::getFacilityId, facilityId)
                .eq(Comment::getReservationId,reservationId));
        if (comment != null) {
            comment.setRate(rate);
            comment.setContent(content);
            return commentMapper.updateById(comment) > 0;
        } else {
            comment = new Comment(null, userId, facilityId,reservationId, rate, content);
            return commentMapper.insert(comment) > 0;
        }
    }
    public Comment getComment(Integer userId,Integer facilityId,Integer reservationId){
        return commentMapper.selectOne(Wrappers.lambdaQuery(Comment.class)
                .eq(Comment::getUserId, userId)
                .eq(Comment::getFacilityId, facilityId)
                .eq(Comment::getReservationId,reservationId));
    }
}
