package com.github.apycazo.arcanum.spring_hibernate.implementations;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Model to store data. (primary-key)<long>id, <varchar(20)> name, <int(2)> age,
 * (unique)<int(3)>code
 *
 * @author Andres Picazo
 */

@Entity
@Table(name = "data")
public class DataModel implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id; // This will be used as the data id
    @Column(name = "NAME", unique = false, nullable = false, length = 20)
    private String name;
    @Column(name = "AGE", unique = false, nullable = false, length = 2)
    private int age;
    @Column(name = "CODE", unique = true, nullable = false, length = 3)
    private int code;
    @Transient
    private boolean isAdult;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
        this.isAdult = this.age > 18;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    // Transient: Not mapped to DB
    public boolean getIsAdult() {
        this.isAdult = this.age > 18;
        return this.isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    // Extra ===================================================================
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id);
        sb.append(" Name: ").append(this.name);
        sb.append(" Age: ").append(this.age);
        sb.append(" Code: ").append(this.code);
        return sb.toString();
    }

}
