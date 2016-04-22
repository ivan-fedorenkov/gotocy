package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.Locales;
import org.gotocy.config.Profiles;
import org.gotocy.config.SecurityProperties;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.forms.PageForm;
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
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true), Locales.DEFAULT);
		pageRepository.save(page);
		mockMvc.perform(get("/" + page.localize(Locales.DEFAULT).getUrl())).andExpect(status().isOk());
	}

	@Test
	public void invisiblePageShouldNotBeAccessible() throws Exception {
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(false), Locales.DEFAULT);
		pageRepository.save(page);
		mockMvc.perform(get("/" + page.localize(Locales.DEFAULT).getUrl())).andExpect(status().isForbidden());
	}

	@Test
	public void nonExistingPageShouldNotBeAccessible() throws Exception {
		mockMvc.perform(get("/non-existing-page-url")).andExpect(status().isNotFound());
	}

	@Test
	public void nonSupportedLanguageShouldNotBeAccessible() throws Exception {
		// Russian locale for existing page translation and English locale for non-existing page translation,
		// because I can't figure out quickly how to apply LocaleFilter correctly
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true), Locales.RU);
		pageRepository.save(page);
		mockMvc.perform(get("/" + page.localize(Locales.RU).getUrl())).andExpect(status().isNotFound());
	}

	@Test
	public void urlNotMatchingLocale() throws Exception {
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true), Locales.EN, Locales.RU);
		LocalizedPage enPage = PageFactory.INSTANCE.getLocalized(page, Locales.EN, lp -> lp.setUrl("some-url"));
		LocalizedPage ruPage = PageFactory.INSTANCE.getLocalized(page, Locales.RU, lp -> lp.setUrl("kakayia-to-ssilka"));
		pageRepository.save(page);
		mockMvc.perform(get("/" + ruPage.getUrl())).andExpect(redirectedUrl("/" + enPage.getUrl()));
	}


	@Test
	public void pageCreationByAdminTest() throws Exception {
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true), Locales.DEFAULT);
		PageForm pageForm = new PageForm(page, Locales.DEFAULT);

		// Create page and verify the redirect
		mockMvc
			.perform(post("/master/pages")
				.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
				.param("title", pageForm.getTitle())
				.param("html", pageForm.getHtml())
				.param("visible", String.valueOf(pageForm.isVisible()))
				.param("url", pageForm.getUrl()))
			.andExpect(redirectedUrlPattern("/master/pages/*")); // Redirect to created page


		// Verify that created page is listed in the pages index
		mockMvc
			.perform(get("/master/pages").sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(pageForm.getTitle())));

		// Verify that created page is available for users
		mockMvc.perform(get("/" + pageForm.getUrl())).andExpect(status().isOk());
	}

	@Test
	public void pageUpdateByAdminTest() throws Exception {
		Page page = PageFactory.INSTANCE.get(p -> p.setVisible(true), Locales.DEFAULT);
		pageRepository.save(page);

		// Verify that user can access the page before update

		mockMvc.perform(get("/" + page.localize(Locales.DEFAULT).getUrl())).andExpect(status().isOk());

		String updatedTitle = "updated title";
		String updatedHtml = "updated html";
		String updatedUrl = "updated-url";

		// Update the page and verify redirect
		mockMvc
			.perform(put("/master/pages/" + page.getId())
				.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
				.param("title", updatedTitle)
				.param("html", updatedHtml)
				.param("url", updatedUrl)
				.param("visible", String.valueOf(false)))
			.andExpect(redirectedUrl("/master/pages/" + page.getId()));

		// Verify updated fields
		Page updatedPage = pageRepository.findByUrl(updatedUrl);
		LocalizedPage updatedLocalizedPage = updatedPage.localize(Locales.DEFAULT);
		Assert.assertEquals(updatedTitle, updatedLocalizedPage.getTitle());
		Assert.assertEquals(updatedHtml, updatedLocalizedPage.getHtml());
		Assert.assertFalse(updatedPage.isVisible());

		// Verify that user can not access the page after update
		mockMvc.perform(get("/" + updatedLocalizedPage.getUrl())).andExpect(status().isForbidden());
	}

	@Test
	public void testAttemptToCreatePageWithExistingUrl() throws Exception {
		Page existing = PageFactory.INSTANCE.get(p -> p.setVisible(true), Locales.DEFAULT);
		pageRepository.save(existing);

		LocalizedPage duplicateUrl = PageFactory.INSTANCE.getLocalized(Locales.DEFAULT, lp -> {
			lp.setUrl(existing.localize(Locales.DEFAULT).getUrl());
		});

		// Detect that new object wasn't created by view name (at least)
		mockMvc.perform(post("/master/pages")
				.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
				.param("url", duplicateUrl.getUrl())
				.param("title", duplicateUrl.getTitle())
				.param("html", duplicateUrl.getHtml())
				.param("visible", String.valueOf(duplicateUrl.isVisible())))
			.andExpect(view().name("master/page/new"));

		// Verify that existing page is still accessible
		mockMvc.perform(get("/" + existing.localize(Locales.DEFAULT).getUrl())).andExpect(status().isOk());
	}

	@Test
	public void testAttemptToUpdatePageWithExistingUrl() throws Exception {
		String existingUrl = "existing-url";
		LocalizedPage existing = PageFactory.INSTANCE.getLocalized(Locales.DEFAULT, lp -> {
			lp.getOriginal().setVisible(true);
			lp.setUrl(existingUrl);
		});
		pageRepository.save(existing.getOriginal());

		String anotherExistingUrl = "another-existing-url";
		LocalizedPage anotherExisting = PageFactory.INSTANCE.getLocalized(Locales.DEFAULT, lp -> {
			lp.getOriginal().setVisible(true);
			lp.setUrl(anotherExistingUrl);
		});
		pageRepository.save(anotherExisting.getOriginal());
		// Detect that new object wasn't created by view name (at least)
		mockMvc.perform(put("/master/pages/" + existing.getId())
			.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
			.param("url", anotherExisting.getUrl())
			.param("title", existing.getTitle())
			.param("html", existing.getHtml())
			.param("visible", String.valueOf(existing.isVisible())))
			.andExpect(view().name("master/page/edit"));

		// Verify that both pages are still accessible
		mockMvc.perform(get("/" + existing.getUrl())).andExpect(status().isOk());
		mockMvc.perform(get("/" + anotherExisting.getUrl())).andExpect(status().isOk());
	}

}
