package egovframework.let.login.service.impl;


import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.com.cmm.LoginVO;

@Repository("egovLoginDAO")
public class LoginDAO extends EgovAbstractMapper {
	
    //일반 로그인을 처리한다
  	public LoginVO actionLogin(LoginVO vo) throws Exception{
  		return selectOne("egovLoginDAO.actionLogin", vo);
  	}

  	//아이디를 찾는다.
  	public LoginVO searchId(LoginVO vo) throws Exception{
  		return selectOne("egovLoginDAO.searchId", vo);
  	}

  	//비밀번호를 찾는다.
  	public LoginVO searchPassword(LoginVO vo) throws Exception{
  		return selectOne("egovLoginDAO.searchPassword", vo);
  	}

  	//변경된 비밀번호를 저장한다.
  	public void updatePassword(LoginVO vo) throws Exception{
  		update("egovLoginDAO.updatePassword", vo);
  	}
    
		
}
