package egovframework.let.rsv.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;

import egovframework.let.join.service.JoinVO;
import egovframework.let.rsv.service.ReservationVO;

@Repository("reservationDAO")
public class ReservationDAO extends EgovAbstractMapper {
    
    //예약 목록 가져오기
    public List<EgovMap> selectReservationList(ReservationVO vo) throws Exception{
    	return selectList("reservationDAO.selectReservationList", vo);
    }
    
    //예약 목록 수
    public int selectReservationListCnt(ReservationVO vo) throws Exception {
    	return selectOne("reservationDAO.selectReservationListCnt", vo);
    }
  		
  	//예약 상세정보
    public ReservationVO selectReservation(ReservationVO vo) throws Exception{
    	return selectOne("reservationDAO.selectReservation", vo);
    }
  	
  	//예약 등록하기
    public void insertReservation(ReservationVO vo) throws Exception{
    	insert("reservationDAO.insertReservation", vo);
    }
  	
  	//예약 수정하기
    public void updateReservation(ReservationVO vo) throws Exception{
    	update("reservationDAO.updateReservation", vo);
    }
  	
  	//예약 삭제하기
    public void deleteReservation(ReservationVO vo) throws Exception{
    	delete("reservationDAO.deleteReservation", vo);
    }
		
}
