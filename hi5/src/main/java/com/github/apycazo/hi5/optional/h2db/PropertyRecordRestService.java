package com.github.apycazo.hi5.optional.h2db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andres Picazo
 */
@Slf4j
//@RepositoryRestController
@RestController
@RequestMapping("find")
public class PropertyRecordRestService {

    public PropertyRecordRestService () {
        log.info("--- PropertyRecordRestService loaded");
    }

    @Autowired
    private PropertyRestRepository repository;

    @Autowired
    private PagedResourcesAssembler<PropertyRecord> pagedAssembler;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> processException (Exception ex) {

        log.error("Captured exception {} {}", ex.getClass().getSimpleName(), ex.getMessage());

        return new ResponseEntity<>("Captured exception " + ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "all1", method = RequestMethod.GET)
    public List<PropertyRecord> getTest1() {

        log.info("Testing");
        Map<String,Object> map = new HashMap<>();
        map.put("key","test.2");
        map.put("value","success");
        Specification<PropertyRecord> specification = PropertyRecordSpecs.mapSpec(map);
        List<PropertyRecord> results = repository.findAll(specification);
        return results;
    }

    @RequestMapping(value = "all2", method = RequestMethod.GET)
    public List<PropertyRecord> getTest2() {

        log.info("Testing");
        Specification<PropertyRecord> specification = PropertyRecordSpecs.testSpec("value", "success");
        List<PropertyRecord> results = repository.findAll(specification);
        return results;
    }
}
