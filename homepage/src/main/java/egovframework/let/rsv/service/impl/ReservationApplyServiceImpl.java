package egovframework.let.rsv.service.impl;

import egovframework.com.cmm.service.FileVO;
import egovframework.let.rsv.service.ReservationApplyService;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.rsv.service.ReservationVO;
import egovframework.let.rsv.service.ReservationApplyVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
/*
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
*/
import org.springframework.stereotype.Service;


@Service("reservationApplyService")
public class ReservationApplyServiceImpl extends EgovAbstractServiceImpl implements ReservationApplyService {

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
    @Resource(name="reservationApplyDAO")
	private ReservationApplyDAO reservationApplyDAO;
    
    @Resource(name = "egovReqIdGnrService")
    private EgovIdGnrService idgenService;
    /*
    @Resource(name = "egovReqTempIdGnrService")
    private EgovIdGnrService idgenTempService;
    */
    @Resource(name = "reservationService")
    private ReservationService reservationService;
    
    //예약가능여부 확인
  	public ReservationApplyVO rsvCheck(ReservationApplyVO vo) throws Exception{
  		//신청 인원 체크
		ReservationVO reservationVO = new ReservationVO();
		reservationVO.setResveId(vo.getResveId());
		ReservationVO result = reservationService.selectReservation(reservationVO);
		if(result.getMaxAplyCnt() <= result.getApplyFCnt()) {
			vo.setErrorCode("ERROR-R1");
			vo.setMessage("마감 되었습니다.");
		}else if(reservationApplyDAO.duplicateApplyCheck(vo) > 0){
			vo.setErrorCode("ERROR-R2");
			vo.setMessage("이미 해당 프로그램 예약이 되어져 있습니다.");
		}
		
		return vo;
  	}
  	
    //예약자 등록하기
  	public ReservationApplyVO insertReservationApply(ReservationApplyVO vo) throws Exception {
  		//신청 인원 체크
  		ReservationVO reservationVO = new ReservationVO();
  		reservationVO.setResveId(vo.getResveId());
  		ReservationVO result = reservationService.selectReservation(reservationVO);
  		if(result.getMaxAplyCnt() <= result.getApplyFCnt()) {
  			vo.setErrorCode("ERROR-R1");
  			vo.setMessage("마감 되었습니다.");
  		}else{
  			//기존 신청여부
  			if(reservationApplyDAO.duplicateApplyCheck(vo) > 0) {
  				vo.setErrorCode("ERROR-R2");
  				vo.setMessage("이미 해당 프로그램 예약이 되어져 있습니다.");
  			}else {
  				String id = idgenService.getNextStringId();
  				vo.setReqstId(id);
  				
  				//최대신청가능인원 체크
  				vo.setMaxAplyCnt(result.getMaxAplyCnt());
  				int cnt = reservationApplyDAO.insertReservationApply(vo);
  				if(cnt == 0) {
  					vo.setErrorCode("ERROR-R1");
  		  			vo.setMessage("마감 되었습니다.");
  				}
  			}
  		}
  		
  		return vo;
  	}
  	
    //예약자 목록 가져오기
	public List<EgovMap> selectReservationApplyList(ReservationApplyVO vo) throws Exception {
		return reservationApplyDAO.selectReservationApplyList(vo);
	}
	
	//예약자 목록 수
	public int selectReservationApplyListCnt(ReservationApplyVO vo) throws Exception {
		return reservationApplyDAO.selectReservationApplyListCnt(vo);
	}
	
	//예약자 상세정보
	public ReservationApplyVO selectReservationApply(ReservationApplyVO vo) throws Exception {
		return reservationApplyDAO.selectReservationApply(vo);
	}
	
	//예약자 수정하기
	public void updateReservationApply(ReservationApplyVO vo) throws Exception {
		reservationApplyDAO.updateReservationApply(vo);
	}
	
	//예약자 삭제하기
	public void deleteReservationApply(ReservationApplyVO vo) throws Exception {
		reservationApplyDAO.deleteReservationApply(vo);
	}
	
	//예약자 승인처리
	public void updateReservationConfirm(ReservationApplyVO vo) throws Exception {
		reservationApplyDAO.updateReservationConfirm(vo);
	}
	
	
}