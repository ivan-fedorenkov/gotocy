package org.gotocy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Embeddable contact details of domain entities.
 *
 * @author ifedorenkov
 */
@Embeddable
@Getter
@Setter
public class Contacts {

	public static final Contacts EMPTY = new EmptyContacts();

	private static final String SEPARATOR = ";";

	private String name;
	private String email;
	private String phone;
	private String spokenLanguages;

	public List<String> getSpokenLanguagesList() {
		return spokenLanguages == null ? Collections.emptyList() :
			Arrays.stream(spokenLanguages.split(";")).collect(toList());
	}

	public void setSpokenLanguagesList(List<String> spokenLanguagesList) {
		spokenLanguages = spokenLanguagesList == null ? null : spokenLanguagesList.stream()
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.collect(joining(SEPARATOR));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contacts contacts = (Contacts) o;
		return Objects.equals(name, contacts.name) &&
			Objects.equals(email, contacts.email) &&
			Objects.equals(phone, contacts.phone) &&
			Objects.equals(spokenLanguages, contacts.spokenLanguages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, email, phone, spokenLanguages);
	}

	private static class EmptyContacts extends Contacts {
		@Override
		public void setName(String name) {
		}

		@Override
		public void setEmail(String email) {
		}

		@Override
		public void setPhone(String phone) {
		}

		@Override
		public void setSpokenLanguages(String spokenLanguages) {
		}
	}

}
