package org.gotocy.persistance.jdbc.querydsl;

import com.mysema.query.QueryException;
import com.mysema.query.SearchResults;
import com.mysema.query.sql.H2Templates;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.Expression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.jdbc.query.UncategorizedQueryException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ifedorenkov
 */
public class QueryDslJdbcTemplate {

	private final JdbcTemplate template;
	private final SQLTemplates dialect;

	public QueryDslJdbcTemplate(DataSourceProperties dsp, JdbcTemplate template) {
		this.template = template;

		switch (dsp.getDriverClassName()) {

		case "org.h2.Driver":
			dialect = new H2Templates();
			break;
		default:
			throw new ExceptionInInitializerError("Can't determine the database type.");
		}
	}

	public <T> T queryForObject(SQLQuery sqlQuery, Expression<T> expression) {
		List<T> results = queryList(sqlQuery, expression);
		if (results.size() == 0)
			return null;
		if (results.size() > 1)
			throw new IncorrectResultSizeDataAccessException(1, results.size());
		return results.get(0);
	}

	public <T> List<T> queryList(SQLQuery sqlQuery, Expression<T> expression) {
		return template.execute((Connection con) -> {
			SQLQuery query = sqlQuery.clone(con);
			try {
				return query.list(expression);
			} catch (QueryException qe) {
				throw translateQueryException(qe, "SQLQuery", query.toString());
			}
		});
	}

	public <T> SearchResults<T> queryResults(SQLQuery sqlQuery, Expression<T> expression) {
		return template.execute((Connection con) -> {
			SQLQuery query = sqlQuery.clone(con);
			try {
				return query.listResults(expression);
			} catch (QueryException qe) {
				throw translateQueryException(qe, "SQLQuery", query.toString());
			}
		});
	}

	public SQLQuery newSqlQuery() {
		return new SQLQuery(dialect);
	}

	public Long insert(RelationalPath<?> entity, QueryDslCallback<SQLInsertClause, Long> callback) {
		return template.execute((Connection con) -> {
			SQLInsertClause clause = new SQLInsertClause(con, dialect, entity);
			try {
				return callback.call(clause);
			} catch (QueryException qe) {
				throw  translateQueryException(qe, "SQLInsertClause", clause.toString());
			}
		});
	}

	private RuntimeException translateQueryException(QueryException qe, String task, String query) {
		Throwable t = qe.getCause();
		if (t instanceof SQLException)
			return template.getExceptionTranslator().translate(task, query, (SQLException) t);
		return new UncategorizedQueryException("Error in " + "SQLQuery", qe);
	}


}
