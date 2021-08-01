package licenta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import licenta.IntegrationTest;
import licenta.domain.Category;
import licenta.domain.SubCategory;
import licenta.repository.SubCategoryRepository;
import licenta.service.EntityManager;
import licenta.service.dto.SubCategoryDTO;
import licenta.service.mapper.SubCategoryMapper;
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
 * Integration tests for the {@link SubCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
@WithMockUser
class SubCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private SubCategoryMapper subCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private SubCategory subCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategory createEntity(EntityManager em) {
        SubCategory subCategory = new SubCategory().name(DEFAULT_NAME);
        // Add required entity
        Category category;
        category = em.insert(CategoryResourceIT.createEntity(em)).block();
        subCategory.setCategory(category);
        return subCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubCategory createUpdatedEntity(EntityManager em) {
        SubCategory subCategory = new SubCategory().name(UPDATED_NAME);
        // Add required entity
        Category category;
        category = em.insert(CategoryResourceIT.createUpdatedEntity(em)).block();
        subCategory.setCategory(category);
        return subCategory;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(SubCategory.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        CategoryResourceIT.deleteEntities(em);
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        subCategory = createEntity(em);
    }

    @Test
    void createSubCategory() throws Exception {
        int databaseSizeBeforeCreate = subCategoryRepository.findAll().collectList().block().size();
        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createSubCategoryWithExistingId() throws Exception {
        // Create the SubCategory with an existing ID
        subCategory.setId(1L);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        int databaseSizeBeforeCreate = subCategoryRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoryRepository.findAll().collectList().block().size();
        // set the field null
        subCategory.setName(null);

        // Create the SubCategory, which fails.
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSubCategories() {
        // Initialize the database
        subCategoryRepository.save(subCategory).block();

        // Get all the subCategoryList
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
            .value(hasItem(subCategory.getId().intValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME));
    }

    @Test
    void getSubCategory() {
        // Initialize the database
        subCategoryRepository.save(subCategory).block();

        // Get the subCategory
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, subCategory.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(subCategory.getId().intValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME));
    }

    @Test
    void getNonExistingSubCategory() {
        // Get the subCategory
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.save(subCategory).block();

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();

        // Update the subCategory
        SubCategory updatedSubCategory = subCategoryRepository.findById(subCategory.getId()).block();
        updatedSubCategory.name(UPDATED_NAME);
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(updatedSubCategory);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, subCategoryDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, subCategoryDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSubCategoryWithPatch() throws Exception {
        // Initialize the database
        subCategoryRepository.save(subCategory).block();

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();

        // Update the subCategory using partial update
        SubCategory partialUpdatedSubCategory = new SubCategory();
        partialUpdatedSubCategory.setId(subCategory.getId());

        partialUpdatedSubCategory.name(UPDATED_NAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSubCategory.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategory))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void fullUpdateSubCategoryWithPatch() throws Exception {
        // Initialize the database
        subCategoryRepository.save(subCategory).block();

        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();

        // Update the subCategory using partial update
        SubCategory partialUpdatedSubCategory = new SubCategory();
        partialUpdatedSubCategory.setId(subCategory.getId());

        partialUpdatedSubCategory.name(UPDATED_NAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSubCategory.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSubCategory))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategoryList.get(subCategoryList.size() - 1);
        assertThat(testSubCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, subCategoryDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().collectList().block().size();
        subCategory.setId(count.incrementAndGet());

        // Create the SubCategory
        SubCategoryDTO subCategoryDTO = subCategoryMapper.toDto(subCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(subCategoryDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the SubCategory in the database
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSubCategory() {
        // Initialize the database
        subCategoryRepository.save(subCategory).block();

        int databaseSizeBeforeDelete = subCategoryRepository.findAll().collectList().block().size();

        // Delete the subCategory
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, subCategory.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<SubCategory> subCategoryList = subCategoryRepository.findAll().collectList().block();
        assertThat(subCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
