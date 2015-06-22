package org.gotocy.persistance.jdbc.querydsl;

import com.mysema.query.sql.dml.AbstractSQLClause;

/**
 * @author ifedorenkov
 */
@FunctionalInterface
public interface QueryDslCallback<T extends AbstractSQLClause<T>, V> {
	V call(T clause);
}
