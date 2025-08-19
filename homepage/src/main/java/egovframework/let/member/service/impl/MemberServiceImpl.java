package egovframework.let.member.service.impl;

import egovframework.let.member.service.MemberService;
import egovframework.let.member.service.MemberVO;
import egovframework.let.utl.sim.service.EgovFileScrty;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;


@Service("memberService")
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {

    @Resource(name="memberDAO")
	private MemberDAO memberDAO;
    
    //회원ID찾기
  	public MemberVO findId(MemberVO vo) throws Exception{
  		return memberDAO.findId(vo);
  	}
  	
  	//회원비밀번호찾기
  	public MemberVO findPassword(MemberVO vo) throws Exception{
  		return memberDAO.findPassword(vo);
  	}
  	
  	//회원비밀번호업데이트
  	public void passwordUpdate(MemberVO vo) throws Exception{
  		//입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getEmplyrId());
		vo.setPassword(enpassword);
		
		memberDAO.passwordUpdate(vo);
  	}
  	
	//회원목록
	public List<EgovMap> selectMberList(MemberVO vo) throws Exception{
		return memberDAO.selectMberList(vo);
	}
	
	//회원목록 수
	public int selectMberListCnt(MemberVO vo) throws Exception{
		return memberDAO.selectMberListCnt(vo);
	}

		
	//회원상세
	public EgovMap selectMber(MemberVO vo) throws Exception{
		return memberDAO.selectMber(vo);
	}
}
