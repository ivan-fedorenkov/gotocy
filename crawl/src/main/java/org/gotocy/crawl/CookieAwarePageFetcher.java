package org.gotocy.crawl;

import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Custom {@link edu.uci.ics.crawler4j.fetcher.PageFetcher} implementation that supports custom cookies.
 *
 * @author ifedorenkov
 */
public class CookieAwarePageFetcher extends PageFetcher {

	public CookieAwarePageFetcher(CrawlProperties properties) {
		super(properties);

		RequestConfig requestConfig =
			RequestConfig.custom().setExpectContinueEnabled(false).setCookieSpec(CookieSpecs.DEFAULT)
				.setRedirectsEnabled(false).setSocketTimeout(properties.getSocketTimeout())
				.setConnectTimeout(properties.getConnectionTimeout()).build();

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.setDefaultRequestConfig(requestConfig);
		clientBuilder.setConnectionManager(connectionManager);
		clientBuilder.setUserAgent(properties.getUserAgentString());
		clientBuilder.setDefaultHeaders(properties.getDefaultHeaders());

		// Custom cookies are being added here
		CookieStore cookieStore = new BasicCookieStore();
		properties.getCookies().forEach(cookie -> cookieStore.addCookie(cookie.toClientCookie()));
		clientBuilder.setDefaultCookieStore(cookieStore);

		if (properties.getProxyHost() != null) {
			if (properties.getProxyUsername() != null) {
				BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(new AuthScope(properties.getProxyHost(), properties.getProxyPort()),
					new UsernamePasswordCredentials(properties.getProxyUsername(), properties.getProxyPassword()));
				clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			}

			HttpHost proxy = new HttpHost(properties.getProxyHost(), properties.getProxyPort());
			clientBuilder.setProxy(proxy);
			logger.debug("Working through Proxy: {}", proxy.getHostName());
		}

		httpClient = clientBuilder.build();
	}

}
