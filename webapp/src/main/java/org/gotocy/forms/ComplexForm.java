package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.config.Locales;
import org.gotocy.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

	// Developer
	private Long developerId;

	// Primary Contact
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

		Contacts complexContacts = complex.getContacts();
		if (complexContacts != null) {
			contactName = complexContacts.getName();
			contactPhone = complexContacts.getPhone();
			contactEmail = complexContacts.getEmail();
			contactSpokenLanguages = complexContacts.getSpokenLanguages();
		}

		title = complex.getTitle();
		location = complex.getLocation();
		address = complex.getAddress();
		coordinates = complex.getCoordinates();

		enDescription = complex.getDescription(Locales.EN);
		enSpecifications = complex.getSpecifications(Locales.EN).stream().collect(joining(STRINGS_JOINER));

		ruDescription = complex.getDescription(Locales.RU);
		ruSpecifications = complex.getSpecifications(Locales.RU).stream().collect(joining(STRINGS_JOINER));

		imagesKeys = complex.getImages().stream().map(Image::getKey).collect(joining(STRINGS_JOINER));
		pdfFilesKeys = complex.getPdfFiles().stream().map(PdfFile::getKey).collect(joining(STRINGS_JOINER));

		if (complex.getRepresentativeImage() != null)
			representativeImageKey = complex.getRepresentativeImage().getKey();
	}

	public Complex mergeWithComplex(Complex complex) {
		complex.setTitle(title);
		complex.setLocation(location);
		complex.setAddress(address);
		complex.setCoordinates(coordinates);

		Contacts complexContacts = complex.getContacts();
		if (complexContacts == null)
			complexContacts = new Contacts();
		complexContacts.setName(contactName);
		complexContacts.setEmail(contactEmail);
		complexContacts.setPhone(contactPhone);
		complexContacts.setSpokenLanguages(contactSpokenLanguages);

		complex.setDescription(enDescription, Locales.EN);
		complex.setDescription(ruDescription, Locales.RU);

		List<String> enSpecificationsList = enSpecifications != null && !enSpecifications.isEmpty() ?
			Arrays.asList(STRING_SEPARATOR.split(enSpecifications)) : Collections.emptyList();
		complex.setSpecifications(enSpecificationsList, Locales.EN);

		List<String> ruSpecificationsList = ruSpecifications != null && !ruSpecifications.isEmpty() ?
			Arrays.asList(STRING_SEPARATOR.split(ruSpecifications)) : Collections.emptyList();
		complex.setSpecifications(ruSpecificationsList, Locales.RU);

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
