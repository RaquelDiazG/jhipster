package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Fs;
import com.mycompany.myapp.repository.FsRepository;
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
 * REST controller for managing Fs.
 */
@RestController
@RequestMapping("/app")
public class FsResource {

    private final Logger log = LoggerFactory.getLogger(FsResource.class);

    @Inject
    private FsRepository fsRepository;

    /**
     * POST  /rest/fss -> Create a new fs.
     */
    @RequestMapping(value = "/rest/fss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Fs fs) {
        log.debug("REST request to save Fs : {}", fs);
        fsRepository.save(fs);
    }

    /**
     * GET  /rest/fss -> get all the fss.
     */
    @RequestMapping(value = "/rest/fss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fs> getAll() {
        log.debug("REST request to get all Fss");
        return fsRepository.findAll();
    }

    /**
     * GET  /rest/fss/:id -> get the "id" fs.
     */
    @RequestMapping(value = "/rest/fss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fs> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Fs : {}", id);
        Fs fs = fsRepository.findOne(id);
        if (fs == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fs, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/fss/:id -> delete the "id" fs.
     */
    @RequestMapping(value = "/rest/fss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Fs : {}", id);
        fsRepository.delete(id);
    }
}
