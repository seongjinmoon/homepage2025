package egovframework.let.temp.service.impl;
import egovframework.let.temp.service.TempVO;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;


@Repository("tempDAO")
public class TempDAO extends EgovAbstractMapper {

    //임시데이터 가져오기
    public TempVO selectTemp(TempVO vo) throws Exception {
    	return selectOne("tempDAO.selectTemp", vo);
    }
    
    //임시데이터 목록 가져오기
	public List<EgovMap> selectTempList(TempVO vo) throws Exception {
  		//return (List<EgovMap>) list("tempDAO.selectTempList", vo);
  		return selectList("tempDAO.selectTempList", vo);
  	}
	
	//임시데이터 등록
    public void insertTemp(TempVO vo) throws Exception {
    	insert("tempDAO.insertTemp", vo);
    }
	
    //임시데이터 수정
    public void updateTemp(TempVO vo) throws Exception {
    	update("tempDAO.updateTemp", vo);
    }
    
    //임시데이터 삭제
    public void deleteTemp(TempVO vo) throws Exception {
    	delete("tempDAO.deleteTemp", vo);
    }
	
    //임시데이터 목록 수
    public int selectTempListCnt(TempVO vo) throws Exception {
    	return selectOne("tempDAO.selectTempListCnt", vo);
    }
}
