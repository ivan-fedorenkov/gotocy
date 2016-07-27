package org.gotocy.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

/**
 * @author ifedorenkov
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Long> {

	@Override
	public Long convertToDatabaseColumn(LocalDate date) {
		return date == null ? null : date.toEpochDay();
	}

	@Override
	public LocalDate convertToEntityAttribute(Long epochDay) {
		return epochDay == null ? null : LocalDate.ofEpochDay(epochDay);
	}

}
