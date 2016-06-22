package nox.apycazo.hibernateannotatedexample;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase persistente en base de datos con annotations.
 *
 * @author Chuidiang
 */
@Entity
@Table(name = "VUELO")
public class Flight {

    /**
     * El id
     */
    Long id;
    private String firstname;
    private Date fecha;

    /**
     * Persistente como clave y valor generado por la base de datos
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    /**
     * Persistente, un tipo basico (string)
     */
    @Basic
    @Column(name = "NOMBRE")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String toString() {
        return "Vuelo : " + id + " " + getFirstname() + " " + fecha;
    }

    /**
     * Una fecha. Debemos indicar si es DATE, TIME o TIMESTAMP
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
