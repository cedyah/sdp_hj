package com.jebi.sdp.serviceImpl;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.service.CmmnService;

@Service
public class CmmnServiceImpl implements CmmnService{
	@Autowired
	private CmmnDao cmmnDao;
	
	@Override
	public List<?> selectList(String queryId, Object paramVO) throws Exception {
		return cmmnDao.selectList(queryId, paramVO);
	}

	@Override
	public Object select(String queryId, Object paramVO) throws Exception {
		return cmmnDao.select(queryId, paramVO);
	}

	@Override
	public int selectCnt(String queryId, Object paramVO) throws Exception {
		return cmmnDao.selectCnt(queryId, paramVO);
	}

	@Override
	public void insert(String queryId, Object paramVO) throws Exception {
		cmmnDao.insert(queryId, paramVO);
	}
	
	@Override
	public String insert_return(String queryId, Object paramVO) throws Exception {
		return (String) cmmnDao.insert_return(queryId, paramVO);
	}

	@Override
	public void update(String queryId, Object paramVO) throws Exception {
		cmmnDao.update(queryId, paramVO);
	}

	@Override
	public void delete(String queryId, Object paramVO) throws Exception {
		cmmnDao.delete(queryId, paramVO);
	}

	public void startTransaction() throws Exception {
		cmmnDao.startTransaction();
	}

	public void endTransaction() throws Exception {
		cmmnDao.endTransaction();
	}

	public void commit() throws Exception {
		cmmnDao.commit();
	}

	public void startBatch() throws Exception {
		cmmnDao.startBatch();
	}

	public void executeBatch() throws Exception {
		cmmnDao.executeBatch();
	}

	@Override
	public void excuteBatch() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
