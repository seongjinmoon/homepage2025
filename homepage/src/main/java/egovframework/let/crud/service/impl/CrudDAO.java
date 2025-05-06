package egovframework.let.crud.service.impl;
import egovframework.let.crud.service.CrudVO;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;


@Repository("crudDAO")
public class CrudDAO extends EgovAbstractMapper {

    //CRUD 목록 가져오기
	public List<EgovMap> selectCrudList(CrudVO vo) throws Exception {
  		return selectList("crudDAO.selectCrudList", vo);
  	}
	
	//CRUD 목록 수
    public int selectCrudListCnt(CrudVO vo) throws Exception {
    	return selectOne("crudDAO.selectCrudListCnt", vo);
    }
	
    //CRUD 가져오기
    public CrudVO selectCrud(CrudVO vo) throws Exception {
    	return selectOne("crudDAO.selectCrud", vo);
    }
    
	//CRUD 등록
    public void insertCrud(CrudVO vo) throws Exception {
    	insert("crudDAO.insertCrud", vo);
    }
	
    //CRUD 수정
    public void updateCrud(CrudVO vo) throws Exception {
    	update("crudDAO.updateCrud", vo);
    }
    
    //CRUD 삭제
    public void deleteCrud(CrudVO vo) throws Exception {
    	delete("crudDAO.deleteCrud", vo);
    }
	
    
    
    
}
