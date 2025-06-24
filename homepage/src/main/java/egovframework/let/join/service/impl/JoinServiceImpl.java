package egovframework.let.join.service.impl;

import egovframework.let.join.service.JoinService;
import egovframework.let.join.service.JoinVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.let.utl.sim.service.EgovFileScrty;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;


@Service("joinService")
public class JoinServiceImpl extends EgovAbstractServiceImpl implements JoinService {

    @Resource(name="joinDAO")
	private JoinDAO joinDAO;
    
    @Resource(name = "joinIdGnrService")
    private EgovIdGnrService idgenService;
    
    //ID중복체크
  	public int duplicateCheck(JoinVO vo) throws Exception {
  		return joinDAO.duplicateCheck(vo);
  	}
  	
    //회원등록
	public String insertJoin(JoinVO vo) throws Exception {
    	String esntlId = idgenService.getNextStringId();
    	vo.setEsntlId(esntlId);
    	
    	//입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getEmplyrId());
		vo.setPassword(enpassword);
    	
		//이메일
		if(!EgovStringUtil.isEmpty(vo.getEmailId()) && !EgovStringUtil.isEmpty(vo.getEmailDomain())) {
			vo.setEmailAdres(vo.getEmailId() + "@" + vo.getEmailDomain());
		}
		
		joinDAO.insertJoin(vo);
		return esntlId;
	}
    
    

		
}
