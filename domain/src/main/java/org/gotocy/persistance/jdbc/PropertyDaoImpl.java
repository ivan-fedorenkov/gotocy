package org.gotocy.persistance.jdbc;

import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.QBean;
import org.gotocy.domain.Property;
import org.gotocy.domain.QProperty;
import org.gotocy.persistance.PropertyDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
public class PropertyDaoImpl extends DaoBaseImpl<Property> implements PropertyDao {

	private static final QProperty QP = QProperty.property;

	public PropertyDaoImpl(DataSource dataSource) {
		super(dataSource, Property.class, QP);
	}

	@Override
	@Transactional
	public Property findOne(Long id) {
		SQLQuery sqlQuery = newSqlQuery().from(QP).where(QP.id.eq(id));
		return queryDslTemplate.queryForObject(sqlQuery, new QBean<>(Property.class, QP.all()));
	}

	@Override
	@Transactional
	public Iterable<Property> findAll() {
		SQLQuery sqlQuery = newSqlQuery().from(QP);
		return queryDslTemplate.query(sqlQuery, new QBean<>(Property.class, QP.all()));
	}

	@Override
	public Iterable<Property> findAll(Sort sort) {
		SQLQuery sqlQuery = newSqlQuery(sort).from(QP);
		return queryDslTemplate.query(sqlQuery, new QBean<>(Property.class, QP.all()));
	}

	@Override
	public Page<Property> findAll(Pageable pageable) {
		SQLQuery sqlQuery = newSqlQuery(pageable).from(QP);
		return new PageImpl<>(queryDslTemplate.query(sqlQuery, new QBean<>(Property.class, QP.all())));
	}

	@Override
	@Transactional
	public <S extends Property> S save(S property) {
		Long id = queryDslTemplate.insertWithKey(QP, (c) -> (c.populate(property)).executeWithKey(Long.class));
		property.setId(id);
		return property;
	}

}
