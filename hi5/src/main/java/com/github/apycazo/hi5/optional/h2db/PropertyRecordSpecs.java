package com.github.apycazo.hi5.optional.h2db;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Andres Picazo
 */
public class PropertyRecordSpecs {

    public static Specification<PropertyRecord> testSpec (String key, String value) {

        return new Specification<PropertyRecord>() {
            @Override
            public Predicate toPredicate(Root<PropertyRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                // root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
                return criteriaBuilder.equal(root.get(key), value);
            }
        };
    }

    public static Specification<PropertyRecord> mapSpec (Map<String,Object> queryMap) {

        return new Specification<PropertyRecord>() {
            @Override
            public Predicate toPredicate(Root<PropertyRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate result = null;

                Iterator<String> keyIterator = queryMap.keySet().iterator();
                while (keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    if (root.get(key) != null) {
                        Object value = queryMap.get(key);
                        result = criteriaBuilder.and(criteriaBuilder.equal(root.get(key), value));
                    }
                }
                return result;
            }
        };
    }
}
