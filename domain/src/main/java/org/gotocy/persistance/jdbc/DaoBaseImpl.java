package org.gotocy.persistance.jdbc;

import org.gotocy.domain.BaseEntity;
import org.gotocy.persistance.DaoBase;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
public abstract class DaoBaseImpl<T extends BaseEntity> implements DaoBase<T> {

	protected final JdbcTemplate jdbcTemplate;
	protected final QueryDslJdbcTemplate queryDslTemplate;

	public DaoBaseImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		queryDslTemplate = new QueryDslJdbcTemplate(jdbcTemplate);
	}

}
