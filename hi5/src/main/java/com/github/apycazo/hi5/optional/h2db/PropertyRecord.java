package com.github.apycazo.hi5.optional.h2db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Andres Picazo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PropertyRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "ts", nullable = false)
    private long ts;
    @Column(name = "key", nullable = false)
    private String key;
    @Column(name = "value", nullable = true)
    private String value;

    @PrePersist
    public void beforePersist () {

        ts = System.currentTimeMillis();
    }

    @PreUpdate
    public void beforeUpdate () {

    }
}
