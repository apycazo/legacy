package com.github.apycazo.hi5.shared.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Andres Picazo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationStatus {

    private boolean success = true;
    private long ts = System.currentTimeMillis();
    private String info = null;
    private String source = null;

    public static OperationStatus success () {

        return new OperationStatus();
    }

    public static OperationStatus failure () {

        OperationStatus os = new OperationStatus();
        os.setSuccess(false);
        return os;
    }
}
