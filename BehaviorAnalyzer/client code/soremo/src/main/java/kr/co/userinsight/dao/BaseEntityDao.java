package kr.co.userinsight.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import kr.co.userinsight.dao.hql.Association;
import kr.co.userinsight.dao.hql.BaseRestrictions;
import kr.co.userinsight.model.entity.Entity;
import kr.co.userinsight.model.meta.NativeRow;
import kr.co.userinsight.tools.exception.DaoException;
import kr.co.userinsight.tools.exception.NativeQueryException;
import kr.co.userinsight.tools.vo.SysTable;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.type.Type;


/**
 * @author HORN DANETH
 *
 */
public interface BaseEntityDao {

    ConnectionProvider getConnectionProvider();

    SessionFactory getSessionFactory();

    Session getCurrentSession();

    Connection getConnection() throws SQLException;

    DatabaseMetaData getDatabaseMetaData() throws SQLException;

    String getUserName() throws SQLException;

    String getDatabaseName() throws SQLException;

    String getDriverName() throws SQLException;


    /**_______________________________________________________________________________________
     *
     * BLOCK [EntityDao]
     * _______________________________________________________________________________________
     */
    Query getNamedQuery(String queryName);

    <T extends Entity> List<Exception> saveOrUpdateBulk(List<T> entityLst);

    <T> List<Exception> deleteList(List<T> entityLst);

    <T> List<Exception> saveOrUpdateList(List<T> entityLst);

    <T extends Entity> void saveOrUpdate(T entity);

    <T extends Entity> void create(T entity);

    <T extends Entity> void update(T entity);

    <T extends Entity> void delete(Class<T> entityClass, Serializable id);

    <T extends Entity> void delete(T entity);

    <T extends Entity> T getByCode(Class<T> entityClass, String code);

    <T extends Entity> boolean checkByCodeExcept(Class<T> entityClass, String code, Long exceptId);

    <T extends Entity> boolean checkByFieldExcept(Class<T> entityClass, String fieldName, Object value, Long exceptId);

    <T extends Entity> T getByDesc(Class<T> entityClass, String desc);

    <T extends Entity> T getByField(Class<T> entityClass, String fieldName, Object value);

    <T extends Entity> T getByIdIfNotExistError(Class<T> entityClass, Serializable id) throws DaoException;

    <T extends Entity> T getById(Class<T> entityClass, Serializable id);

    void flush();

    void clear();

    <T extends Entity> void refresh(T entity);

    <T extends Entity> T merge(T entity);

    <T extends Entity> long count(BaseRestrictions<T> restrictions);

    <T extends Entity> long countByProperty(BaseRestrictions<T> restrictions, String property);

    <T extends Entity> Long[] getIds(BaseRestrictions<T> restrictions);

    <T extends Entity> List<T> list(Class<T> entityClass);

    <T extends Entity> List<T> list(Class<T> entityClass, Order order);

    <T extends Entity> List<T> list(BaseRestrictions<T> restrictions);

    <T extends Entity> List<T> listByPage(Class<T> entityClass, int pageNum, int pageSize, Order order);

    <T extends Entity> List<T> listByPage(BaseRestrictions<T> restrictions, int pageNum, int pageSize);

    <T extends Entity> List<T> listByIds(Class<T> entityClass, List<Long> idList) ;

    <T extends Entity> List<T> list(Class<T> entityClass, Criterion criterion);

    <T extends Entity> List<T> list(Class<T> entityClass, List<Criterion> criterions, Order order);

    <T extends Entity> List<T> list(Class<T> entityClass, List<Association> associations, List<Criterion> criterions, Integer firstResult, Integer maxResults, List<Order> orders);

    <T extends Entity> List<T> list(Class<T> entityClass, List<Association> associations, List<Criterion> criterions, Projection projection, Integer firstResult, Integer maxResults, List<Order> orders);

    <T extends Entity> List<T> list(Class<T> entityClass, boolean isDistinctRootEntity, String associationPath, List<Association> associations, List<Criterion> criterions, List<Projection> projections, Integer firstResult, Integer maxResults, List<Order> orders);

    <T extends Entity> T getFirst(BaseRestrictions<T> restrictions);

    <T extends Entity> T getUnique(BaseRestrictions<T> restrictions);

    int deleteViaHQL(String queryString);

    int deleteViaHQL(String queryString, Object value, Type type);

    int deleteViaHQL(String queryString, Object[] values, Type[] types);

    int updateViaHQL(String queryString);

    int updateViaHQL(String queryString, Object[] values, Type[] types);

    int executeUpdateViaHQL(String queryString, Object[] values, Type[] types);

    <T extends Entity> Criteria createCriteria(Class<T> entityClass);

    Query createQuery(String queryString);

    SQLQuery createSqlQuery(String queryString);

    List<SysTable> getAllTablesName();

    List<NativeRow> executeSQLNativeQuery(final String sql) throws NativeQueryException;
}
