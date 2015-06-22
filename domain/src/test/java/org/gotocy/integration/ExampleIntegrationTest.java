package org.gotocy.integration;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.gotocy.persistance.LocalizedPropertyDao;
import org.gotocy.persistance.PropertyDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ifedorenkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Config.class)
@IntegrationTest
public class ExampleIntegrationTest {

	@Autowired
	private PropertyDao propertyDao;

	@Autowired
	private LocalizedPropertyDao localizedPropertyDao;

	@Test
	@Transactional
	public void test() {
		Property p = new Property();
		p = propertyDao.save(p);


		LocalizedProperty lp = new LocalizedProperty();
		lp.setLocale("en");
		lp.setTitle("Title");
		lp.setProperty(p);

		lp = localizedPropertyDao.save(lp);

		Property foundProperty = propertyDao.findOne(p.getId());
		Iterable<Property> foundAllProperties = propertyDao.findAll();

		LocalizedProperty foundLocalizedProperty = localizedPropertyDao.findOne(lp.getId());

		int j = 1;
	}

}
