package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.gotocy.helpers.Helper;
import org.gotocy.i18n.I18n;
import org.springframework.context.MessageSource;

/**
 * Property fields and their view layer representation.
 *
 * @author ifedorenkov
 */
public enum FieldFormat {
	LOCATION {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.location";
		}

		@Override
		public String formatValue(Property p) {
			return I18n.t(p.getLocation());
		}
	},
	PROPERTY_TYPE {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.property-type";
		}

		@Override
		public String formatValue(Property p) {
			return I18n.t(p.getPropertyType());
		}
	},
	RENTAL_TYPE {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.rental";
		}

		@Override
		public String formatValue(Property p) {
			return I18n.t(p.getPropertyStatus());
		}
	},
	PRICE {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.price";
		}

		@Override
		public String formatValue(Property p) {
			return "<span class=\"tag price\">" + PropertyHelper.price(p)  + "</span>";
		}
	},
	VAT {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.vat";
		}

		@Override
		public String formatValue(Property p) {
			return I18n.t(p.isVatIncluded() ? "dictionary.vat-included" : "dictionary.vat-not-included");
		}
	},
	COVERED_AREA {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.covered-area";
		}

		@Override
		public String formatValue(Property p) {
			return p.getCoveredArea() + " " + I18n.t("dictionary.meters") + "<sup>2</sup>";
		}
	},
	PLOT_SIZE {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.plot-size";
		}

		@Override
		public String formatValue(Property p) {
			return p.getPlotSize() + " " + I18n.t("dictionary.meters") + "<sup>2</sup>";
		}
	},
	LEVELS {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.levels." + p.getPropertyType().name();
		}

		@Override
		public String formatValue(Property p) {
			return p.getLevels() > 0 ? String.valueOf(p.getLevels()) : Constants.NA;
		}
	},
	BEDROOMS {
		@Override
		public String getHeadingKey(Property p) {
			return Helper.pluralize("dictionary.bedrooms", p.getBedrooms());
		}

		@Override
		public String formatValue(Property p) {
			return Integer.toString(p.getBedrooms());
		}
	},
	GUESTS {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.guests";
		}

		@Override
		public String formatValue(Property p) {
			return Integer.toString(p.getGuests());
		}
	},
	FURNISHING {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.furnishing";
		}

		@Override
		public String formatValue(Property p) {
			return p.getFurnishing() == null ? "" : I18n.t(p.getFurnishing());
		}
	},
	HEATING_SYSTEM {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.heating-system";
		}

		@Override
		public String formatValue(Property p) {
			return yesNoValue(p.hasHeatingSystem());
		}
	},
	AIR_CONDITIONER {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.air-conditioner";
		}

		@Override
		public String formatValue(Property p) {
			return yesNoValue(p.hasAirConditioner());
		}
	},
	READY_TO_MOVE_IN {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.ready-to-move-in";
		}

		@Override
		public String formatValue(Property p) {
			return yesNoValue(p.isReadyToMoveIn());
		}
	},
	DISTANCE_TO_SEA {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.distance-to-sea";
		}

		@Override
		public String formatValue(Property p) {
			return PropertyHelper.distance(p.getDistanceToSea());
		}
	},
	DISTANCE_TO_SEA_SHORT {
		@Override
		public String getHeadingKey(Property p) {
			return "dictionary.distance-to-sea-short";
		}

		@Override
		public String formatValue(Property p) {
			return PropertyHelper.distance(p.getDistanceToSea());
		}
	};

	/**
	 * Returns the heading key of a field that can be resolved later through {@link MessageSource}.
	 */
	public abstract String getHeadingKey(Property p);

	/**
	 * Returns the formatted heading using the given {@link I18n} helper.
	 */
	public String formatHeadingKey(Property p) {
		return I18n.t(getHeadingKey(p));
	}

	/**
	 * Returns the formatted field value using the given {@link I18n} helper.
	 */
	public abstract String formatValue(Property p);

	protected String yesNoValue(boolean condition) {
		return I18n.t(condition ? "dictionary.yes" : "dictionary.no");
	}

	private static class Constants {
		public static final String NA = "N/A";
	}
}
