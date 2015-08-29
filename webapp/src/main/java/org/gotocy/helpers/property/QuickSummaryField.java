package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.gotocy.helpers.Helper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Objects;

/**
 * @author ifedorenkov
 */
enum QuickSummaryField {
	LOCATION {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.location";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return ms.getMessage(p.getLocation(), LocaleContextHolder.getLocale());
		}
	},
	PROPERTY_TYPE {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.property-type";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return ms.getMessage(p.getPropertyType(), LocaleContextHolder.getLocale());
		}
	},
	RENTAL_TYPE {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.rental";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return ms.getMessage(p.getPropertyStatus(), LocaleContextHolder.getLocale());
		}
	},
	PRICE {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.price";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return "<span class=\"tag price\">" + PropertyHelper.price(ms, p)  + "</span>";
		}
	},
	VAT {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.vat";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return ms.getMessage(
				Boolean.TRUE.equals(p.getVatIncluded()) ?
					"org.gotocy.dictionary.vat-included" : "org.gotocy.dictionary.vat-not-included",
				new Object[0], LocaleContextHolder.getLocale());
		}
	},
	COVERED_AREA {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.covered-area";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return Objects.toString(p.getCoveredArea(), "0") + " " +
				ms.getMessage("org.gotocy.dictionary.meters", new Object[0], LocaleContextHolder.getLocale()) +
				"<sup>2</sup>";
		}
	},
	PLOT_SIZE {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.plot-size";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return Objects.toString(p.getPlotSize(), "0") + " " +
				ms.getMessage("org.gotocy.dictionary.meters", new Object[0], LocaleContextHolder.getLocale()) +
				"<sup>2</sup>";
		}
	},
	LEVELS {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.levels." + p.getPropertyType().name();
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return Objects.toString(p.getLevels(), "0");
		}
	},
	BEDROOMS {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return Helper.pluralize("org.gotocy.dictionary.bedrooms", p.getBedrooms() == null ? 0 : p.getBedrooms());
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return Objects.toString(p.getBedrooms(), "0");
		}
	},
	GUESTS {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.guests";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return Objects.toString(p.getGuests(), "0");
		}
	},
	FURNISHING {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.furnishing";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return p.getFurnishing() == null ? "" : ms.getMessage(p.getFurnishing(), LocaleContextHolder.getLocale());
		}
	},
	HEATING_SYSTEM {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.heating-system";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return yesNoValue(ms, p.getHeatingSystem());
		}
	},
	AIR_CONDITIONER {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.air-conditioner";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return yesNoValue(ms, p.getAirConditioner());
		}
	},
	READY_TO_MOVE_IN {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.ready-to-move-in";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return yesNoValue(ms, p.getReadyToMoveIn());
		}
	},
	DISTANCE_TO_SEA {
		@Override
		protected String dtTextKey(MessageSource ms, Property p) {
			return "org.gotocy.dictionary.distance-to-sea";
		}

		@Override
		protected String ddValue(MessageSource ms, Property p) {
			return PropertyHelper.distance(ms, p.getDistanceToSea() == null ? 0 : p.getDistanceToSea());
		}
	};

	protected abstract String dtTextKey(MessageSource ms, Property p);
	protected abstract String ddValue(MessageSource ms, Property p);

	protected String yesNoValue(MessageSource ms, Boolean condition) {
		return ms.getMessage(Boolean.TRUE.equals(condition) ?
				"org.gotocy.dictionary.yes" : "org.gotocy.dictionary.no",
			new Object[0], LocaleContextHolder.getLocale());
	}

	private String dt(MessageSource ms, String key) {
		return "<dt>" + ms.getMessage(key, new Object[0], LocaleContextHolder.getLocale()) + "</dt>";
	}

	private String dd(String value) {
		return "<dd>" + value + "</dd>";
	}

	public String generateHtml(MessageSource ms, Property p) {
		return dt(ms, dtTextKey(ms, p)) + dd(ddValue(ms, p));
	}

}
