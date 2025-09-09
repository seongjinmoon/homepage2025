package egovframework.let.join.web;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.let.api.naver.service.NaverLoginService;
import egovframework.let.join.service.JoinService;
import egovframework.let.join.service.JoinVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import net.sf.json.JSONObject;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class JoinController {

	@Resource(name = "joinService")
    private JoinService joinService;
	
	@Resource(name = "naverLoginService")
    private NaverLoginService naverLoginService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	//약관동의
	@RequestMapping(value = "/join/siteUseAgree.do")
	public String siteUseAgree(@ModelAttribute("searchVO") JoinVO vo,  HttpServletRequest request, ModelMap model, HttpSession session) throws Exception{
		
		return "join/SiteUseAgree";
	}
	
	//회원구분
	@RequestMapping(value = "/join/memberType.do")
	public String memberType(@ModelAttribute("searchVO") JoinVO vo,  HttpServletRequest request, ModelMap model, HttpSession session) throws Exception{
		//약관동의
		if(!"Y".equals(vo.getAgree01()) || !"Y".equals(vo.getAgree02())) {
			model.addAttribute("message", "잘못 된 접근입니다.");
			return "forward:/join/siteUseAgree.do";
		}
		
		//Naver
        String domain = request.getServerName();
        String port = Integer.toString(request.getServerPort());
        String naverAuthUrl = naverLoginService.getAuthorizationUrl(session, domain, port);
        model.addAttribute("naverAuthUrl", naverAuthUrl);
        
        //네이버로그인 타입체크용
        request.getSession().setAttribute("naverLoginType", "JOIN");
        
		return "join/MemberType";
	}
		
	//회원등록 폼
	@RequestMapping(value = "/join/memberRegist.do")
	public String memberRegist(@ModelAttribute("searchVO") JoinVO vo,  HttpServletRequest request, ModelMap model) throws Exception{
		//약관동의
		if(!"Y".equals(vo.getAgree01()) || !"Y".equals(vo.getAgree02())) {
			model.addAttribute("message", "잘못 된 접근입니다.");
			return "forward:/join/siteUseAgree.do";
		//회원유형선택여부	
		}else if(EgovStringUtil.isEmpty(vo.getLoginType())) {
			model.addAttribute("message", "잘못 된 접근입니다.");
			return "forward:/join/siteUseAgree.do";
		}
		
		return "join/MemberRegist";
	}
	
	//아이디 중복체크
	@RequestMapping(value = "/join/duplicateCheck.do")
	public void duplicateCheck(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String successYn = "Y";
		String message = "성공";
		
		JSONObject jo = new JSONObject();
    	response.setContentType("application/json; charset=utf-8");
    	
    	int duplicateCnt = joinService.duplicateCheck(vo);
		if(duplicateCnt > 0) {
			successYn = "N";
			message = egovMessageSource.getMessage("fail.duplicate.member"); //이미 사용중인 ID입니다.;
		}
    	
		jo.put("successYn", successYn);
		jo.put("message", message);
		
		PrintWriter printwriter = response.getWriter();
    	printwriter.println(jo.toString());
		printwriter.flush();
		printwriter.close();
	}
	
	/*
	//아이디 중복체크
	@RequestMapping(value = "/join/duplicateCheck.do")
	public void duplicateCheck(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String successYn = "Y";
		String message = "성공";
		
		org.json.simple.JSONObject jo = new org.json.simple.JSONObject();
    	response.setContentType("application/json; charset=utf-8");
    	
    	int duplicateCnt = joinService.duplicateCheck(vo);
		if(duplicateCnt > 0) {
			successYn = "N";
			message = egovMessageSource.getMessage("fail.duplicate.member"); //이미 사용중인 ID입니다.;
		}
    	
		jo.put("successYn", successYn);
		jo.put("message", message);
		
		PrintWriter printwriter = response.getWriter();
    	printwriter.println(jo.toString());
		printwriter.flush();
		printwriter.close();
	}
	*/
	
	//회원가입
	@RequestMapping(value = "/join/insertMember.do")
	public String insertMember(@ModelAttribute("searchVO") JoinVO vo,  HttpServletRequest request, ModelMap model) throws Exception{
		
		if(!EgovStringUtil.isEmpty(vo.getLoginType())) {
			//일반가입을 제외하고는 ID값은 SNS명 + ID값
			if(!("normal").equals(vo.getLoginType())){
				vo.setEmplyrId(vo.getLoginType() + "-" + vo.getEmplyrId());
				vo.setPassword("");
				vo.setPasswordHint("SNS가입자");
				vo.setPasswordCnsr("SNS가입자");
			}
		}
		
		if(joinService.duplicateCheck(vo) > 0) {
			//SNS 작업 후
			if(!("normal").equals(vo.getLoginType())){
				model.addAttribute("message", "이미 등록 된 SNS계정입니다.");
			}else {
				model.addAttribute("message", egovMessageSource.getMessage("fail.duplicate.member")); //이미 사용중인 ID입니다.
			}
			/*
			//SNS 작업 전
			model.addAttribute("message", egovMessageSource.getMessage("fail.duplicate.member")); //이미 사용중인 ID입니다.
			*/
			return "forward:/login/login.do";
		}else {
			joinService.insertJoin(vo);
			model.addAttribute("message", egovMessageSource.getMessage("join.request.msg")); //회원신청이 정상적으로 완료되었습니다.\n로그인 후 이용해 주세요.
		}
		
		return "join/MemberComplete";
	}
	
}