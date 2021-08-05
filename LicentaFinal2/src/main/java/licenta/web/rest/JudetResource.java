package licenta.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import licenta.repository.JudetRepository;
import licenta.service.JudetService;
import licenta.service.dto.JudetDTO;
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
import reactor.core.publisher.Flux;

/**
 * REST controller for managing {@link licenta.domain.Judet}.
 */
@RestController
@RequestMapping("/api")
public class JudetResource {

    private final Logger log = LoggerFactory.getLogger(JudetResource.class);

    private static final String ENTITY_NAME = "judet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JudetService judetService;

    private final JudetRepository judetRepository;

    public JudetResource(JudetService judetService, JudetRepository judetRepository) {
        this.judetService = judetService;
        this.judetRepository = judetRepository;
    }

    /**
     * {@code POST  /judets} : Create a new judet.
     *
     * @param judetDTO the judetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new judetDTO, or with status {@code 400 (Bad Request)} if the judet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/judets")
    public Mono<ResponseEntity<JudetDTO>> createJudet(@Valid @RequestBody JudetDTO judetDTO) throws URISyntaxException {
        log.debug("REST request to save Judet : {}", judetDTO);
        if (judetDTO.getId() != null) {
            throw new BadRequestAlertException("A new judet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return judetService
            .save(judetDTO)
            .map(
                result -> {
                    try {
                        return ResponseEntity
                            .created(new URI("/api/judets/" + result.getId()))
                            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                            .body(result);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }

    /**
     * {@code PUT  /judets/:id} : Updates an existing judet.
     *
     * @param id the id of the judetDTO to save.
     * @param judetDTO the judetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated judetDTO,
     * or with status {@code 400 (Bad Request)} if the judetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the judetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/judets/{id}")
    public Mono<ResponseEntity<JudetDTO>> updateJudet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody JudetDTO judetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Judet : {}, {}", id, judetDTO);
        if (judetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, judetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return judetRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    return judetService
                        .save(judetDTO)
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
     * {@code PATCH  /judets/:id} : Partial updates given fields of an existing judet, field will ignore if it is null
     *
     * @param id the id of the judetDTO to save.
     * @param judetDTO the judetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated judetDTO,
     * or with status {@code 400 (Bad Request)} if the judetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the judetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the judetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/judets/{id}", consumes = "application/merge-patch+json")
    public Mono<ResponseEntity<JudetDTO>> partialUpdateJudet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody JudetDTO judetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Judet partially : {}, {}", id, judetDTO);
        if (judetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, judetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return judetRepository
            .existsById(id)
            .flatMap(
                exists -> {
                    if (!exists) {
                        return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                    }

                    Mono<JudetDTO> result = judetService.partialUpdate(judetDTO);

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
     * {@code GET  /judets} : get all the judets.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of judets in body.
     */
    @GetMapping("/judets")
    public Mono<ResponseEntity<List<JudetDTO>>> getAllJudets(Pageable pageable, ServerHttpRequest request) {
        log.debug("REST request to get a page of Judets");
        return judetService
            .countAll()
            .zipWith(judetService.findAll(pageable).collectList())
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


    @GetMapping("/judets/all")
    public Mono<ResponseEntity<List<JudetDTO>>> getAllJudets() {
        log.debug("REST request to get all judets");
        return judetService
            .countAll()
            .zipWith(judetService.findAllWithoutPagination().collectList())
            .map(
                list -> {
                    return ResponseEntity.ok().body(list.getT2());
                }
            );
    }

    /**
     * {@code GET  /judets/:id} : get the "id" judet.
     *
     * @param id the id of the judetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the judetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/judets/{id}")
    public Mono<ResponseEntity<JudetDTO>> getJudet(@PathVariable Long id) {
        log.debug("REST request to get Judet : {}", id);
        Mono<JudetDTO> judetDTO = judetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(judetDTO);
    }

    /**
     * {@code DELETE  /judets/:id} : delete the "id" judet.
     *
     * @param id the id of the judetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/judets/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteJudet(@PathVariable Long id) {
        log.debug("REST request to delete Judet : {}", id);
        return judetService
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
