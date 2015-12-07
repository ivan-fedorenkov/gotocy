package org.gotocy.i18n;

import org.gotocy.config.Locales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

/**
 * Localization helper with the following features:
 *
 * <ul>
 *     <li>construction of message keys from string, object (enum constant), number (integer)</li>
 *     <li>message resolution with fallback to default locale</li>
 *     <li>logging</li>
 * </ul>
 *
 * Utilizes the {@link LocaleContextHolder#getLocale()} to determine the current locale.
 *
 * This class uses dirty hack to substitute static message source to real message source during container initialization.
 * So it can't resolve messages until container is fully initialized or explicit call to {@link #setMessageSource(MessageSource)} was made.
 *
 * @author ifedorenkov
 */
@Component
public class I18n implements MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(I18n.class);

	private static final String NO_MESSAGE = "NO_MESSAGE";
	private static MessageSource messageSource = new StaticMessageSource();

	@Override
	public void setMessageSource(MessageSource messageSource) {
		I18n.messageSource = messageSource;
	}

	/**
	 * Resolves the given code in the current locale to localized message. Fallback to default locale if can't resolve
	 * message in the current locale.
	 *
	 * @param code to be resolved
	 * @param args to be passed to message resolver
	 * @return resolved localized message
	 * @throws NoSuchMessageException when code can't be resolved even for default locale
	 */
	public static String t(String code, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		String resolved = messageSource.getMessage(code, args, NO_MESSAGE, locale);

		//noinspection StringEquality
		if (NO_MESSAGE.equals(resolved)) {
			resolved = messageSource.getMessage(code, args, Locales.DEFAULT);
			logger.warn("No message found under code '{}' for locale '{}'. Fallback to default translation '{}'",
				code, locale, resolved);
		}

		return resolved;
	}

	/**
	 * Resolves the given message source resolvable object to localized message. Fallback to default locale if can't
	 * resolve message in the current locale.
	 *
	 * @param messageSourceResolvable to be resolved
	 * @return resolved localized message
	 * @throws NoSuchMessageException when object can't be resolved even for default locale
	 */
	public static String t(MessageSourceResolvable messageSourceResolvable) {
		Locale locale = LocaleContextHolder.getLocale();

		String resolved;
		try {
			resolved = messageSource.getMessage(messageSourceResolvable, locale);
			if (Objects.equals(resolved, messageSourceResolvable.getDefaultMessage())) {
				resolved = messageSource.getMessage(messageSourceResolvable, Locales.DEFAULT);
				logger.warn("No message found for object '{}' for locale '{}'. Fallback to default translation '{}'",
					messageSourceResolvable, locale, resolved);
			}
		} catch (NoSuchMessageException nme) {
			resolved = messageSource.getMessage(messageSourceResolvable, Locales.DEFAULT);
			logger.warn("No message found for object '{}' for locale '{}'. Fallback to default translation '{}'",
				messageSourceResolvable, locale, resolved);
		}
		return resolved;
	}

	/**
	 * Delegate to {@link MessageSource#getMessage(String, Object[], Locale)}.
	 */
	public static String getMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}

	/**
	 * Delegate to {@link MessageSource#getMessage(MessageSourceResolvable, Locale)}.
	 */
	public static String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) {
		return messageSource.getMessage(messageSourceResolvable, locale);
	}

}
