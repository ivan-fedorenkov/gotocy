package org.gotocy.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QLocalizedProperty is a Querydsl query type for LocalizedProperty
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLocalizedProperty extends EntityPathBase<LocalizedProperty> {

    private static final long serialVersionUID = -829172783L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocalizedProperty localizedProperty = new QLocalizedProperty("localizedProperty");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath locale = createString("locale");

    public final QProperty property;

    public final StringPath title = createString("title");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLocalizedProperty(String variable) {
        this(LocalizedProperty.class, forVariable(variable), INITS);
    }

    public QLocalizedProperty(Path<? extends LocalizedProperty> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocalizedProperty(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocalizedProperty(PathMetadata<?> metadata, PathInits inits) {
        this(LocalizedProperty.class, metadata, inits);
    }

    public QLocalizedProperty(Class<? extends LocalizedProperty> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.property = inits.isInitialized("property") ? new QProperty(forProperty("property")) : null;
    }

}

