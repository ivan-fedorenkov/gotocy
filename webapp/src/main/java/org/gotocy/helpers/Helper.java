package org.gotocy.helpers;

import com.amazonaws.services.s3.AmazonS3Client;
import org.gotocy.beans.S3Configuration;
import org.gotocy.domain.Asset;
import org.gotocy.domain.BaseEntity;
import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.util.NumberPointType;
import org.thymeleaf.util.NumberUtils;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A helper object for the view layer. Contains a number of utility methods, such as price formatting, etc.
 * TODO: change the s3 dependency on some project interface that is to generate urls
 *
 * @author ifedorenkov
 */
public class Helper {

	private final AmazonS3Client s3client;
	private final S3Configuration s3config;

	public Helper(@NotNull AmazonS3Client s3client, @NotNull S3Configuration s3config) {
		this.s3client = s3client;
		this.s3config = s3config;
	}

	/**
	 * Generates url for a given asset. In production, the asset path would be converted into a presigned s3 link.
	 */
	public String url(Asset asset) {
		return s3client.generatePresignedUrl(s3config.getBucket(), asset.getAssetKey(),
			s3config.getExpirationDate()).toString();
	}

	/**
	 * Returns a string price representation adding the dollar symbol.
	 */
	public static String price(int price) {
		return "$ " + NumberUtils.format(price, 1, NumberPointType.COMMA, LocaleContextHolder.getLocale());
	}

	/**
	 * Returns path of a given entity object.
	 */
	public static <T extends BaseEntity> String path(T entity) {
		if (entity instanceof Property) {
			return "/property/" + entity.getId();
		} else if (entity instanceof LocalizedProperty) {
			LocalizedProperty lp = (LocalizedProperty) entity;
			return getPrefixForLanguage(lp.getLocale()) + path(lp.getProperty());
		}
		// TODO: log error
		return "";
	}

	/**
	 * Returns path of of a given entity object using the given language.
	 * The language should follow {@link java.util.Locale} rules.
	 */
	public static <T extends BaseEntity> String path(T entity, String language) {
		if (entity instanceof Property) {
			return getPrefixForLanguage(language) + "/property/" + entity.getId();
		} else if (entity instanceof LocalizedProperty) {
			return path(((LocalizedProperty) entity).getProperty(), language);
		}
		// TODO: log error
		return "";
	}

	private static String getPrefixForLanguage(String language) {
		return Objects.equals(language, "ru") ? "/ru" : "";
	}

}
