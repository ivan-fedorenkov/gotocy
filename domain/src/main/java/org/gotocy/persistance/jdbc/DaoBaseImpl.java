package org.gotocy.persistance.jdbc;

import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.PathBuilder;
import org.gotocy.domain.BaseEntity;
import org.gotocy.persistance.DaoBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
public abstract class DaoBaseImpl<T extends BaseEntity> implements DaoBase<T> {

	protected final QueryDslJdbcTemplate queryDslTemplate;
	protected final PathBuilder<?> builder;

	public DaoBaseImpl(DataSource dataSource, Class<T> type, RelationalPathBase<?> pathBase) {
		queryDslTemplate = new QueryDslJdbcTemplate(new JdbcTemplate(dataSource));
		builder = new PathBuilder<>(type, pathBase.getMetadata());
	}

	@Override
	public Iterable<T> findAll(Sort sort) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends T> S save(S entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T findOne(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean exists(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<T> findAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<T> findAll(Iterable<Long> ids) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long count() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll() {
		throw new UnsupportedOperationException();
	}

	protected SQLQuery newSqlQuery() {
		return queryDslTemplate.newSqlQuery();
	}

	protected SQLQuery newSqlQuery(Pageable pageable) {
		return applyPaging(newSqlQuery(), pageable);
	}

	protected SQLQuery newSqlQuery(Sort sort) {
		return applySorting(newSqlQuery(), sort);
	}

	private SQLQuery applyPaging(SQLQuery query, Pageable pageable) {
		if (pageable == null)
			return query;
		query.offset(pageable.getOffset());
		query.limit(pageable.getPageSize());
		return applySorting(query, pageable.getSort());
	}

	private SQLQuery applySorting(SQLQuery query, Sort sort) {
		if (sort == null)
			return query;
		sort.forEach((order -> query.orderBy(toOrderBy(order))));
		return query;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private OrderSpecifier toOrderBy(Sort.Order order) {
		Expression property = builder.get(order.getProperty());
		return new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, property);
	}

}
