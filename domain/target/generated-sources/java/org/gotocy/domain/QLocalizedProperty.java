package org.gotocy.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;

import com.mysema.query.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QLocalizedProperty is a Querydsl query type for QLocalizedProperty
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QLocalizedProperty extends com.mysema.query.sql.RelationalPathBase<QLocalizedProperty> {

    private static final long serialVersionUID = 1079581886;

    public static final QLocalizedProperty localizedProperty = new QLocalizedProperty("LOCALIZED_PROPERTY");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath locale = createString("locale");

    public final NumberPath<Long> propertyId = createNumber("propertyId", Long.class);

    public final StringPath title = createString("title");

    public final com.mysema.query.sql.PrimaryKey<QLocalizedProperty> constraintD = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QProperty> localizedPropertyPropertyIdFk = createForeignKey(propertyId, "ID");

    public QLocalizedProperty(String variable) {
        super(QLocalizedProperty.class, forVariable(variable), "PUBLIC", "LOCALIZED_PROPERTY");
        addMetadata();
    }

    public QLocalizedProperty(String variable, String schema, String table) {
        super(QLocalizedProperty.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QLocalizedProperty(Path<? extends QLocalizedProperty> path) {
        super(path.getType(), path.getMetadata(), "PUBLIC", "LOCALIZED_PROPERTY");
        addMetadata();
    }

    public QLocalizedProperty(PathMetadata<?> metadata) {
        super(QLocalizedProperty.class, metadata, "PUBLIC", "LOCALIZED_PROPERTY");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(locale, ColumnMetadata.named("LOCALE").withIndex(3).ofType(Types.VARCHAR).withSize(8).notNull());
        addMetadata(propertyId, ColumnMetadata.named("PROPERTY_ID").withIndex(4).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(title, ColumnMetadata.named("TITLE").withIndex(2).ofType(Types.VARCHAR).withSize(256).notNull());
    }

}

