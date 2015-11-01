package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.gotocy.helpers.Helper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Objects;

/**
 * Property fields and their view layer representation.
 *
 * @author ifedorenkov
 */
public enum FieldFormat {
	LOCATION {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.location";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return ms.getMessage(p.getLocation(), LocaleContextHolder.getLocale());
		}
	},
	PROPERTY_TYPE {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.property-type";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return ms.getMessage(p.getPropertyType(), LocaleContextHolder.getLocale());
		}
	},
	RENTAL_TYPE {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.rental";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return ms.getMessage(p.getPropertyStatus(), LocaleContextHolder.getLocale());
		}
	},
	PRICE {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.price";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return "<span class=\"tag price\">" + PropertyHelper.price(ms, p)  + "</span>";
		}
	},
	VAT {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.vat";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return ms.getMessage(
				p.isVatIncluded() ?
					"org.gotocy.dictionary.vat-included" : "org.gotocy.dictionary.vat-not-included",
				new Object[0], LocaleContextHolder.getLocale());
		}
	},
	COVERED_AREA {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.covered-area";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return p.getCoveredArea() + " " +
				ms.getMessage("org.gotocy.dictionary.meters", new Object[0], LocaleContextHolder.getLocale()) +
				"<sup>2</sup>";
		}
	},
	PLOT_SIZE {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.plot-size";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return p.getPlotSize() + " " +
				ms.getMessage("org.gotocy.dictionary.meters", new Object[0], LocaleContextHolder.getLocale()) +
				"<sup>2</sup>";
		}
	},
	LEVELS {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.levels." + p.getPropertyType().name();
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return Integer.toString(p.getLevels());
		}
	},
	BEDROOMS {
		@Override
		public String getHeadingKey(Property p) {
			return Helper.pluralize("org.gotocy.dictionary.bedrooms", p.getBedrooms());
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return Integer.toString(p.getBedrooms());
		}
	},
	GUESTS {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.guests";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return Integer.toString(p.getGuests());
		}
	},
	FURNISHING {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.furnishing";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return p.getFurnishing() == null ? "" : ms.getMessage(p.getFurnishing(), LocaleContextHolder.getLocale());
		}
	},
	HEATING_SYSTEM {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.heating-system";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return yesNoValue(ms, p.hasHeatingSystem());
		}
	},
	AIR_CONDITIONER {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.air-conditioner";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return yesNoValue(ms, p.hasAirConditioner());
		}
	},
	READY_TO_MOVE_IN {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.ready-to-move-in";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return yesNoValue(ms, p.isReadyToMoveIn());
		}
	},
	DISTANCE_TO_SEA {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.distance-to-sea";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return PropertyHelper.distance(ms, p.getDistanceToSea());
		}
	},
	DISTANCE_TO_SEA_SHORT {
		@Override
		public String getHeadingKey(Property p) {
			return "org.gotocy.dictionary.distance-to-sea-short";
		}

		@Override
		public String formatValue(MessageSource ms, Property p) {
			return PropertyHelper.distance(ms, p.getDistanceToSea());
		}
	};

	/**
	 * Returns the heading key of a field that can be resolved later through a {@link MessageSource}.
	 */
	public abstract String getHeadingKey(Property p);

	/**
	 * Returns the formatted heading using the given message source and thread-bound locale.
	 */
	public String formatHeadingKey(MessageSource ms, Property p) {
		return ms.getMessage(getHeadingKey(p), new Object[0], LocaleContextHolder.getLocale());
	}

	/**
	 * Returns the formatted field value using the given message source and thread-bound locale.
	 */
	public abstract String formatValue(MessageSource ms, Property p);

	protected String yesNoValue(MessageSource ms, boolean condition) {
		return ms.getMessage(condition ? "org.gotocy.dictionary.yes" : "org.gotocy.dictionary.no",
			new Object[0], LocaleContextHolder.getLocale());
	}

}
