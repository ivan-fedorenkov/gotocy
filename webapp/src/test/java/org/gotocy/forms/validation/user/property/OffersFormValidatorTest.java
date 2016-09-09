package org.gotocy.forms.validation.user.property;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.forms.user.property.OffersForm;
import org.gotocy.test.factory.PropertyFactory;
import org.gotocy.test.factory.UserFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author ifedorenkov
 */
@RunWith(Parameterized.class)
public class OffersFormValidatorTest {
	private static final String FORM_OBJECT_NAME = "offersForm";

	private final GtcUser user;
	private final OfferStatus initialOfferStatus;
	private final OfferStatus changedOfferStatus;
	private final Consumer<Errors> assertion;

	private Property property;
	private OffersFormValidator validator;


	public OffersFormValidatorTest(GtcUser user, OfferStatus initialOfferStatus,
		OfferStatus changedOfferStatus, Consumer<Errors> assertion)
	{
		this.user = user;
		this.initialOfferStatus = initialOfferStatus;
		this.changedOfferStatus = changedOfferStatus;
		this.assertion = assertion;
	}

	@Before
	public void setUp() {
		property = PropertyFactory.INSTANCE.get(p -> p.setOfferStatus(initialOfferStatus));
		validator = new OffersFormValidator(user, property);
	}

	@Test
	public void testValidationForMaster() {
		OffersForm form = new OffersForm();
		form.setOfferType(property.getOfferType());
		form.setOfferStatus(changedOfferStatus);
		form.setPrice(1);

		Errors errors = new BeanPropertyBindingResult(form, FORM_OBJECT_NAME);
		validator.validate(form, errors);
		assertion.accept(errors);
	}

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[] {
				UserFactory.INSTANCE.get(), OfferStatus.ACTIVE, OfferStatus.PROMO,
				(Consumer<Errors>) e -> Assert.assertTrue(e.hasErrors())
			},
			new Object[] {
				UserFactory.INSTANCE.get(), OfferStatus.PROMO, OfferStatus.ACTIVE,
				(Consumer<Errors>) e -> Assert.assertTrue(e.hasErrors())
			},
			new Object[] {
				UserFactory.INSTANCE.getMaster(), OfferStatus.ACTIVE, OfferStatus.PROMO,
				(Consumer<Errors>) e -> Assert.assertFalse(e.hasErrors())
			},
			new Object[] {
				UserFactory.INSTANCE.getMaster(), OfferStatus.PROMO, OfferStatus.ACTIVE,
				(Consumer<Errors>) e -> Assert.assertFalse(e.hasErrors())
			}
		);
	}

}
