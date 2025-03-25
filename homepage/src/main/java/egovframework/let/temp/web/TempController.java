package egovframework.let.temp.web;

import egovframework.let.temp.service.TempService;
import egovframework.let.temp.service.TempVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TempController {
	
	@Resource(name = "tempService")
    private TempService tempService;
	/*
	@Resource(name = "temp2Service")
    private TempService temp2Service;
	*/
	//임시데이터 가져오기
	@RequestMapping(value = "/temp/select.do")
	public String select(TempVO tempVO, HttpServletRequest request, ModelMap model) throws Exception{
		TempVO result = tempService.selectTemp(tempVO);
		model.addAttribute("result", result);
		return "temp/TempSelect";
	}
	
	
	//임시데이터 목록 가져오기
	@RequestMapping(value = "/temp/selectList.do")
	public String selectList(TempVO tempVO,  HttpServletRequest request, ModelMap model) throws Exception{
		//1차
		
		List<EgovMap> resultList = tempService.selectTempList(tempVO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = tempService.selectTempListCnt(tempVO);
		model.addAttribute("totCnt", totCnt);
		
		/*
		//2차 - 페이징 작업 시
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(tempVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(tempVO.getPageUnit());
		paginationInfo.setPageSize(tempVO.getPageSize());

		tempVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		tempVO.setLastIndex(paginationInfo.getLastRecordIndex());
		tempVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<EgovMap> resultList = tempService.selectTempList(tempVO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = tempService.selectTempListCnt(tempVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		*/
		return "temp/TempSelectList";
	}
	
	//임시데이터 등록/수정
	@RequestMapping(value = "/temp/tempRegist.do")
	public String tempRegist(TempVO tempVO, HttpServletRequest request, ModelMap model) throws Exception{
		/* 수정할 때 오픈하기 */
		TempVO result = new TempVO();
		//egovframework.let.utl.fcc.service.EgovStringUtil
		if(!EgovStringUtil.isEmpty(tempVO.getTempId())) {
			//result = temp2Service.selectTemp(tempVO);
		}
		
		model.addAttribute("result", result);
		
		return "temp/TempRegist";
	}
	
	//임시데이터 등록하기
	@RequestMapping(value = "/temp/insert.do")
	public String insert(TempVO tempVO, HttpServletRequest request) throws Exception{
		//temp2Service.insertTemp(tempVO);
		
		return "forward:/temp/selectList.do";
	}
	
	//임시데이터 수정하기
	@RequestMapping(value = "/temp/update.do")
	public String update(TempVO tempVO, HttpServletRequest request) throws Exception{
		//temp2Service.updateTemp(tempVO);
		return "forward:/temp/selectList.do";
	}
	
	//임시데이터 삭제하기
	@RequestMapping(value = "/temp/delete.do")
	public String delete(TempVO tempVO, HttpServletRequest request) throws Exception{
		//temp2Service.deleteTemp(tempVO);
		return "forward:/temp/selectList.do";
	}
	
	//JSTL
	@RequestMapping(value = "/temp/jstl.do")
	public String jstl(TempVO tempVO, HttpServletRequest request, ModelMap model) throws Exception{
		
		return "/temp/Jstl";
	}
	
	//JSTL Import용
	@RequestMapping(value = "/temp/jstlImport.do")
	public String jstlImport(TempVO tempVO, HttpServletRequest request, ModelMap model) throws Exception{
		
		return "/temp/JstlImport";
	}
	
}