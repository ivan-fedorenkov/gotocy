package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.*;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * TODO: unit test
 * TODO: optimize / refactor : specifications, assets
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class ComplexForm {

	private static final Pattern STRING_SEPARATOR = Pattern.compile("[\n\r]+");
	private static final String STRINGS_JOINER = "\n";

	private static final Locale EN_LOCALE = Locale.ENGLISH;
	private static final Locale RU_LOCALE = new Locale("ru");

	// Developer
	private Long developerId;

	// Primary Contact
	private Long contactId;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String contactSpokenLanguages;

	private String title;
	private Location location;
	private String address;
	private String coordinates;

	// En fields
	private String enDescription;
	private String enSpecifications;

	// Ru fields
	private String ruDescription;
	private String ruSpecifications;

	// Assets
	private String representativeImageKey;
	private String imagesKeys;
	private String pdfFilesKeys;

	public ComplexForm() {
		location = Location.FAMAGUSTA;
	}

	public ComplexForm(Complex complex) {
		developerId = complex.getDeveloper() == null ? null : complex.getDeveloper().getId();

		contactId = complex.getPrimaryContact().getId();
		contactName = complex.getPrimaryContact().getName();
		contactPhone = complex.getPrimaryContact().getPhone();
		contactEmail = complex.getPrimaryContact().getEmail();
		contactSpokenLanguages = complex.getPrimaryContact().getSpokenLanguages();

		title = complex.getTitle();
		location = complex.getLocation();
		address = complex.getAddress();
		coordinates = complex.getCoordinates();

		enDescription = complex.getDescription(EN_LOCALE);
		enSpecifications = complex.getSpecifications(EN_LOCALE).stream().collect(joining(STRINGS_JOINER));

		ruDescription = complex.getDescription(RU_LOCALE);
		ruSpecifications = complex.getSpecifications(RU_LOCALE).stream().collect(joining(STRINGS_JOINER));

		imagesKeys = complex.getImages().stream().map(Image::getKey).collect(joining(STRINGS_JOINER));
		pdfFilesKeys = complex.getPdfFiles().stream().map(PdfFile::getKey).collect(joining(STRINGS_JOINER));

		if (complex.getRepresentativeImage() != null)
			representativeImageKey = complex.getRepresentativeImage().getKey();
	}

	public Contact mergeWithContact(Contact contact) {
		contact.setName(contactName);
		contact.setPhone(contactPhone);
		contact.setEmail(contactEmail);
		contact.setSpokenLanguages(contactSpokenLanguages);
		return contact;
	}

	public Complex mergeWithComplex(Complex complex) {
		complex.setTitle(title);
		complex.setLocation(location);
		complex.setAddress(address);
		complex.setCoordinates(coordinates);

		complex.setDescription(enDescription, EN_LOCALE);
		complex.setDescription(ruDescription, RU_LOCALE);

		List<String> enSpecificationsList = enSpecifications != null && !enSpecifications.isEmpty() ?
			Arrays.asList(STRING_SEPARATOR.split(enSpecifications)) : Collections.emptyList();
		complex.setSpecifications(enSpecificationsList, EN_LOCALE);

		List<String> ruSpecificationsList = ruSpecifications != null && !ruSpecifications.isEmpty() ?
			Arrays.asList(STRING_SEPARATOR.split(ruSpecifications)) : Collections.emptyList();
		complex.setSpecifications(ruSpecificationsList, RU_LOCALE);

		Image representativeImage = null;
		if (assetKeyIsDefined(representativeImageKey))
			representativeImage = new Image(representativeImageKey);

		// Only if representative image changed
		if (!Objects.equals(complex.getRepresentativeImage(), representativeImage))
			complex.setRepresentativeImage(representativeImage);

		List<Image> images = imagesKeys != null && !imagesKeys.isEmpty() ?
			Arrays.stream(STRING_SEPARATOR.split(imagesKeys))
				.filter(ComplexForm::assetKeyIsDefined)
				.map(Image::new)
				.collect(toList()) : Collections.emptyList();
		complex.setImages(images);

		List<PdfFile> pdfFiles = pdfFilesKeys != null && !pdfFilesKeys.isEmpty() ?
			Arrays.stream(STRING_SEPARATOR.split(pdfFilesKeys))
				.filter(ComplexForm::assetKeyIsDefined)
				.map(PdfFile::new)
				.collect(toList()) : Collections.emptyList();
		complex.setPdfFiles(pdfFiles);

		return complex;
	}

	private static boolean assetKeyIsDefined(String assetKey) {
		return assetKey != null && !assetKey.trim().isEmpty();
	}

}
