package org.gotocy.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QProperty is a Querydsl query type for Property
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProperty extends EntityPathBase<Property> {

    private static final long serialVersionUID = -1060035756L;

    public static final QProperty property = new QProperty("property");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QProperty(String variable) {
        super(Property.class, forVariable(variable));
    }

    public QProperty(Path<? extends Property> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProperty(PathMetadata<?> metadata) {
        super(Property.class, metadata);
    }

}

