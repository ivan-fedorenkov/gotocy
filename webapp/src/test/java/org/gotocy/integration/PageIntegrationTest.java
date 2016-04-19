package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.Locales;
import org.gotocy.config.Profiles;
import org.gotocy.config.SecurityProperties;
import org.gotocy.domain.Page;
import org.gotocy.repository.PageRepository;
import org.gotocy.test.factory.PageFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author ifedorenkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true, value = "debug")
@ActiveProfiles(Profiles.TEST)
@Transactional
public class PageIntegrationTest {

	@Autowired
	private PageRepository pageRepository;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void visiblePageShouldBeAccessible() throws Exception {
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true));
		page.initLocalizedFieldsFromTransients(Locales.DEFAULT);
		pageRepository.save(page);
		mockMvc.perform(get("/" + page.getUrl())).andExpect(status().isOk());
	}

	@Test
	public void invisiblePageShouldNotBeAccessible() throws Exception {
		Page page = PageFactory.INSTANCE.get();
		page.initLocalizedFieldsFromTransients(Locales.DEFAULT);
		pageRepository.save(page);
		mockMvc.perform(get("/" + page.getUrl())).andExpect(status().isForbidden());
	}

	@Test
	public void nonExistingPageShouldNotBeAccessible() throws Exception {
		mockMvc.perform(get("/non-existing-page-url")).andExpect(status().isNotFound());
	}

	@Test
	public void urlNotMatchingLocale() throws Exception {
		String enUrl = "some-url";
		String ruUrl = "kakayia-to-ssilka";

		Page page = PageFactory.INSTANCE.get(p -> {
			p.setVisible(true);
			p.setUrl(enUrl, Locales.EN);
			p.setUrl(ruUrl, Locales.RU);
		});

		pageRepository.save(page);

		mockMvc.perform(get("/" + page.getUrl(Locales.RU).get()))
			.andExpect(redirectedUrl("/" + page.getUrl(Locales.EN).get()));
	}


	@Test
	public void pageCreationByAdminTest() throws Exception {
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true));

		// Create page and verify the redirect
		mockMvc
			.perform(post("/master/pages")
				.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
				.param("title", page.getTitle())
				.param("html", page.getHtml())
				.param("visible", String.valueOf(page.isVisible()))
				.param("url", page.getUrl()))
			.andExpect(redirectedUrlPattern("/master/pages/*")); // Redirect to created page


		// Verify that created page is listed in the pages index
		mockMvc
			.perform(get("/master/pages").sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(page.getTitle())));

		// Verify that created page is available for users
		mockMvc.perform(get("/" + page.getUrl())).andExpect(status().isOk());
	}

	@Test
	public void pageUpdateByAdminTest() throws Exception {
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true));
		page.initLocalizedFieldsFromTransients(Locales.DEFAULT);
		pageRepository.save(page);

		// Verify that user can access the page before update

		mockMvc.perform(get(UriComponentsBuilder.fromPath("/{url}").buildAndExpand(page.getUrl()).toUri()))
			.andExpect(status().isOk());

		String updatedTitle = "updated title";
		String updatedHtml = "updated html";
		String updatedUrl = "updated-url";

		// Update the page and verify redirect
		mockMvc
			.perform(put(UriComponentsBuilder.fromPath("/master/pages/{url}").buildAndExpand(page.getUrl()).toUri())
				.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
				.param("title", updatedTitle)
				.param("html", updatedHtml)
				.param("url", updatedUrl)
				.param("visible", String.valueOf(false)))
			.andExpect(redirectedUrl(UriComponentsBuilder.fromPath("/master/pages/{url}")
				.buildAndExpand(updatedUrl).toString()));

		// Verify updated fields
		Page updatedPage = pageRepository.findByUrl(updatedUrl);
		Assert.assertEquals(updatedTitle, updatedPage.getTitle());
		Assert.assertEquals(updatedHtml, updatedPage.getHtml());
		Assert.assertFalse(updatedPage.isVisible());

		// Verify that user can not access the page after update
		mockMvc.perform(get(UriComponentsBuilder.fromPath("/{url}").buildAndExpand(page.getUrl()).toUri()))
			.andExpect(status().isForbidden());
	}

}
