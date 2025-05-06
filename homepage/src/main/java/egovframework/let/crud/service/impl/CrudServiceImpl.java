package egovframework.let.crud.service.impl;

import egovframework.let.crud.service.CrudService;
import egovframework.let.crud.service.CrudVO;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;


@Service("crudService")
public class CrudServiceImpl extends EgovAbstractServiceImpl implements CrudService {

    @Resource(name="crudDAO")
	private CrudDAO crudDAO;
    
    @Resource(name = "egovCrudIdGnrService")
    private EgovIdGnrService idgenService;
    
    
    //CRUD 가져오기
	public CrudVO selectCrud(CrudVO vo) throws Exception {
		return crudDAO.selectCrud(vo);
	}
	
	//CRUD 등록하기
	public String insertCrud(CrudVO vo) throws Exception {
		String id = idgenService.getNextStringId();
		vo.setCrudId(id);
		crudDAO.insertCrud(vo);
		
		return id;
	}

	//CRUD 목록 가져오기
	public List<EgovMap> selectCrudList(CrudVO vo) throws Exception {
		return crudDAO.selectCrudList(vo);
	}

	//CRUD 목록 수
	public int selectCrudListCnt(CrudVO vo) throws Exception {
		return crudDAO.selectCrudListCnt(vo);
	}

	//CRUD 수정하기
	public void updateCrud(CrudVO vo) throws Exception {
		crudDAO.updateCrud(vo);
	}

	//CRUD 삭제하기
	public void deleteCrud(CrudVO vo) throws Exception {
		crudDAO.deleteCrud(vo);
	}
	
}
