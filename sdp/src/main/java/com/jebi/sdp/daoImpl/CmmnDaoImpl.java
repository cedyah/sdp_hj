package com.jebi.sdp.daoImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapClasspathEntityResolver;
import com.jebi.sdp.dao.CmmnDao;

@Repository
public class CmmnDaoImpl implements CmmnDao {
	@Resource(name="sqlMapClient")
	private SqlMapClient sqlMapClient;

	@Override
	public List<?> selectList(String queryId, Object paramVO) throws Exception {
		return sqlMapClient.queryForList(queryId, paramVO);
	}

	@Override
	public Object select(String queryId, Object paramVO) throws Exception {
		return sqlMapClient.queryForObject(queryId, paramVO);
	}

	@Override
	public int selectCnt(String queryId, Object paramVO) throws Exception {
		return (Integer)sqlMapClient.queryForObject(queryId, paramVO);
	}

	@Override
	public void insert(String queryId, Object paramVO) throws Exception {
		sqlMapClient.insert(queryId, paramVO);
	}
	
	@Override
	public String insert_return(String queryId, Object paramVO) throws Exception {
		return (String) sqlMapClient.insert(queryId, paramVO);
	}

	@Override
	public void update(String queryId, Object paramVO) throws Exception {
		sqlMapClient.update(queryId, paramVO);
	}

	@Override
	public void delete(String queryId, Object paramVO) throws Exception {
		sqlMapClient.delete(queryId, paramVO);
	}
	
	@Override
	public void startTransaction() throws Exception {
		sqlMapClient.startTransaction();
		sqlMapClient.getCurrentConnection().setAutoCommit(false);
	}

	@Override
	public void endTransaction() throws Exception {
		sqlMapClient.endTransaction();
	}

	@Override
	public void commit() throws Exception {
		sqlMapClient.getCurrentConnection().commit();
	}

	@Override
	public void startBatch() throws Exception {
		sqlMapClient.startBatch();
	}

	@Override
	public void executeBatch() throws Exception {
		sqlMapClient.executeBatch();
	}

	@Override
	public void setAutoCommit(boolean value) throws Exception {
		sqlMapClient.getCurrentConnection().setAutoCommit(value);
	}
}
