package egovframework.let.temp.service.impl;

import egovframework.let.temp.service.TempService;
import egovframework.let.temp.service.TempVO;


import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;


@Service("tempService")
public class TempServiceImpl extends EgovAbstractServiceImpl implements TempService {
    
    @Resource(name = "tempDAO")
    private TempDAO tempDAO;
    
	/*
    @Resource(name = "egovTempIdGnrService")
    private EgovIdGnrService idgenService;
    */
	
    //임시데이터 가져오기
	public TempVO selectTemp(TempVO vo) throws Exception {
		return tempDAO.selectTemp(vo);
	}
	
	//임시데이터 목록 가져오기
	public List<EgovMap> selectTempList(TempVO vo) throws Exception {
		return tempDAO.selectTempList(vo);
	}
	
	//임시데이터 등록하기
	public String insertTemp(TempVO vo) throws Exception {
		tempDAO.insertTemp(vo);
		
		return null;
	}
	
	//임시데이터 수정하기
	public void updateTemp(TempVO vo) throws Exception{
		tempDAO.updateTemp(vo);
	}
	
	//임시데이터 삭제하기
	public void deleteTemp(TempVO vo) throws Exception{
		tempDAO.deleteTemp(vo);
	}
	
	//임시데이터 목록 수
	public int selectTempListCnt(TempVO vo) throws Exception {
		return tempDAO.selectTempListCnt(vo);
	}
	
}
