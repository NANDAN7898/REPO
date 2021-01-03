package com.schoolmgmt.dao;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.schoolmgmt.util.ApplicationConstants;

public class DBManager
{
	private static final Logger			OUT		= LoggerFactory.getLogger(DBManager.class.getName());

	private SqlSessionFactory			sqlSessionFactory;

	private volatile static DBManager	instance;

	private String						databaseType;

	public static final String			MYSQL	= "MYSQL";

	/**
	 * 
	 */
	public DBManager()
	{
		try
		{
			databaseType = MYSQL;
			String resource = "myBatisConfig.xml";
			OUT.info("Database Type: {}", databaseType);
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
	}

	/**
	 * @return
	 */
	public static DBManager getInstance()
	{
		if (instance == null)
		{
			synchronized (DBManager.class)
			{
				if (instance == null)
				{
					instance = new DBManager();
				}
			}
		}
		return instance;
	}

	/**
	 * * To add a new IModel Object in DB, returns int with ID of newly inserted
	 * row
	 * 
	 * @param modelObject
	 * @param queryName
	 * @return
	 * @throws SQLException
	 */
	public int insert(String queryName, IModel modelObject) throws Exception
	{
		Integer pkId = null;
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			pkId = sqlSession.insert(queryName, modelObject);
			sqlSession.commit();
		}
		catch (Exception e)
		{
			if (sqlSession != null)
			{
				sqlSession.rollback();
			}
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return pkId;
	}

	/**
	 * @param modelObject
	 * @param queryName
	 * @throws SQLException
	 */
	public Integer update(String queryName, IModel modelObject) throws Exception
	{
		SqlSession sqlSession = null;
		Integer result = 0;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			result = sqlSession.update(queryName, modelObject);
			sqlSession.commit();
		}
		catch (Exception e)
		{
			if (sqlSession != null)
			{
				sqlSession.rollback();
			}
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return result;
	}
	
	/**
	 * @param modelObject
	 * @param queryName
	 * @throws SQLException
	 */
	public Integer update(String queryName, Object object) throws Exception
	{
		SqlSession sqlSession = null;
		Integer result = 0;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			result = sqlSession.update(queryName, object);
			sqlSession.commit();
		}
		catch (Exception e)
		{
			if (sqlSession != null)
			{
				sqlSession.rollback();
			}
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return result;
	}

	/**
	 * @param modelObject
	 * @param queryName
	 * @throws SQLException
	 */
	public int deleteObjectById(String queryName, int id) throws Exception
	{
		SqlSession sqlSession = null;
		int isDelete = 0;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			isDelete = sqlSession.delete(queryName, id);
			sqlSession.commit();
		}
		catch (Exception e)
		{
			if (sqlSession != null)
			{
				sqlSession.rollback();
			}
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
		return isDelete;
	}

	/**
	 * @param modelObject
	 * @param queryName
	 * @return
	 * @throws SQLException
	 */
	public Integer getUniqueCount(String queryName, IModel modelObject) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			return (Integer) sqlSession.selectOne(queryName, modelObject);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	/**
	 * @param queryName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer getUniqueCount(String queryName, int id) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			return (Integer) sqlSession.selectOne(queryName, id);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	/**
	 * @param queryName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IModel getResultAsObjectById(String queryName, int id) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			return (IModel) sqlSession.selectOne(queryName, id);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	/**
	 * @param queryName
	 * @param parametersObject
	 * @return
	 * @throws Exception
	 */
	public IModel getResultAsObject(String queryName, IModel parametersObject) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			return (IModel) sqlSession.selectOne(queryName, parametersObject);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	/**
	 * @param queryName
	 * @param parametersObject
	 * @return
	 * @throws Exception
	 */
	public Object getResultAsObject(String queryName, Object parametersObject) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			return sqlSession.selectOne(queryName, parametersObject);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	/**
	 * @param <T>
	 * @param queryName
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getResultAsList(String queryName, T parameterObject) throws Exception
	{
		return getResultAsList(queryName, parameterObject, 0, 0);
	}

	/**
	 * @param <T>
	 * @param queryName
	 * @param parameterObject
	 * @param skipResults
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getResultAsList(String queryName, T parameterObject, int skipResults, int maxResults) throws Exception
	{
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			if (skipResults < 1)
				skipResults = RowBounds.NO_ROW_OFFSET;
			if (maxResults < 1)
				maxResults = RowBounds.NO_ROW_LIMIT;

			RowBounds bounds = new RowBounds(skipResults, maxResults);

			return sqlSession.selectList(queryName, parameterObject, bounds);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
			OUT.debug("Query: {} executed in {} ms", queryName, (System.currentTimeMillis() - startTime));
		}
	}

	/**
	 * @param queryName
	 * @param parametersObject
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> getResultList(String queryName, Object parameterObject) throws Exception
	{
		return getResultList(queryName, parameterObject, 0, 0);
	}

	/**
	 * @param queryName
	 * @param parameterObject
	 * @param skipResults
	 * @param maxResults
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> getResultList(String queryName, Object parameterObject, int skipResults, int maxResults) throws Exception
	{
		List<T> resultList = new ArrayList<T>();
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			if (skipResults < 1)
				skipResults = RowBounds.NO_ROW_OFFSET;
			if (maxResults < 1)
				maxResults = RowBounds.NO_ROW_LIMIT;

			RowBounds bounds = new RowBounds(skipResults, maxResults);
			resultList = sqlSession.selectList(queryName, parameterObject, bounds);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
		OUT.debug("Ibatis Query : {}  executed in {} ms. ", queryName, (System.currentTimeMillis() - startTime));
		return resultList;
	}

	/**
	 * @return
	 */
	public SqlSession getSession()
	{
		return sqlSessionFactory.openSession();
	}

	/**
	 * @return
	 */
	public SqlSession getBatchSession()
	{
		return sqlSessionFactory.openSession(ExecutorType.BATCH);
	}

	/**
	 * @param <T>
	 * @param queryName
	 * @param parameterObject
	 * @param handler
	 * @throws Exception
	 */
	public <T> void getResultByHandler(String queryName, T parameterObject, ResultHandler handler) throws Exception
	{
		getResultByHandler(queryName, parameterObject, handler, 0, 0);
	}

	/**
	 * @param <T>
	 * @param queryName
	 * @param parameterObject
	 * @param handler
	 * @param skipResults
	 * @param maxResults
	 * @throws Exception
	 */
	public <T> void getResultByHandler(String queryName, T parameterObject, ResultHandler handler, int skipResults, int maxResults) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			if (skipResults < 1)
				skipResults = RowBounds.NO_ROW_OFFSET;
			if (maxResults < 1)
				maxResults = RowBounds.NO_ROW_LIMIT;

			RowBounds bounds = new RowBounds(skipResults, maxResults);
			sqlSession.select(queryName, parameterObject, bounds, handler);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	/**
	 * @param modelObject
	 * @param queryName
	 * @throws SQLException
	 */
	public void deleteObjectByModel(String queryName, IModel modelObject) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			sqlSession.delete(queryName, modelObject);
			sqlSession.commit();
		}
		catch (Exception e)
		{
			if (sqlSession != null)
				sqlSession.rollback();
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	/**
	 * @param queryName
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(String queryName, Object obj) throws Exception
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			return (Integer) sqlSession.selectOne(queryName, obj);
		}
		catch (RuntimeException ex)
		{
			throw ex;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}

	public String getDatabaseType()
	{
		return databaseType;
	}

	public void setDatabaseType(String databaseType)
	{
		this.databaseType = databaseType;
	}

	public SqlSessionFactory getSqlSessionFactory()
	{
		return sqlSessionFactory;
	}

	public boolean isServerReachable()
	{
		SqlSession sqlSession = null;
		try
		{
			sqlSession = sqlSessionFactory.openSession();
			OUT.info("Database connectivity success");
			return true;
		}
		catch (Exception e)
		{
			OUT.info("Database connectivity failed");
			OUT.error(ApplicationConstants.EXCEPTION, e);
			return false;
		}
		finally
		{
			if (sqlSession != null)
			{
				sqlSession.close();
			}
		}
	}
}