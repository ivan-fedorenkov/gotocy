package org.gotocy.integration.persistance.jdbc;

import org.gotocy.domain.Property;
import org.gotocy.domain.QProperty;
import org.gotocy.integration.Config;
import org.gotocy.persistance.PropertyDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ifedorenkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Config.class)
@IntegrationTest
@Transactional
public class PropertyDaoTest {

	@Autowired
	private PropertyDao dao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void count() {
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, QProperty.property.getTableName()), dao.count());
		dao.save(new Property());
		assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, QProperty.property.getTableName()), dao.count());
	}

	@Test
	public void save() {
		Property p = dao.save(new Property());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, QProperty.property.getTableName()), dao.count());
		assertNotNull(p.getId());
	}

	@Test
	public void findOne() {
		Property p = dao.save(new Property());
		assertNotNull(dao.findOne(p.getId()));
	}

	@Test
	public void findAll() {
		Property[] properties = new Property[] {new Property(), new Property()};
		dao.save(properties[0]);
		dao.save(properties[1]);

		Iterable<Property> foundProperties = dao.findAll();
		int foundPropertiesSize = 0;
		for (Property p : foundProperties)
			foundPropertiesSize++;

		assertEquals(properties.length, foundPropertiesSize);
	}

}
