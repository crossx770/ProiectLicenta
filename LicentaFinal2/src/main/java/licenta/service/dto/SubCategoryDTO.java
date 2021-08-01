package licenta.service.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.relational.core.mapping.Column;

public class SubCategoryDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    @Size(min = 1, max = 40)
    private String name;

    private CategoryDTO category;

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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SubcategoryDTO{" + "id=" + id + ", name='" + name + '\'' + ", categoryDTO=" + category + '}';
    }
}
