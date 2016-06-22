package com.github.apycazo.hi5.optional.directory;

import com.github.apycazo.hi5.shared.domains.OperationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andres Picazo
 */
@Slf4j
@RestController
@RequestMapping(value = DirectoryConfig.MAPPING_PATH)
@ConditionalOnProperty(value = DirectoryConfig.ACTIVATION_PROPERTY, havingValue = "true", matchIfMissing = false)
public class DirectoryRestCtrl {

    public DirectoryRestCtrl () {

        log.info("-- Initializing rest ctrl on {}", DirectoryConfig.MAPPING_PATH);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public OperationStatus heartbeat () {

        OperationStatus os = OperationStatus.success();
        log.info("Heartbeat on ts {}", os.getTs());
        return os;
    }
}
