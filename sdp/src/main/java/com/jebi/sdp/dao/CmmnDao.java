package com.jebi.sdp.dao;

import java.util.List;

public interface CmmnDao {
	public List<?> selectList(String queryId, Object paramVO) throws Exception;
	
	public Object select(String queryId, Object paramVO) throws Exception;
	
	public int selectCnt(String queryId, Object paramVO) throws Exception;
	
	public void insert(String queryId, Object paramVO) throws Exception;
	
	public String insert_return(String queryId, Object paramVO) throws Exception;
	
	public void update(String queryId, Object paramVO) throws Exception;
	
	public void delete(String queryId, Object paramVO) throws Exception;
	
	public void startTransaction() throws Exception;
	
	public void endTransaction() throws Exception;
	
	public void commit() throws Exception;
	
	public void startBatch() throws Exception;
	
	public void executeBatch() throws Exception;
	
	public void setAutoCommit(boolean value) throws Exception;
}
