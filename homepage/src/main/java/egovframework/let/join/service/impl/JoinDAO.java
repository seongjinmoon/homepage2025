package egovframework.let.join.service.impl;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.join.service.JoinVO;

@Repository("joinDAO")
public class JoinDAO extends EgovAbstractMapper {
	
	//ID중복체크
    public int duplicateCheck(JoinVO vo) throws Exception {
    	return selectOne("joinDAO.duplicateCheck", vo);
    }
    
	//회원등록
    public void insertJoin(JoinVO vo) throws Exception {
    	insert("joinDAO.insertJoin", vo);
    }
		
}
