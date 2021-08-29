package licenta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;

import licenta.security.SecurityUtils;
import lombok.With;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Post.
 */
@Table("post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull(message = "must not be null")
    @Size(max = 60)
    @Column("title")
    private String title;

    @NotNull(message = "must not be null")
    @Size(max = 300)
    @Column("description")
    private String description;

    @Column("is_promoted")
    private Boolean is_promoted;

    @Column("created_at")
    private Instant created_at = Instant.now();

    @NotNull(message = "must not be null")
    @Column("price")
    private Float price;

    @Transient
    private User user_post;

    @Column("user_post_id")
    private Long user_postId;

    @Column("judet")
    private String judet;

    @Column("city")
    private String city;

    @Column("category")
    private String category;

    @Column("subcategory")
    private String subcategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Post description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_promoted() {
        return this.is_promoted;
    }

    public Post is_promoted(Boolean is_promoted) {
        this.is_promoted = is_promoted;
        return this;
    }

    public void setIs_promoted(Boolean is_promoted) {
        this.is_promoted = is_promoted;
    }

    public Instant getCreated_at() {
        return this.created_at;
    }

    public Post created_at(Instant created_at) {
        this.created_at = created_at;
        return this;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Float getPrice() {
        return this.price;
    }

    public Post price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public User getUser_post() {
        return this.user_post;
    }

    public Post user_post(User user) {
        this.setUser_post(user);
        this.user_postId = user != null ? user.getId() : null;
        return this;
    }

    public void setUser_post(User user) {
        this.user_post = user;
        this.user_postId = user != null ? user.getId() : null;
    }

    public String getJudet() {
        return judet;
    }
    public Post judet(String judet) {
        this.judet = judet;
        return this;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getCity() {
        return city;
    }

    public Post city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getUser_postId() {
        return this.user_postId;
    }

    public void setUser_postId(Long user) {
        this.user_postId = user;
    }

    public String getCategory() {
        return category;
    }

    public Post category(String category) {
        this.category=category;
        return this;
    }

    public Post subcategory(String subcategory) {
        this.subcategory=subcategory;
        return this;
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


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", is_promoted='" + getIs_promoted() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
