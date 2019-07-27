package kr.co.userinsight.dao.impl;

import kr.co.userinsight.dao.BaseEntityDao;
import kr.co.userinsight.dao.hql.Association;
import kr.co.userinsight.dao.hql.BaseRestrictions;
import kr.co.userinsight.model.entity.*;
import kr.co.userinsight.model.meta.FieldType;
import kr.co.userinsight.model.meta.NativeColumn;
import kr.co.userinsight.model.meta.NativeRow;
import kr.co.userinsight.tools.NativeColumnUtils;
import kr.co.userinsight.tools.exception.DaoException;
import kr.co.userinsight.tools.exception.NativeQueryException;
import kr.co.userinsight.tools.vo.SysColumn;
import kr.co.userinsight.tools.vo.SysTable;
import org.hibernate.*;
import org.hibernate.cfg.Environment;
import org.hibernate.criterion.*;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.Work;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.io.Serializable;
import java.sql.*;
import java.util.*;

/**
 * @author HORN DANETH
 *
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
public abstract class BaseEntityDaoImpl implements BaseEntityDao {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Properties envProperties = Environment.getProperties();
    public static int NB_MAX_ERRORS = 1;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     *
     */
    public BaseEntityDaoImpl() {
        //logger.info("Instantiate EntityDao.");
    }

    /**
     *
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }


    @Override
    public Connection getConnection() throws SQLException {
        // Way3 - using Session Impl
//		SessionImpl sessionImpl = (SessionImpl) session;
//		connection = sessionImpl.connection();
//		// do your work using connection
//
//		// Way4 - using connection provider
//		SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session.getSessionFactory();
//		ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
//		try {
//		connection = connectionProvider.getConnection();
//		// do your work using connection
//		} catch (SQLException e) {
//		e.printStackTrace();
//		} - See more at: http://myjourneyonjava.blogspot.com/2014/12/different-ways-to-get-connection-object.html#sthash.xq0vMn3n.dpuf
//		return getConnectionProvider().getConnection();

        // Way3 - using Session Impl
        SessionImpl sessionImpl = (SessionImpl) getCurrentSession();
        Connection connection = sessionImpl.connection();
        return connection;

    }


    @Override
    public ConnectionProvider getConnectionProvider() {
        return null;
//		return ((SessionFactoryImpl) getSessionFactory()).getConnectionProvider();
        // return envProperties.get(Environment.CONNECTION_PROVIDER);
    }


    @Override
    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        return getConnection().getMetaData();
    }


    @Override
    public String getUserName() throws SQLException {
        return getDatabaseMetaData().getUserName();
    }


    @Override
    public String getDatabaseName() throws SQLException {
        return getDatabaseMetaData().getDatabaseProductName();
    }


    @Override
    public String getDriverName() throws SQLException {
        return getDatabaseMetaData().getDriverName();
    }


    @Override
    public Query getNamedQuery(String queryName) {
        return getCurrentSession().getNamedQuery(queryName);
    }

    /**
     *
     * @param entity
     */
    private <T extends Entity> void setStatusToActiveIfNull(T entity) {
        if (entity instanceof EntityStatusRecordAware) {
            EntityStatusRecordAware entSta = (EntityStatusRecordAware) entity;
            if (entSta != null && entSta.getStatusRecord() == null) {
                entSta.setStatusRecord(EStatusRecord.ACTIV);
            }
        }
    }



    @Override
    public <T extends Entity> void create(T entity) {
        if (entity instanceof EntityA) {
            ((EntityA) entity).checkForCreation();
            ((EntityA) entity).fillSysBlock();
        }

        setStatusToActiveIfNull(entity);
        getCurrentSession().save(entity);
    }


    @Override
    public <T extends Entity> void update(T entity) {
        if (entity instanceof EntityA) {
            if (((EntityA) entity).isRecycledBin()) {
                ((EntityA) entity).checkBeforeThrowIntoRecycledBin();
            } else {
                ((EntityA) entity).checkForUpdate();
                ((EntityA) entity).fillSysBlock();
            }
        }

        setStatusToActiveIfNull(entity);
        getCurrentSession().update(entity);
    }


    @Override
    public <T extends Entity> T merge(T entity) {
        if (entity instanceof EntityA) {
            if (((EntityA) entity).isRecycledBin()) {
                ((EntityA) entity).checkBeforeThrowIntoRecycledBin();
            } else {
                ((EntityA) entity).checkForUpdate();
            }
        }

        setStatusToActiveIfNull(entity);
        return (T) getCurrentSession().merge(entity);
    }


    @Override
    public <T extends Entity> void saveOrUpdate(T entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            create(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public <T extends Entity> List<Exception> saveOrUpdateBulk(List<T> entityLst) {
        return saveOrUpdateList(entityLst);
    }

    @Override
    public <T> List<Exception> saveOrUpdateList(List<T> entityLst) {
        logger.debug("----start saveOrUpdateBulk------ nb entities:" + entityLst.size());
        int i = 0;
        Entity entity = null;
        int nbErrors = 0;
        List<Exception> lstExceptions = new LinkedList<Exception>();

        for (; i < entityLst.size() && nbErrors < NB_MAX_ERRORS; i++) {
            try {
                entity = (Entity) entityLst.get(i);
                saveOrUpdate(entity);
                lstExceptions.add(null);
            } catch (Exception e) {
                nbErrors++;
                logger.error("---- Error [" + nbErrors + "] ------");

                String errMsg = "Error on entity [" + i + "]";
                logger.error(errMsg);
                logger.error(errMsg, e);
                lstExceptions.add(new Exception(errMsg, e));
            }
        }

        return lstExceptions;
    }

    @Override
    public <T> List<Exception> deleteList(List<T> entityLst) {
        logger.debug("----start deleteList------ nb entities:" + entityLst.size());
        int i = 0;
        Entity entity = null;
        int nbErrors = 0;
        List<Exception> lstExceptions = new LinkedList<Exception>();

        for (; i < entityLst.size() && nbErrors < NB_MAX_ERRORS; i++) {
            try {
                entity = (Entity) entityLst.get(i);
                delete(entity);
                lstExceptions.add(null);
            } catch (Exception e) {
                nbErrors++;
                logger.error("---- Error [" + nbErrors + "] ------");

                String errMsg = "Error on entity [" + i + "]";
                logger.error(errMsg);
                logger.error(e.getMessage());
                lstExceptions.add(new Exception(errMsg, e));
            }
        }
        i = 0;
        for (; i < entityLst.size(); i++) {
            entityLst.remove(i);
            i = 0;
        }

        return lstExceptions;
    }


    @Override
    public <T extends Entity> void delete(Class<T> entityClass, Serializable id)  {
        T entity = getById(entityClass, id);
        delete(entity);
    }


    @Override
    public <T extends Entity> void delete(T entity) {
        if (entity instanceof EntityA) {
            ((EntityA) entity).checkForDeletion();
        }
        getCurrentSession().delete(entity);
    }


    @Override
    public void flush() {
        getCurrentSession().flush();
    }


    public void clear() {
        getCurrentSession().clear();
    }


    @Override
    public <T extends Entity> T getByIdIfNotExistError(Class<T> entityClass, Serializable id) throws DaoException {
        // load: if object not found throw exception
        // get: if object not found return null
        T entity = (T) getCurrentSession().load(entityClass, id);
        if (entity == null) {
            throw new DaoException("loadIfNotExistError: "
                    + entityClass.getName() + " [" + id
                    + "] does not exist.");
        }
        return entity;
    }

    @Override
    public <T extends Entity> T getByCode(Class<T> entityClass, String code) {
        return getByField(entityClass, EntityRefA.CODE, code);
    }

    @Override
    public <T extends Entity> boolean checkByCodeExcept(Class<T> entityClass, String code, Long exceptId) {
        Criterion codeCri = Restrictions.eq("code", code);
        Criterion mainCri = null;
        if (exceptId != null) {
            Criterion idCri = Restrictions.not(Restrictions.eq("id", exceptId));
            mainCri = Restrictions.and(idCri, codeCri);
        }
        else {
            mainCri = codeCri;
        }
        List<T> entities = list(entityClass, mainCri);
        if (entities != null && entities.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Entity> boolean checkByFieldExcept(Class<T> entityClass, String fieldName, Object value, Long exceptId) {
        Criterion codeCri = Restrictions.eq(fieldName, value);
        Criterion mainCri = null;
        if (exceptId != null) {
            Criterion idCri = Restrictions.not(Restrictions.eq("id", exceptId));
            mainCri = Restrictions.and(idCri, codeCri);
        }
        else {
            mainCri = codeCri;
        }
        List<T> entities = list(entityClass, mainCri);
        if (entities != null && entities.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public <T extends Entity> T getByDesc(Class<T> entityClass, String desc) {
        return getByField(entityClass, "desc", desc);
    }

    @Override
    public <T extends Entity> T getByField(Class<T> entityClass, String fieldName, Object value) {
        Criteria criteria = createCriteria(entityClass);
        criteria.add(Restrictions.eq(fieldName, value));
        T entity = (T) criteria.uniqueResult();
        return entity;
    }


    @Override
    public <T extends Entity> T getById(Class<T> entityClass, Serializable id) {
        // load: if object not found throw exception
        // get: if object not found return null
        return (T) getCurrentSession().get(entityClass, id);
    }


    @Override
    public int deleteViaHQL(String queryString) {
        Object[] values = new Object[] {};
        Type[] types = new Type[] {};
        return deleteViaHQL(queryString, values, types);
    }


    @Override
    public int deleteViaHQL(String queryString, Object value, Type type) {
        Object[] values = new Object[] { value };
        Type[] types = new Type[] { type };
        return deleteViaHQL(queryString, values, types);
    }


    @Override
    public int deleteViaHQL(String queryString, Object[] values, Type[] types) {
        return executeUpdateViaHQL(queryString, values, types);
    }


    @Override
    public int updateViaHQL(String queryString) {
        return updateViaHQL(queryString, null, null);
    }


    @Override
    public int updateViaHQL(String queryString, Object[] values, Type[] types)
    {
        return executeUpdateViaHQL(queryString, values, types);
    }



    @Override
    public int executeUpdateViaHQL(String queryString, Object[] values, Type[] types) {
        Query q = getCurrentSession().createQuery(queryString);
        if (values != null && types != null) {
            q.setParameters(values, types);
        }
        int result = q.executeUpdate();
        return result;
    }



    @Override
    public <T extends Entity> List<T> list(Class<T> entityClass) {
        return createCriteria(entityClass).list();
    }


    @Override
    public <T extends Entity> List<T> list(Class<T> entityClass, Order order) {
        return createCriteria(entityClass).addOrder(order).list();
    }

    @Override
    public <T extends Entity> List<T> listByPage(Class<T> entityClass, int pageNum, int pageSize, Order order) {
        BaseRestrictions<T> restrictions = new BaseRestrictions<T>(entityClass);
        if (order != null) {
            restrictions.addOrder(order);
        }
        return listByPage(restrictions, pageNum, pageSize);
    }

    @Override
    public <T extends Entity> List<T> listByPage(BaseRestrictions<T> restrictions, int pageNum, int pageSize) {
        restrictions.setFirstResult((pageNum - 1) * pageSize);
        restrictions.setMaxResults(pageSize);
        return list(restrictions);
    }

    @Override
    public <T extends Entity> List<T> list(BaseRestrictions<T> restrictions) {
        restrictions.internalPreBuildSpecificCriteria();
        restrictions.internalPreBuildCommunCriteria();

        List<T> lst = list(
                restrictions.getEntityClass(),
                restrictions.isDistinctRootEntity(),
                null,
                restrictions.getAssociations(),
                restrictions.getCriterions(),
                restrictions.getProjections(),
                restrictions.getFirstResult(),
                restrictions.getMaxResults(),
                restrictions.getOrders());
        return lst;
    }

    @Override
    public <T extends Entity> T getFirst(BaseRestrictions<T> restrictions) {
        restrictions.setMaxResults(1);
        final List<T> lst = list(restrictions);
        if (lst == null || lst.size() == 0) {
            return null;
        }
        return lst.get(0);
    }

    @Override
    public <T extends Entity> T getUnique(BaseRestrictions<T> restrictions) {
        final List<T> lst = list(restrictions);
        if (lst == null || lst.size() == 0) {
            return null;
        }
        if (lst.size() > 1) {
            throw new NonUniqueResultException("Found more than one result [" + lst.size() + "] of Object " + restrictions.getEntityClass().getCanonicalName() + "");
        }
        return lst.get(0);
    }

    @Override
    public <T extends Entity> long count(final BaseRestrictions<T> restrictions) {
        return countByProperty(restrictions, "id");
    }

    @Override
    public <T extends Entity> long countByProperty(BaseRestrictions<T> restrictions, String property) {
        restrictions.internalPreBuildSpecificCriteria();
        restrictions.internalPreBuildCommunCriteria();
        List result = list(
                restrictions.getEntityClass(),
                restrictions.getAssociations(),
                restrictions.getCriterions(),
                Projections.countDistinct(property),
                null,
                null,
                null);
        if (!result.isEmpty()) {
            return (Long) result.get(0);
        }
        return 0;
    }

    @Override
    public <T extends Entity> Long[] getIds(BaseRestrictions<T> restrictions) {
        restrictions.internalPreBuildSpecificCriteria();
        restrictions.internalPreBuildCommunCriteria();
        List<T> result = list(
                restrictions.getEntityClass(),
                restrictions.getAssociations(),
                restrictions.getCriterions(),
                Projections.distinct(Projections.property("id")),
                restrictions.getFirstResult(),
                restrictions.getMaxResults(),
                restrictions.getOrders());

        return result.toArray(new Long[0]);
    }


    @Override
    public <T extends Entity> List<T> listByIds(Class<T> entityClass, List<Long> idList) {
        BaseRestrictions<T> restriction = new BaseRestrictions<T>(entityClass);
        restriction.addCriterion(Restrictions.in("id", idList));
        return list(restriction);
    }



    @Override
    public <T extends Entity> List<T> list(Class<T> entityClass, Criterion criterion) {
        return list(entityClass, Arrays.asList(criterion), null);
    }


    @Override
    public <T extends Entity> List<T> list(Class<T> entityClass, List<Criterion> criterions, Order order) {
        return list(entityClass, (List<Association>) null, criterions, null, null, null, Arrays.asList(order));
    }



    @Override
    public <T extends Entity> List<T> list(Class<T> entityClass, List<Association> associations, List<Criterion> criterions, Integer firstResult, Integer maxResults, List<Order> orders){
        return list(entityClass, associations, criterions, null, firstResult, maxResults, orders);
    }



    @Override
    public <T extends Entity> List<T> list(Class<T> entityClass, List<Association> associations, List<Criterion> criterions, Projection projection, Integer firstResult, Integer maxResults, List<Order> orders)  {
        return list(entityClass, false, null, associations, criterions, Arrays.asList(projection), firstResult, maxResults, orders);
    }

    @Override
    public <T extends Entity> List<T> list(Class<T> entityClass, boolean isDistinctRootEntity, String associationPath, List<Association> associations, List<Criterion> criterions, List<Projection> projections, Integer firstResult, Integer maxResults, List<Order> orders)  {

        Criteria criteria = createCriteria(entityClass);
        if (isDistinctRootEntity) {
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        }
        if (associations != null) {
            for (Association association : associations) {
                if (association != null) {
                    criteria.createAlias(
                            association.getAssociationPath(),
                            association.getAlias(),
                            association.getJoinType(),
                            association.getWithClause());
                }
            }
        }

        if (criterions != null) {
            for (Criterion criterion : criterions) {
                if (criterion != null) {
                    criteria = criteria.add(criterion);
                }
            }
        }

        if (projections != null) {
            ProjectionList projList = Projections.projectionList();
            for (Projection proj : projections) {
                if (proj != null) {
                    projList.add(proj);
                }
            }
            if (projList != null && projList.getLength() > 0) {
                criteria.setProjection(projList);
            }
        }


        if (orders != null) {
            for (Order order : orders) {
                if (order != null) {
                    criteria = criteria.addOrder(order);
                }
            }
        }


        if (firstResult != null) {
            criteria.setFirstResult(firstResult);
        }

        if (maxResults != null) {
            criteria.setMaxResults(maxResults);
        }


        return criteria.list();

    }


    @Override
    public <T extends Entity> void refresh(T entity) {
        getCurrentSession().refresh(entity);
    }


    @Override
    public <T extends Entity> Criteria createCriteria(Class<T> entityClass) {
        return getCurrentSession().createCriteria(entityClass);
    }


    @Override
    public Query createQuery(String queryString) {
        return getCurrentSession().createQuery(queryString);
    }


    @Override
    public SQLQuery createSqlQuery(String queryString) {
        return getCurrentSession().createSQLQuery(queryString);
    }

    /**
     * _______________________________________________________________________________________
     *
     * BLOCK [CommonDao]
     * _______________________________________________________________________________________
     */




    @Override
    public List<SysTable> getAllTablesName() {
        List<SysTable> sysTableList = new ArrayList<SysTable>();
        Map classMetadata = getSessionFactory().getAllClassMetadata();
        for (Iterator iter = classMetadata.keySet().iterator(); iter.hasNext();) {
            String className = (String) iter.next();
            AbstractEntityPersister aep = (AbstractEntityPersister) classMetadata.get(className);
            SysTable sysTable = new SysTable();
            sysTable.setName(aep.getTableName());
            String[] propertyNames = aep.getPropertyNames();
            for (int i = 0; i < propertyNames.length; i++) {
                String columnName = aep.getPropertyColumnNames(propertyNames[i])[0];
                if (columnName != null) {
                    sysTable.addColumn(new SysColumn(columnName));
                }
            }
            sysTableList.add(sysTable);
        }
        return sysTableList;
    }


    @Override
    public List<NativeRow> executeSQLNativeQuery(final String sql) throws NativeQueryException {
        String sqlLowercase = sql.toLowerCase();
        if (sqlLowercase.indexOf("update ") != -1
                || sqlLowercase.indexOf("delete ") != -1
                || sqlLowercase.indexOf("insert ") != -1) {
            throw new NativeQueryException(
                    "Operation INSERT or UPDATE or DELETE is not allowed");
        }
        final List<NativeRow> rows = new ArrayList<NativeRow>();
        try {
            getCurrentSession().doWork(new Work() {

                public void execute(Connection conn) throws SQLException {
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            NativeRow row = new NativeRow();
                            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                                NativeColumn column = new NativeColumn();
                                column.setName(rs.getMetaData().getColumnName(i));
                                FieldType type = NativeColumnUtils.getCusType(rs.getMetaData().getColumnType(i));
                                switch (type) {
                                    case LONG:
                                        column.setValue(rs.getLong(column.getName()));
                                        break;
                                    case INTEGER:
                                        column.setValue(rs.getInt(column.getName()));
                                        break;
                                    case DATE:
                                        column.setValue(rs.getDate(column.getName()));
                                        break;
                                    case TIME:
                                        column.setValue(rs.getTime(column.getName()));
                                        break;
                                    case DATETIME:
                                        column.setValue(rs.getTimestamp(column.getName()));
                                        break;
                                    case DOUBLE:
                                        column.setValue(rs.getDouble(column.getName()));
                                        break;
                                    case FLOAT:
                                        column.setValue(rs.getFloat(column.getName()));
                                        break;
                                    case BOOLEAN:
                                        column.setValue(rs.getBoolean(column.getName()));
                                        break;
                                    default:
                                        column.setValue(rs.getString(column.getName()));
                                        break;
                                }
                                column.setType(type);
                                row.addColumn(column);
                            }
                            rows.add(row);
                        }
                    } finally {
                        if (stmt != null) {
                            stmt.close();
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.error("-> Error while executing native sql query : " + sql, e);
            throw new NativeQueryException(e);
        }

        return rows;
    }

}
