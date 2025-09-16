package egovframework.let.rsv.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.rsv.service.ReservationVO;
import egovframework.let.utl.fcc.service.EgovDateUtil;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ReservationController {

	
	@Resource(name = "reservationService")
    private ReservationService reservationService;
	
	//예약정보 목록 가져오기
	@RequestMapping(value = "/rsv/selectList.do")
	public String selectList(@ModelAttribute("searchVO") ReservationVO searchVO,  HttpServletRequest request, ModelMap model) throws Exception{
		
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<EgovMap> resultList = reservationService.selectReservationList(searchVO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = reservationService.selectReservationListCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user);
		
		return "rsv/RsvSelectList";
	}
		
	//예약정보 상세
	@RequestMapping(value = "/rsv/rsvSelect.do")
	public String select(@ModelAttribute("searchVO") ReservationVO searchVO, HttpServletRequest request, ModelMap model) throws Exception{
		request.getSession().removeAttribute("sessionReservationApply");
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user);
		
		ReservationVO result = reservationService.selectReservation(searchVO);
		
		model.addAttribute("result", result);
		return "rsv/RsvSelect";
	}
	
	
	//예약정보 캘린터
	@RequestMapping(value = "/rsv/selectCalendar.do")
	public String selectCalendar(@ModelAttribute("searchVO") ReservationVO searchVO,  HttpServletRequest request, ModelMap model) throws Exception{
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user);
		
		Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        
        //검색일자가 없으면 현재일자로 검색
        if (searchVO.getSearchDate() == null || !(searchVO.getSearchDate().length() == 6)) {
        	searchVO.setSearchDate(sdf.format(now.getTime()));
        }
        searchVO.setRecordCountPerPage(Integer.MAX_VALUE);
        searchVO.setFirstIndex(0);
        List<EgovMap> resultList = reservationService.selectReservationList(searchVO);
		model.addAttribute("resultList", resultList);
        
        now.setTime(sdf.parse(searchVO.getSearchDate()));

        now.add(Calendar.MONTH, -1);
        model.addAttribute("prevSearchDate", sdf.format(now.getTime()));

        now.add(Calendar.MONTH, 1);
        model.addAttribute("currYear", now.get(Calendar.YEAR));
        model.addAttribute("currMonth", now.get(Calendar.MONTH) + 1);
        model.addAttribute("firstDayOfMonth", now.get(Calendar.DAY_OF_WEEK));
        model.addAttribute("lastDayOfMonth", now.getActualMaximum(Calendar.DAY_OF_MONTH));
        model.addAttribute("today", EgovDateUtil.getToday());

        now.add(Calendar.MONTH, 1);
        model.addAttribute("nextSearchDate", sdf.format(now.getTime()));
		
		return "rsv/RsvCalendar";
	}
}