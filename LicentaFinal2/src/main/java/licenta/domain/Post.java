package licenta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
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
    private Instant created_at;

    @NotNull(message = "must not be null")
    @Column("price")
    private Float price;

    @Transient
    private User user_post;

    @Column("user_post_id")
    private Long user_postId;

    @Transient
    private Judet judet_post;

    @Column("judet_post_id")
    private Long judet_postId;

    @JsonIgnoreProperties(value = { "judet" }, allowSetters = true)
    @Transient
    private City city_post;

    @Column("city_post_id")
    private Long city_postId;

    @Transient
    private Category category_post;

    @Column("category_post_id")
    private Long category_postId;

    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    @Transient
    private SubCategory subCategory_post;

    @Column("sub_category_post_id")
    private Long subCategory_postId;

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

    public Long getUser_postId() {
        return this.user_postId;
    }

    public void setUser_postId(Long user) {
        this.user_postId = user;
    }

    public Judet getJudet_post() {
        return this.judet_post;
    }

    public Post judet_post(Judet judet) {
        this.setJudet_post(judet);
        this.judet_postId = judet != null ? judet.getId() : null;
        return this;
    }

    public void setJudet_post(Judet judet) {
        this.judet_post = judet;
        this.judet_postId = judet != null ? judet.getId() : null;
    }

    public Long getJudet_postId() {
        return this.judet_postId;
    }

    public void setJudet_postId(Long judet) {
        this.judet_postId = judet;
    }

    public City getCity_post() {
        return this.city_post;
    }

    public Post city_post(City city) {
        this.setCity_post(city);
        this.city_postId = city != null ? city.getId() : null;
        return this;
    }

    public void setCity_post(City city) {
        this.city_post = city;
        this.city_postId = city != null ? city.getId() : null;
    }

    public Long getCity_postId() {
        return this.city_postId;
    }

    public void setCity_postId(Long city) {
        this.city_postId = city;
    }

    public Category getCategory_post() {
        return this.category_post;
    }

    public Post category_post(Category category) {
        this.setCategory_post(category);
        this.category_postId = category != null ? category.getId() : null;
        return this;
    }

    public void setCategory_post(Category category) {
        this.category_post = category;
        this.category_postId = category != null ? category.getId() : null;
    }

    public Long getCategory_postId() {
        return this.category_postId;
    }

    public void setCategory_postId(Long category) {
        this.category_postId = category;
    }

    public SubCategory getSubCategory_post() {
        return this.subCategory_post;
    }

    public Post subCategory_post(SubCategory subCategory) {
        this.setSubCategory_post(subCategory);
        this.subCategory_postId = subCategory != null ? subCategory.getId() : null;
        return this;
    }

    public void setSubCategory_post(SubCategory subCategory) {
        this.subCategory_post = subCategory;
        this.subCategory_postId = subCategory != null ? subCategory.getId() : null;
    }

    public Long getSubCategory_postId() {
        return this.subCategory_postId;
    }

    public void setSubCategory_postId(Long subCategory) {
        this.subCategory_postId = subCategory;
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
