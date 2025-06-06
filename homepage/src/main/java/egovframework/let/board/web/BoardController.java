package egovframework.let.board.web;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
public class BoardController {
	
	@Resource(name = "boardService")
    private BoardService boardService;
	
	@Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

	@Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    /*
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    */
	//게시물 목록 가져오기
	@RequestMapping(value = "/board/selectList.do")
	public String selectList(@ModelAttribute("searchVO") BoardVO searchVO,  HttpServletRequest request, ModelMap model) throws Exception{
		//공지 게시 글
		searchVO.setNoticeAt("Y");
		List<EgovMap> noticeResultList = boardService.selectBoardList(searchVO);
		model.addAttribute("noticeResultList", noticeResultList);
		/*
		//이미지게시판일 경우
		if("IMAGE".equals(searchVO.getBoardType())) {
			searchVO.setPageUnit(propertyService.getInt("imagePageUnit"));
			searchVO.setPageSize(propertyService.getInt("imagePageSize"));
		}
		*/
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		//일반 게시 글
		searchVO.setNoticeAt("N");
		List<EgovMap> resultList = boardService.selectBoardList(searchVO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = boardService.selectBoardListCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "board/BoardSelectList";
	}
		
	//게시물 가져오기
	@RequestMapping(value = "/board/select.do")
	public String select(@ModelAttribute("searchVO") BoardVO searchVO, HttpServletRequest request, ModelMap model) throws Exception{
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user);
		
		//조회수 업데이트
		/*
		boardService.updateViewCnt(searchVO);
		String a = null;
		if(a.equals("aa")) a = "";
		*/
		BoardVO result = boardService.selectBoard(searchVO);
		//비밀 글여부 체크
		if("Y".equals(result.getOthbcAt())) {
			//본인 및 관리자만 허용
			if(user == null || user.getId() == null || (!user.getId().equals(result.getFrstRegisterId()) && !"admin".equals(user.getId()))) {
				model.addAttribute("message", "작성자 본인만 확인 가능합니다.");
				return "forward:/board/selectList.do";
			}
		}
		model.addAttribute("result", result);
		return "board/BoardSelect";
	}

	//게시물 등록/수정
	@RequestMapping(value = "/board/boardRegist.do")
	public String boardRegist(@ModelAttribute("searchVO") BoardVO boardVO, HttpServletRequest request, ModelMap model) throws Exception{
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	    if(user == null || user.getId() == null){
	    	model.addAttribute("message", "로그인 후 사용가능합니다.");
	    	return "forward:/board/selectList.do";
		}else {
			model.addAttribute("USER_INFO", user);
		}
	    //수정 시 진행
		BoardVO result = new BoardVO();
		if(!EgovStringUtil.isEmpty(boardVO.getBoardId())) {
			result = boardService.selectBoard(boardVO);
			//본인 및 관리자만 허용
			if(!user.getId().equals(result.getFrstRegisterId()) && !"admin".equals(user.getId())){
				model.addAttribute("message", "작성자 본인만 확인 가능합니다.");
				return "forward:/board/selectList.do";
			}
		}
		model.addAttribute("result", result);
		
		request.getSession().removeAttribute("sessionBoard");
		
		return "board/BoardRegist";
	}
	
	//게시물 등록하기
	@RequestMapping(value = "/board/insert.do")
	public String insert(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO searchVO, HttpServletRequest request, ModelMap model) throws Exception{
		//이중 서브밋 방지 체크
		if(request.getSession().getAttribute("sessionBoard") != null){
			return "forward:/board/selectList.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null){
	    	model.addAttribute("message", "로그인 후 사용가능합니다.");
	    	return "forward:/board/selectList.do";
		}
	    
		List<FileVO> result = null;
	    String atchFileId = "";
	    
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if(!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "BOARD_", 0, "", "board.fileStorePath");
			atchFileId = fileMngService.insertFileInfs(result);
	    }
	    searchVO.setAtchFileId(atchFileId);
		
		//사용자IP가져오기
	    searchVO.setCreatIp(request.getRemoteAddr());
	    searchVO.setUserId(user.getId());
	    
		boardService.insertBoard(searchVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionBoard", searchVO);
		return "forward:/board/selectList.do";
	}
	
	//게시물 수정하기
	@RequestMapping(value = "/board/update.do")
	public String update(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO searchVO, HttpServletRequest request, ModelMap model) throws Exception{
		//이중 서브밋 방지
		if(request.getSession().getAttribute("sessionBoard") != null){
			return "forward:/board/selectList.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null){
	    	model.addAttribute("message", "로그인 후 사용가능합니다.");
	    	return "forward:/board/selectList.do";
		}else if("admin".equals(user.getId())){
			searchVO.setMngAt("Y");
		}
	    
		String atchFileId = searchVO.getAtchFileId();
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if(!files.isEmpty()) {
			if(EgovStringUtil.isEmpty(atchFileId)) {
			    List<FileVO> result = fileUtil.parseFileInf(files, "BOARD_", 0, "", "board.fileStorePath");
			    atchFileId = fileMngService.insertFileInfs(result);
			    searchVO.setAtchFileId(atchFileId);
			}else {
			    FileVO fvo = new FileVO();
			    fvo.setAtchFileId(atchFileId);
			    int cnt = fileMngService.getMaxFileSN(fvo);
			    List<FileVO> _result = fileUtil.parseFileInf(files, "BOARD_", cnt, atchFileId, "board.fileStorePath");
			    fileMngService.updateFileInfs(_result);
			}
	    }
	    
	    searchVO.setUserId(user.getId());
	    
		boardService.updateBoard(searchVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionBoard", searchVO);
		return "forward:/board/selectList.do";
	}
	
	//게시물 삭제하기
	@RequestMapping(value = "/board/delete.do")
	public String delete(@ModelAttribute("searchVO") BoardVO searchVO, HttpServletRequest request, ModelMap model) throws Exception{
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null){
	    	model.addAttribute("message", "로그인 후 사용가능합니다.");
	    	return "forward:/board/selectList.do";
		}else if("admin".equals(user.getId())){
			searchVO.setMngAt("Y");
		}
	    
	    searchVO.setUserId(user.getId());
	    
		boardService.deleteBoard(searchVO);
		
		return "forward:/board/selectList.do";
	}
	
}