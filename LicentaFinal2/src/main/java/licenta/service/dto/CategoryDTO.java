package licenta.service.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 40)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
