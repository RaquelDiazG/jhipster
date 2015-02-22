package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.F;
import com.mycompany.myapp.repository.FRepository;
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
 * REST controller for managing F.
 */
@RestController
@RequestMapping("/app")
public class FResource {

    private final Logger log = LoggerFactory.getLogger(FResource.class);

    @Inject
    private FRepository fRepository;

    /**
     * POST  /rest/fs -> Create a new f.
     */
    @RequestMapping(value = "/rest/fs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody F f) {
        log.debug("REST request to save F : {}", f);
        fRepository.save(f);
    }

    /**
     * GET  /rest/fs -> get all the fs.
     */
    @RequestMapping(value = "/rest/fs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<F> getAll() {
        log.debug("REST request to get all Fs");
        return fRepository.findAll();
    }

    /**
     * GET  /rest/fs/:id -> get the "id" f.
     */
    @RequestMapping(value = "/rest/fs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<F> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get F : {}", id);
        F f = fRepository.findOne(id);
        if (f == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/fs/:id -> delete the "id" f.
     */
    @RequestMapping(value = "/rest/fs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete F : {}", id);
        fRepository.delete(id);
    }
}
