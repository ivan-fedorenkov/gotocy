package org.gotocy.test.factory;

import org.gotocy.domain.Complex;
import org.gotocy.domain.Location;

/**
 * A factory class of the {@link org.gotocy.domain.Complex} entity.
 *
 * @author ifedorenkov
 */
public class ComplexFactory extends BaseFactory<Complex> {

	public static final ComplexFactory INSTANCE = new ComplexFactory();

	private ComplexFactory() {
	}

	@Override
	public Complex get() {
		Complex complex = new Complex();
		complex.setTitle(ANY_STRING);
		complex.setAddress(ANY_STRING);
		complex.setLocation(Location.LARNACA);
		complex.setCoordinates("[{lat:" + ANY_DOUBLE + ",lng:" + ANY_DOUBLE + "}]");
		return complex;
	}

}
