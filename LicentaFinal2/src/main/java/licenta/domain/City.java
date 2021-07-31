package licenta.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A City.
 */
@Table("city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 30)
    @Column("name")
    private String name;

    @Transient
    private Judet judet;

    @Column("judet_id")
    private Long judetId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public City name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Judet getJudet() {
        return this.judet;
    }

    public City judet(Judet judet) {
        this.setJudet(judet);
        this.judetId = judet != null ? judet.getId() : null;
        return this;
    }

    public void setJudet(Judet judet) {
        this.judet = judet;
        this.judetId = judet != null ? judet.getId() : null;
    }

    public Long getJudetId() {
        return this.judetId;
    }

    public void setJudetId(Long judet) {
        this.judetId = judet;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
