package com.github.apycazo.arcanum.spring_hibernate.implementations;

import com.github.apycazo.arcanum.spring_hibernate.interfaces.ItfDataBO;
import com.github.apycazo.arcanum.spring_hibernate.interfaces.ItfDataDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Andres Picazo
 */

@Service("dataBO")
public class DataBO implements ItfDataBO {
    
    @Autowired
    private ItfDataDAO dao;

    public void create(DataModel data) {
        dao.create(data);
    }

    public DataModel read(long id) {
        return dao.read(id);
    }

    public void update(DataModel data) {
        dao.update(data);
    }

    public void delete(DataModel data) {
        dao.delete(data);
    }
    
    // Extra
    public DataModel readByCode (int code) {
        return dao.readByCode(code);
    }
    
    public List<DataModel> readAll () {
        return dao.readAll();
    }
    
    public void deleteAll () {
        dao.deleteAll();
    }
    
}
