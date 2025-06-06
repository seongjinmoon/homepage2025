package egovframework.let.board.service;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;

public interface BoardService {
	
	//게시물 목록 가져오기
	public List<EgovMap> selectBoardList(BoardVO vo) throws Exception;
	
	//게시물 목록 수
	public int selectBoardListCnt(BoardVO vo) throws Exception;
		
	//게시물 상세정보
	public BoardVO selectBoard(BoardVO vo) throws Exception;

	//게시물 등록하기
	public String insertBoard(BoardVO vo) throws Exception;
	
	//게시물 수정하기
	public void updateBoard(BoardVO vo) throws Exception;
	
	//게시물 삭제하기
	public void deleteBoard(BoardVO vo) throws Exception;
	
	//조회수 업데이트(테스트용)
	public void updateViewCnt(BoardVO vo) throws Exception;
	
}