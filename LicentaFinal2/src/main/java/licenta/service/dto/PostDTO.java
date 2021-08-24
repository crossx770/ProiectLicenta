package licenta.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link licenta.domain.Post} entity.
 */
public class PostDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    @Size(max = 60)
    private String title;

    @NotNull(message = "must not be null")
    @Size(max = 300)
    private String description;

    private Boolean is_promoted;

    private Instant created_at;

    @NotNull(message = "must not be null")
    private Float price;

    private UserDTO user_post;

    private JudetDTO judet_post;

    private CityDTO city_post;

    private CategoryDTO category_post;

    private SubCategoryDTO subCategory_post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_promoted() {
        return is_promoted;
    }

    public void setIs_promoted(Boolean is_promoted) {
        this.is_promoted = is_promoted;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public UserDTO getUser_post() {
        return user_post;
    }

    public void setUser_post(UserDTO user_post) {
        this.user_post = user_post;
    }

    public JudetDTO getJudet_post() {
        return judet_post;
    }

    public void setJudet_post(JudetDTO judet_post) {
        this.judet_post = judet_post;
    }

    public CityDTO getCity_post() {
        return city_post;
    }

    public void setCity_post(CityDTO city_post) {
        this.city_post = city_post;
    }

    public CategoryDTO getCategory_post() {
        return category_post;
    }

    public void setCategory_post(CategoryDTO category_post) {
        this.category_post = category_post;
    }

    public SubCategoryDTO getSubCategory_post() {
        return subCategory_post;
    }

    public void setSubCategory_post(SubCategoryDTO subCategory_post) {
        this.subCategory_post = subCategory_post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", is_promoted='" + getIs_promoted() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", price=" + getPrice() +
            ", user_post=" + getUser_post() +
            ", judet_post=" + getJudet_post() +
            ", city_post=" + getCity_post() +
            ", category_post=" + getCategory_post() +
            ", subCategory_post=" + getSubCategory_post() +
            "}";
    }
}
