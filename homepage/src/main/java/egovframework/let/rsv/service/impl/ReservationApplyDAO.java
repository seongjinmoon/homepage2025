package egovframework.let.rsv.service.impl;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;

import egovframework.let.rsv.service.ReservationApplyVO;

@Repository("reservationApplyDAO")
public class ReservationApplyDAO extends EgovAbstractMapper {
    
    //예약자 등록하기
  	int insertReservationApply(ReservationApplyVO vo) throws Exception{
  		return insert("reservationApplyDAO.insertReservationApply", vo);
  	}
  	
  	//기존 신청여부
  	int duplicateApplyCheck(ReservationApplyVO vo) throws Exception{
  		return selectOne("reservationApplyDAO.duplicateApplyCheck", vo);
  	}
  		
  	//예약자 목록 가져오기
  	List<EgovMap> selectReservationApplyList(ReservationApplyVO vo) throws Exception{
  		return selectList("reservationApplyDAO.selectReservationApplyList", vo);
  	}
  	
  	//예약자 목록 수
  	int selectReservationApplyListCnt(ReservationApplyVO vo) throws Exception{
  		return selectOne("reservationApplyDAO.selectReservationApplyListCnt", vo);
  	}
  		
  	//예약자 상세정보
  	ReservationApplyVO selectReservationApply(ReservationApplyVO vo) throws Exception{
  		return selectOne("reservationApplyDAO.selectReservationApply", vo);
  	}
  	
  	//예약자 수정하기
  	void updateReservationApply(ReservationApplyVO vo) throws Exception{
  		update("reservationApplyDAO.updateReservationApply", vo);
  	}
  	
  	//예약자 삭제하기
  	void deleteReservationApply(ReservationApplyVO vo) throws Exception{
  		delete("reservationApplyDAO.deleteReservationApply", vo);
  	}
  	
  	//예약자 승인처리
  	void updateReservationConfirm(ReservationApplyVO vo) throws Exception{
  		update("reservationApplyDAO.updateReservationConfirm", vo);
  	}
  	
  	//예약가능여부 확인
  	//void rsvCheck(ReservationApplyVO vo) throws Exception;
  	
  	//임시예약자 등록하기
  	void insertReservationApplyTemp(ReservationApplyVO vo) throws Exception{
  		insert("reservationApplyDAO.insertReservationApplyTemp", vo);
  	}
  	
  	//임시예약자 삭제
  	void deleteReservationApplyTemp(ReservationApplyVO vo) throws Exception{
  		delete("reservationApplyDAO.deleteReservationApplyTemp", vo);
  	}
  	
  	//임시예약자 목록 가져오기
  	List<EgovMap> selectReservationApplyTemp(ReservationApplyVO vo) throws Exception{
  		return selectList("reservationApplyDAO.selectReservationApplyTemp", vo);
  	}
  	
  	//임시예약자 예약자로 일괄등록
  	void insertReservationApplyTempAll(ReservationApplyVO vo) throws Exception{
  		insert("reservationApplyDAO.insertReservationApplyTempAll", vo);
  	}
		
}
