package com.github.apycazo.hi5.shared.interfaces;

import java.io.Serializable;

/**
 * @author Andres Picazo
 */
public interface Identifiable<ID extends Serializable> {

    ID getId();
}
