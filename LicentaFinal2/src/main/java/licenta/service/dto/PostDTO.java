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

    private String judet;

    private String city;

    private String category;

    private String subcategory;

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

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
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
            ", judet=" + getJudet() +
            ", city=" + getCity() +
            ", category=" + getCategory() +
            ", subcategory=" + getSubcategory() +
            "}";
    }
}
