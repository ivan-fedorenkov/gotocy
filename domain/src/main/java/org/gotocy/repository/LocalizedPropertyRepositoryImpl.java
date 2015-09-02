package org.gotocy.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;
import org.gotocy.domain.LocalizedProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

/**
 * @author ifedorenkov
 */
public class LocalizedPropertyRepositoryImpl extends SimpleJpaRepository<LocalizedProperty, Long>
	implements LocalizedPropertyRepositoryCustom
{
	private final EntityManager entityManager;
	private final EntityPath<LocalizedProperty> path;
	private final PathBuilder<LocalizedProperty> builder;
	private final Querydsl querydsl;

	@Autowired
	public LocalizedPropertyRepositoryImpl(EntityManager entityManager) {
		super(LocalizedProperty.class, entityManager);


		CrudMethodMetadata metadata = getRepositoryMethodMetadata();
		this.entityManager = entityManager;
		this.path = SimpleEntityPathResolver.INSTANCE.createPath(LocalizedProperty.class);
		this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(entityManager, builder);
	}

	@Override
	public Page<LocalizedProperty> findAll(Predicate predicate, Pageable pageable) {
		JPAQuery countQuery = createQuery(predicate);
		JPAQuery query = (JPAQuery) querydsl.applyPagination(pageable, createQuery(predicate));

		query.setHint(EntityGraph.EntityGraphType.LOAD.getKey(),
			entityManager.getEntityGraph("LocalizedProperty.withProperty"));

		Long total = countQuery.count();
		List<LocalizedProperty> content = total > pageable.getOffset() ? query.list(path) :
			Collections.<LocalizedProperty> emptyList();

		return new PageImpl<>(content, pageable, total);
	}

	private JPAQuery createQuery(Predicate predicate) {
		return querydsl.createQuery(path).where(predicate);
	}

}
