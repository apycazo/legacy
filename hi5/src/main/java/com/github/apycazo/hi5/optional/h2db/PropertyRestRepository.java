package com.github.apycazo.hi5.optional.h2db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * @author Andres Picazo
 */
@RepositoryRestResource
@ConditionalOnProperty(value = H2DBConfig.ACTIVATION_PROPERTY, havingValue = "true", matchIfMissing = false)
public interface PropertyRestRepository extends PagingAndSortingRepository<PropertyRecord, Long>, JpaSpecificationExecutor<PropertyRecord> {

    Page<PropertyRecord> findByTs (@Param("ts") long ts, Pageable pageable);
    Page<PropertyRecord> findByKey (@Param("key") String key, Pageable pageable);
    Page<PropertyRecord> findByValue (@Param("value") String value, Pageable pageable);

    Page<PropertyRecord> findByKeyAndValue (@Param("key") String key, @Param("value") String value, Pageable pageable);

    @Query("SELECT x from PropertyRecord x WHERE x.ts = :ts AND x.key = :key")
    Page<PropertyRecord> queryTest (@Param("ts") long ts, @Param("key") String key, Pageable pageable);

}
