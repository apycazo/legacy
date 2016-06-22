package com.github.apycazo.hi5.shared.interfaces;

import java.io.Serializable;

/**
 * @author Andres Picazo
 */
public interface GroupIdentifiable<ID extends Serializable> extends Identifiable<ID> {

    ID getGroupId();

}
