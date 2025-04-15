package egovframework.let.temp2.web;

import java.util.List;

import egovframework.let.temp2.service.Temp2Service;
import egovframework.let.temp2.service.Temp2VO;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Temp2Controller {

	
	@Resource(name = "temp2Service")
    private Temp2Service temp2Service;
	
	//임시데이터 목록 가져오기
	@RequestMapping(value = "/temp2/selectList.do")
	public String selectList(Temp2VO temp2VO,  HttpServletRequest request, ModelMap model) throws Exception{
		
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(temp2VO.getPageIndex());
		paginationInfo.setRecordCountPerPage(temp2VO.getPageUnit());
		paginationInfo.setPageSize(temp2VO.getPageSize());

		temp2VO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		temp2VO.setLastIndex(paginationInfo.getLastRecordIndex());
		temp2VO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<EgovMap> resultList = temp2Service.selectTempList(temp2VO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = temp2Service.selectTempListCnt(temp2VO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		model.addAttribute("totCnt", totCnt);
		
		return "temp2/TempSelectList";
	}
		
	//임시데이터 가져오기
	@RequestMapping(value = "/temp2/select.do")
	public String select(Temp2VO temp2VO, HttpServletRequest request, ModelMap model) throws Exception{
		Temp2VO result = temp2Service.selectTemp(temp2VO);
		model.addAttribute("result", result);
		return "temp2/TempSelect";
	}
	
	//임시데이터 등록/수정
	@RequestMapping(value = "/temp2/tempRegist.do")
	public String tempRegist(Temp2VO temp2VO, HttpServletRequest request, ModelMap model) throws Exception{
		Temp2VO result = new Temp2VO();
		if(!EgovStringUtil.isEmpty(temp2VO.getTempId())) {
			result = temp2Service.selectTemp(temp2VO);
		}
		model.addAttribute("result", result);
		
		return "temp2/TempRegist";
	}
	
	//임시데이터 등록하기
	@RequestMapping(value = "/temp2/insert.do")
	public String insert(Temp2VO temp2VO, HttpServletRequest request, ModelMap model) throws Exception{
		temp2Service.insertTemp(temp2VO);
		return "forward:/temp2/selectList.do";
	}
	
	//임시데이터 수정하기
	@RequestMapping(value = "/temp2/update.do")
	public String update(Temp2VO temp2VO, HttpServletRequest request, ModelMap model) throws Exception{
		temp2Service.updateTemp(temp2VO);
		return "forward:/temp2/selectList.do";
	}
	
	//임시데이터 삭제하기
	@RequestMapping(value = "/temp2/delete.do")
	public String delete(Temp2VO temp2VO, HttpServletRequest request, ModelMap model) throws Exception{
		temp2Service.deleteTemp(temp2VO);
		return "forward:/temp2/selectList.do";
	}
	
}