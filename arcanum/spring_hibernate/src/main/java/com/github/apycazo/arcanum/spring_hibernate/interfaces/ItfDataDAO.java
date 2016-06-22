
package com.github.apycazo.arcanum.spring_hibernate.interfaces;

import com.github.apycazo.arcanum.spring_hibernate.implementations.DataModel;
import java.util.List;

/**
 * Data Access Object Interface. 
 * (same as ItfDataBO, actually).
 * @author Andres Picazo
 */
public interface ItfDataDAO {
    
    // Defines CRUD operations: Create, Read, Update, Delete
    public void create(DataModel data);
    public DataModel read(long id);
    public void update(DataModel data);
    public void delete(DataModel data);
    // Extra
    public DataModel readByCode (int code);
    public List<DataModel> readAll ();
    public void deleteAll ();
}
