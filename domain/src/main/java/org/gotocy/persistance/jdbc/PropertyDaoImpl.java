package org.gotocy.persistance.jdbc;

import com.mysema.query.SearchResults;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.QBean;
import org.gotocy.domain.Property;
import org.gotocy.domain.QProperty;
import org.gotocy.persistance.PropertyDao;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
public class PropertyDaoImpl extends DaoBaseImpl<Property> implements PropertyDao {

	private static final QProperty QP = QProperty.property;

	public PropertyDaoImpl(DataSourceProperties dsp, DataSource dataSource) {
		super(dsp, dataSource);
	}

	@Override
	@Transactional
	public Property save(Property property) {
		Long id = queryDslTemplate.insert(QP, (c) -> (c.populate(property)).executeWithKey(Long.class));
		property.setId(id);
		return property;
	}

	@Override
	@Transactional
	public Property findOne(Long id) {
		SQLQuery sqlQuery = queryDslTemplate.newSqlQuery().from(QP).where(QP.id.eq(id));
		return queryDslTemplate.queryForObject(sqlQuery, new QBean<>(Property.class, QP.all()));
	}

	@Override
	@Transactional
	public SearchResults<Property> findAll() {
		SQLQuery sqlQuery = queryDslTemplate.newSqlQuery().from(QP);
		return queryDslTemplate.queryResults(sqlQuery, new QBean<>(Property.class, QP.all()));
	}

}
