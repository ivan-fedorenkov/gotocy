package org.gotocy.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QProperty is a Querydsl query type for QProperty
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QProperty extends com.mysema.query.sql.RelationalPathBase<QProperty> {

    private static final long serialVersionUID = -1086149689;

    public static final QProperty property = new QProperty("PROPERTY");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.mysema.query.sql.PrimaryKey<QProperty> constraintF = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QLocalizedProperty> _localizedPropertyPropertyIdFk = createInvForeignKey(id, "PROPERTY_ID");

    public QProperty(String variable) {
        super(QProperty.class, forVariable(variable), "PUBLIC", "PROPERTY");
        addMetadata();
    }

    public QProperty(String variable, String schema, String table) {
        super(QProperty.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QProperty(Path<? extends QProperty> path) {
        super(path.getType(), path.getMetadata(), "PUBLIC", "PROPERTY");
        addMetadata();
    }

    public QProperty(PathMetadata<?> metadata) {
        super(QProperty.class, metadata, "PUBLIC", "PROPERTY");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
    }

}

