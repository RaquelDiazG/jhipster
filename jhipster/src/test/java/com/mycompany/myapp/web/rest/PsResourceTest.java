package com.mycompany.myapp.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Ps;
import com.mycompany.myapp.repository.PsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PsResource REST controller.
 *
 * @see PsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PsResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    

    @Inject
    private PsRepository psRepository;

    private MockMvc restPsMockMvc;

    private Ps ps;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PsResource psResource = new PsResource();
        ReflectionTestUtils.setField(psResource, "psRepository", psRepository);
        this.restPsMockMvc = MockMvcBuilders.standaloneSetup(psResource).build();
    }

    @Before
    public void initTest() {
        ps = new Ps();
        ps.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createPs() throws Exception {
        // Validate the database is empty
        assertThat(psRepository.findAll()).hasSize(0);

        // Create the Ps
        restPsMockMvc.perform(post("/app/rest/pss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ps)))
                .andExpect(status().isOk());

        // Validate the Ps in the database
        List<Ps> pss = psRepository.findAll();
        assertThat(pss).hasSize(1);
        Ps testPs = pss.iterator().next();
        assertThat(testPs.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPss() throws Exception {
        // Initialize the database
        psRepository.saveAndFlush(ps);

        // Get all the pss
        restPsMockMvc.perform(get("/app/rest/pss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(ps.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getPs() throws Exception {
        // Initialize the database
        psRepository.saveAndFlush(ps);

        // Get the ps
        restPsMockMvc.perform(get("/app/rest/pss/{id}", ps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ps.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPs() throws Exception {
        // Get the ps
        restPsMockMvc.perform(get("/app/rest/pss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePs() throws Exception {
        // Initialize the database
        psRepository.saveAndFlush(ps);

        // Update the ps
        ps.setNombre(UPDATED_NOMBRE);
        restPsMockMvc.perform(post("/app/rest/pss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ps)))
                .andExpect(status().isOk());

        // Validate the Ps in the database
        List<Ps> pss = psRepository.findAll();
        assertThat(pss).hasSize(1);
        Ps testPs = pss.iterator().next();
        assertThat(testPs.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deletePs() throws Exception {
        // Initialize the database
        psRepository.saveAndFlush(ps);

        // Get the ps
        restPsMockMvc.perform(delete("/app/rest/pss/{id}", ps.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ps> pss = psRepository.findAll();
        assertThat(pss).hasSize(0);
    }
}
