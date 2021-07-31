package licenta.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import licenta.IntegrationTest;
import licenta.domain.Judet;
import licenta.repository.JudetRepository;
import licenta.service.EntityManager;
import licenta.service.dto.JudetDTO;
import licenta.service.mapper.JudetMapper;
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
 * Integration tests for the {@link JudetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient
@WithMockUser
class JudetResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/judets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JudetRepository judetRepository;

    @Autowired
    private JudetMapper judetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Judet judet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Judet createEntity(EntityManager em) {
        Judet judet = new Judet().code(DEFAULT_CODE).name(DEFAULT_NAME);
        return judet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Judet createUpdatedEntity(EntityManager em) {
        Judet judet = new Judet().code(UPDATED_CODE).name(UPDATED_NAME);
        return judet;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Judet.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        judet = createEntity(em);
    }

    @Test
    void createJudet() throws Exception {
        int databaseSizeBeforeCreate = judetRepository.findAll().collectList().block().size();
        // Create the Judet
        JudetDTO judetDTO = judetMapper.toDto(judet);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeCreate + 1);
        Judet testJudet = judetList.get(judetList.size() - 1);
        assertThat(testJudet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJudet.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createJudetWithExistingId() throws Exception {
        // Create the Judet with an existing ID
        judet.setId(1L);
        JudetDTO judetDTO = judetMapper.toDto(judet);

        int databaseSizeBeforeCreate = judetRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = judetRepository.findAll().collectList().block().size();
        // set the field null
        judet.setCode(null);

        // Create the Judet, which fails.
        JudetDTO judetDTO = judetMapper.toDto(judet);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = judetRepository.findAll().collectList().block().size();
        // set the field null
        judet.setName(null);

        // Create the Judet, which fails.
        JudetDTO judetDTO = judetMapper.toDto(judet);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllJudets() {
        // Initialize the database
        judetRepository.save(judet).block();

        // Get all the judetList
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
            .value(hasItem(judet.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME));
    }

    @Test
    void getJudet() {
        // Initialize the database
        judetRepository.save(judet).block();

        // Get the judet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, judet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(judet.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME));
    }

    @Test
    void getNonExistingJudet() {
        // Get the judet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewJudet() throws Exception {
        // Initialize the database
        judetRepository.save(judet).block();

        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();

        // Update the judet
        Judet updatedJudet = judetRepository.findById(judet.getId()).block();
        updatedJudet.code(UPDATED_CODE).name(UPDATED_NAME);
        JudetDTO judetDTO = judetMapper.toDto(updatedJudet);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, judetDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
        Judet testJudet = judetList.get(judetList.size() - 1);
        assertThat(testJudet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJudet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingJudet() throws Exception {
        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();
        judet.setId(count.incrementAndGet());

        // Create the Judet
        JudetDTO judetDTO = judetMapper.toDto(judet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, judetDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchJudet() throws Exception {
        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();
        judet.setId(count.incrementAndGet());

        // Create the Judet
        JudetDTO judetDTO = judetMapper.toDto(judet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamJudet() throws Exception {
        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();
        judet.setId(count.incrementAndGet());

        // Create the Judet
        JudetDTO judetDTO = judetMapper.toDto(judet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateJudetWithPatch() throws Exception {
        // Initialize the database
        judetRepository.save(judet).block();

        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();

        // Update the judet using partial update
        Judet partialUpdatedJudet = new Judet();
        partialUpdatedJudet.setId(judet.getId());

        partialUpdatedJudet.code(UPDATED_CODE).name(UPDATED_NAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedJudet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedJudet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
        Judet testJudet = judetList.get(judetList.size() - 1);
        assertThat(testJudet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJudet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void fullUpdateJudetWithPatch() throws Exception {
        // Initialize the database
        judetRepository.save(judet).block();

        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();

        // Update the judet using partial update
        Judet partialUpdatedJudet = new Judet();
        partialUpdatedJudet.setId(judet.getId());

        partialUpdatedJudet.code(UPDATED_CODE).name(UPDATED_NAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedJudet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedJudet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
        Judet testJudet = judetList.get(judetList.size() - 1);
        assertThat(testJudet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJudet.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingJudet() throws Exception {
        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();
        judet.setId(count.incrementAndGet());

        // Create the Judet
        JudetDTO judetDTO = judetMapper.toDto(judet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, judetDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchJudet() throws Exception {
        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();
        judet.setId(count.incrementAndGet());

        // Create the Judet
        JudetDTO judetDTO = judetMapper.toDto(judet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamJudet() throws Exception {
        int databaseSizeBeforeUpdate = judetRepository.findAll().collectList().block().size();
        judet.setId(count.incrementAndGet());

        // Create the Judet
        JudetDTO judetDTO = judetMapper.toDto(judet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(judetDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Judet in the database
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteJudet() {
        // Initialize the database
        judetRepository.save(judet).block();

        int databaseSizeBeforeDelete = judetRepository.findAll().collectList().block().size();

        // Delete the judet
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, judet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Judet> judetList = judetRepository.findAll().collectList().block();
        assertThat(judetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
