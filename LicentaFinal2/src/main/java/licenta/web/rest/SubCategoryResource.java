package licenta.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import licenta.repository.SubCategoryRepository;
import licenta.service.SubCategoryService;
import licenta.service.dto.SubCategoryDTO;
import licenta.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link licenta.domain.SubCategory}.
 */
@RestController
@RequestMapping("/api")
public class SubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoryResource.class);

    private static final String ENTITY_NAME = "subCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubCategoryService subCategoryService;

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryResource(SubCategoryService subCategoryService, SubCategoryRepository subCategoryRepository) {
        this.subCategoryService = subCategoryService;
        this.subCategoryRepository = subCategoryRepository;
    }

    /**
     * {@code POST  /sub-categories} : Create a new subCategory.
     *
     * @param subCategoryDTO the subCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subCategoryDTO, or with status {@code 400 (Bad Request)} if the subCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-categories")
    public Mono<ResponseEntity<SubCategoryDTO>> createSubCategory(@Valid @RequestBody SubCategoryDTO subCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save SubCategory : {}", subCategoryDTO);
        if (subCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new subCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return subCategoryService
            .save(subCategoryDTO)
            .map(
                result -> {
                    try {
                        return ResponseEntity
                            .created(new URI("/api/sub-categories/" + result.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                            .body(result);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }

    /**
     * {@code PUT  /sub-categories/:id} : Updates an existing subCategory.
     *
     * @param id the id of the subCategoryDTO to save.
     * @param subCategoryDTO the subCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the subCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-categories/{id}")
    public Mono<ResponseEntity<SubCategoryDTO>> updateSubCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubCategoryDTO subCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubCategory : {}, {}", id, subCategoryDTO);
        if (subCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return subCategoryRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    return subCategoryService
                        .save(subCategoryDTO)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                        .map(
                            result ->
                                ResponseEntity
                                    .ok()
                                    .headers(
                                        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString())
                                    )
                                    .body(result)
                        );
                }
            );
    }

    /**
     * {@code PATCH  /sub-categories/:id} : Partial updates given fields of an existing subCategory, field will ignore if it is null
     *
     * @param id the id of the subCategoryDTO to save.
     * @param subCategoryDTO the subCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the subCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-categories/{id}", consumes = "application/merge-patch+json")
    public Mono<ResponseEntity<SubCategoryDTO>> partialUpdateSubCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubCategoryDTO subCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubCategory partially : {}, {}", id, subCategoryDTO);
        if (subCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return subCategoryRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    Mono<SubCategoryDTO> result = subCategoryService.partialUpdate(subCategoryDTO);

                    return result
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                        .map(
                            res ->
                                ResponseEntity
                                    .ok()
                                    .headers(
                                        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, res.getId().toString())
                                    )
                                    .body(res)
                        );
                }
            );
    }

    /**
     * {@code GET  /sub-categories} : get all the subCategories.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subCategories in body.
     */
    @GetMapping("/sub-categories")
    public Mono<ResponseEntity<List<SubCategoryDTO>>> getAllSubCategories(Pageable pageable, ServerHttpRequest request) {
        log.debug("REST request to get a page of SubCategories");
        return subCategoryService
            .countAll()
            .zipWith(subCategoryService.findAll(pageable).collectList())
            .map(
                countWithEntities -> {
                    return ResponseEntity
                        .ok()
                        .headers(
                            PaginationUtil.generatePaginationHttpHeaders(
                                UriComponentsBuilder.fromHttpRequest(request),
                                new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                            )
                        )
                        .body(countWithEntities.getT2());
                }
            );
    }

    /**
     * {@code GET  /sub-categories/:id} : get the "id" subCategory.
     *
     * @param id the id of the subCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-categories/{id}")
    public Mono<ResponseEntity<SubCategoryDTO>> getSubCategory(@PathVariable Long id) {
        log.debug("REST request to get SubCategory : {}", id);
        Mono<SubCategoryDTO> subCategoryDTO = subCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subCategoryDTO);
    }

    /**
     * {@code DELETE  /sub-categories/:id} : delete the "id" subCategory.
     *
     * @param id the id of the subCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-categories/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete SubCategory : {}", id);
        return subCategoryService
            .delete(id)
            .map(
                result ->
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                        .build()
            );
    }
}
