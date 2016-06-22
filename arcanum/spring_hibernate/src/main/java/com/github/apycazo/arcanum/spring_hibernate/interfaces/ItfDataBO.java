
package com.github.apycazo.arcanum.spring_hibernate.interfaces;

import com.github.apycazo.arcanum.spring_hibernate.implementations.DataModel;
import java.util.List;

/**
 * Data Business Object (Interface)
 * @author Andres Picazo
 */
public interface ItfDataBO {
    
    // Defines CRUD operations: Create, Read, Update, Delete
    public void create(DataModel data);
    public DataModel read(long id);
    public void update(DataModel data);
    public void delete(DataModel data);
    // EXtra
    public DataModel readByCode (int code);
    public List<DataModel> readAll ();
    public void deleteAll ();
    
}
