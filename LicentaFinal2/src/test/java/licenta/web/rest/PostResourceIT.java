package licenta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import licenta.IntegrationTest;
import licenta.domain.Category;
import licenta.domain.City;
import licenta.domain.Judet;
import licenta.domain.Post;
import licenta.domain.SubCategory;
import licenta.domain.User;
import licenta.repository.PostRepository;
import licenta.service.EntityManager;
import licenta.service.dto.PostDTO;
import licenta.service.mapper.PostMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link PostResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
@WithMockUser
class PostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_JUDET = "AAAAAAAAAA";
    private static final String UPDATED_JUDET = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SUBCATEGORY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PROMOTED = false;
    private static final Boolean UPDATED_IS_PROMOTED = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final String ENTITY_API_URL = "/api/posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Post post;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .is_promoted(DEFAULT_IS_PROMOTED)
            .created_at(DEFAULT_CREATED_AT)
            .price(DEFAULT_PRICE)
            .city(DEFAULT_CITY)
            .judet(DEFAULT_JUDET);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity(em)).block();
        post.setUser_post(user);
        return post;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post post = new Post()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .is_promoted(UPDATED_IS_PROMOTED)
            .created_at(UPDATED_CREATED_AT)
            .price(UPDATED_PRICE)
            .city(UPDATED_CITY)
            .judet(UPDATED_JUDET)
            .category(UPDATED_CATEGORY)
            .subcategory(UPDATED_SUBCATEGORY);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity(em)).block();
        post.setUser_post(user);
        return post;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Post.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        UserResourceIT.deleteEntities(em);
        JudetResourceIT.deleteEntities(em);
        CityResourceIT.deleteEntities(em);
        CategoryResourceIT.deleteEntities(em);
        SubCategoryResourceIT.deleteEntities(em);
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        post = createEntity(em);
    }

    @Test
    void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().collectList().block().size();
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPost.getIs_promoted()).isEqualTo(DEFAULT_IS_PROMOTED);
        assertThat(testPost.getCreated_at()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPost.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    void createPostWithExistingId() throws Exception {
        // Create the Post with an existing ID
        post.setId(1L);
        PostDTO postDTO = postMapper.toDto(post);

        int databaseSizeBeforeCreate = postRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().collectList().block().size();
        // set the field null
        post.setTitle(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().collectList().block().size();
        // set the field null
        post.setDescription(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = postRepository.findAll().collectList().block().size();
        // set the field null
        post.setPrice(null);

        // Create the Post, which fails.
        PostDTO postDTO = postMapper.toDto(post);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPosts() {
        // Initialize the database
        postRepository.save(post).block();

        // Get all the postList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(post.getId().intValue()))
            .jsonPath("$.[*].title")
            .value(hasItem(DEFAULT_TITLE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].is_promoted")
            .value(hasItem(DEFAULT_IS_PROMOTED.booleanValue()))
            .jsonPath("$.[*].created_at")
            .value(hasItem(DEFAULT_CREATED_AT.toString()))
            .jsonPath("$.[*].price")
            .value(hasItem(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    void getPost() {
        // Initialize the database
        postRepository.save(post).block();

        // Get the post
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, post.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(post.getId().intValue()))
            .jsonPath("$.title")
            .value(is(DEFAULT_TITLE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.is_promoted")
            .value(is(DEFAULT_IS_PROMOTED.booleanValue()))
            .jsonPath("$.created_at")
            .value(is(DEFAULT_CREATED_AT.toString()))
            .jsonPath("$.price")
            .value(is(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    void getNonExistingPost() {
        // Get the post
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewPost() throws Exception {
        // Initialize the database
        postRepository.save(post).block();

        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).block();
        updatedPost
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .is_promoted(UPDATED_IS_PROMOTED)
            .created_at(UPDATED_CREATED_AT)
            .price(UPDATED_PRICE);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, postDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPost.getIs_promoted()).isEqualTo(UPDATED_IS_PROMOTED);
        assertThat(testPost.getCreated_at()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    void putNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, postDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePostWithPatch() throws Exception {
        // Initialize the database
        postRepository.save(post).block();

        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost.created_at(UPDATED_CREATED_AT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPost.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPost))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPost.getIs_promoted()).isEqualTo(DEFAULT_IS_PROMOTED);
        assertThat(testPost.getCreated_at()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPost.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    void fullUpdatePostWithPatch() throws Exception {
        // Initialize the database
        postRepository.save(post).block();

        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .is_promoted(UPDATED_IS_PROMOTED)
            .created_at(UPDATED_CREATED_AT)
            .price(UPDATED_PRICE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedPost.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedPost))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPost.getIs_promoted()).isEqualTo(UPDATED_IS_PROMOTED);
        assertThat(testPost.getCreated_at()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    void patchNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, postDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().collectList().block().size();
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(postDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePost() {
        // Initialize the database
        postRepository.save(post).block();

        int databaseSizeBeforeDelete = postRepository.findAll().collectList().block().size();

        // Delete the post
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, post.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Post> postList = postRepository.findAll().collectList().block();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
