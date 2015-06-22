package org.gotocy.persistance.jdbc;

import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.BeanMapper;
import com.mysema.query.sql.dml.Mapper;
import com.mysema.query.types.MappingProjection;
import com.mysema.query.types.Path;
import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.gotocy.domain.QLocalizedProperty;
import org.gotocy.domain.QProperty;
import org.gotocy.persistance.LocalizedPropertyDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author ifedorenkov
 */
public class LocalizedPropertyDaoImpl extends DaoBaseImpl<LocalizedProperty> implements LocalizedPropertyDao {

	private static final QProperty QP = QProperty.property;
	private static final QLocalizedProperty QLP = QLocalizedProperty.localizedProperty;

	public LocalizedPropertyDaoImpl(DataSource dataSource) {
		super(dataSource, LocalizedProperty.class, QLP);
	}

	@Override
	@Transactional
	public <S extends LocalizedProperty> S save(S property) {
		Long id = queryDslTemplate.insert(QLP, (c) -> c.populate(property, PROPERTY_MAPPER).executeWithKey(Long.class));
		property.setId(id);
		return property;
	}

	@Override
	@Transactional
	public LocalizedProperty findOne(Long id) {
		SQLQuery sqlQuery = newSqlQuery()
			.from(QLP)
			.innerJoin(QP).on(QLP.id.eq(QP.id))
			.where(QLP.propertyId.eq(QP.id));

		return queryDslTemplate.queryForObject(sqlQuery, PROPERTY_PROJECTION);

	}

	@Override
	@Transactional
	public List<LocalizedProperty> findAll() {
		SQLQuery sqlQuery = newSqlQuery().from(QLP).innerJoin(QP).on(QLP.propertyId.eq(QP.id));
		return queryDslTemplate.query(sqlQuery, PROPERTY_PROJECTION);
	}

	@Override
	public Iterable<LocalizedProperty> findAll(Sort sort) {
		SQLQuery sqlQuery = newSqlQuery(sort).from(QLP).innerJoin(QP).on(QLP.propertyId.eq(QP.id));
		return queryDslTemplate.query(sqlQuery, PROPERTY_PROJECTION);
	}

	@Override
	public Page<LocalizedProperty> findAll(Pageable pageable) {
		SQLQuery sqlQuery = newSqlQuery(pageable).from(QLP).innerJoin(QP).on(QLP.propertyId.eq(QP.id));
		return new PageImpl<>(queryDslTemplate.query(sqlQuery, PROPERTY_PROJECTION));
	}

	private static final MappingProjection<LocalizedProperty> PROPERTY_PROJECTION =
		new MappingProjection<LocalizedProperty>(LocalizedProperty.class, QP.all(), QLP.all())
		{
			@Override
			protected LocalizedProperty map(Tuple tuple) {
				Property p = new Property();
				p.setId(tuple.get(QP.id));

				LocalizedProperty lp = new LocalizedProperty();
				lp.setId(tuple.get(QLP.id));
				lp.setTitle(tuple.get(QLP.title));
				lp.setLocale(tuple.get(QLP.locale));
				lp.setProperty(p);

				return lp;
			}
		};

	private static final Mapper<LocalizedProperty> PROPERTY_MAPPER = (path, lp) -> {
		Map<Path<?>, Object> values = BeanMapper.DEFAULT.createMap(path, lp);
		if (lp.getProperty() != null)
			values.put(QLP.propertyId, lp.getProperty().getId());
		return values;
	};

}
