package org.gotocy.tools;

import com.mysema.query.sql.codegen.MetaDataExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

/**
 * An utility tool to create querydsl metadata objects.
 *
 * @author ifedorenkov
 */
@EnableAutoConfiguration
public class SchemaExporter {

	public static void main(String[] args) throws SQLException {
		ApplicationContext context = SpringApplication.run(SchemaExporter.class, args);
		DataSource dataSource = context.getBean(DataSource.class);

		MetaDataExporter exporter = new MetaDataExporter();
		exporter.setPackageName("org.gotocy.domain");
		exporter.setTargetFolder(new File("domain/target/generated-sources/java"));
		exporter.export(dataSource.getConnection().getMetaData());
	}

}
