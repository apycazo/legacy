package com.github.apycazo.arcanum.spring_hibernate.implementations;

import com.github.apycazo.arcanum.spring_hibernate.interfaces.ItfDataDAO;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Data Access Model.
 * Warning: In hibernate use classes at queries, not sql element names (for example,
 * the from xxx where ..., xxx should be the class name DataModel, and NOT the table
 * name 'data').
 * @author Andres Picazo
 */

@Repository("dataDAO")
public class DataDAO extends DataHibernateSupport implements ItfDataDAO {

    public void create(DataModel data) {
        getHibernateTemplate().save(data);
    }

    public DataModel read(long id) {
        List list = getHibernateTemplate().find("from DataModel where id=?", id);
        return (DataModel)list.get(0);        
    }

    public void update(DataModel data) {
        getHibernateTemplate().update(data);
    }

    public void delete(DataModel data) {
        getHibernateTemplate().delete(data);
    }
    
    // Extra
    public DataModel readByCode (int code) {
        List list = getHibernateTemplate().find("from DataModel where code=?",code);
        return (DataModel)list.get(0);        
    }
    
    public List<DataModel> readAll () {
        List<DataModel> data = getHibernateTemplate().find("from DataModel");
        return data;
    }
    
    public void deleteAll () {
        getHibernateTemplate().deleteAll(readAll());
    }
    
}
