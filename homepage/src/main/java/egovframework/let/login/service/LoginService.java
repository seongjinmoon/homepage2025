package egovframework.let.login.service;

import egovframework.com.cmm.LoginVO;

//로그인
public interface LoginService {

	//일반 로그인을 처리
	public LoginVO actionLogin(LoginVO vo) throws Exception;

	//아이디를 찾는다.
	public LoginVO searchId(LoginVO vo) throws Exception;

	//비밀번호를 찾는다.
	public boolean searchPassword(LoginVO vo) throws Exception;

}