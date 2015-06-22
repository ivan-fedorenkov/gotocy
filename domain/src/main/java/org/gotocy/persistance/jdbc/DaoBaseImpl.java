package org.gotocy.persistance.jdbc;

import org.gotocy.domain.BaseEntity;
import org.gotocy.persistance.DaoBase;
import org.gotocy.persistance.jdbc.querydsl.QueryDslJdbcTemplate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
public abstract class DaoBaseImpl<T extends BaseEntity> implements DaoBase<T> {

	protected final QueryDslJdbcTemplate queryDslTemplate;

	public DaoBaseImpl(DataSourceProperties dsp, DataSource dataSource) {
		queryDslTemplate = new QueryDslJdbcTemplate(dsp, new JdbcTemplate(dataSource));
	}

}
