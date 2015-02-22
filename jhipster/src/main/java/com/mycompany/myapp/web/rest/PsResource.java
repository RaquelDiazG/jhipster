package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Ps;
import com.mycompany.myapp.repository.PsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Ps.
 */
@RestController
@RequestMapping("/app")
public class PsResource {

    private final Logger log = LoggerFactory.getLogger(PsResource.class);

    @Inject
    private PsRepository psRepository;

    /**
     * POST  /rest/pss -> Create a new ps.
     */
    @RequestMapping(value = "/rest/pss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Ps ps) {
        log.debug("REST request to save Ps : {}", ps);
        psRepository.save(ps);
    }

    /**
     * GET  /rest/pss -> get all the pss.
     */
    @RequestMapping(value = "/rest/pss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ps> getAll() {
        log.debug("REST request to get all Pss");
        return psRepository.findAll();
    }

    /**
     * GET  /rest/pss/:id -> get the "id" ps.
     */
    @RequestMapping(value = "/rest/pss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ps> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Ps : {}", id);
        Ps ps = psRepository.findOne(id);
        if (ps == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/pss/:id -> delete the "id" ps.
     */
    @RequestMapping(value = "/rest/pss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Ps : {}", id);
        psRepository.delete(id);
    }
}
