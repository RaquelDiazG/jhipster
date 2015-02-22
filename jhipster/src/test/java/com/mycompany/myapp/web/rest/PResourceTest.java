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
import com.mycompany.myapp.domain.P;
import com.mycompany.myapp.repository.PRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PResource REST controller.
 *
 * @see PResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    

    @Inject
    private PRepository pRepository;

    private MockMvc restPMockMvc;

    private P p;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PResource pResource = new PResource();
        ReflectionTestUtils.setField(pResource, "pRepository", pRepository);
        this.restPMockMvc = MockMvcBuilders.standaloneSetup(pResource).build();
    }

    @Before
    public void initTest() {
        p = new P();
        p.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createP() throws Exception {
        // Validate the database is empty
        assertThat(pRepository.findAll()).hasSize(0);

        // Create the P
        restPMockMvc.perform(post("/app/rest/ps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(p)))
                .andExpect(status().isOk());

        // Validate the P in the database
        List<P> ps = pRepository.findAll();
        assertThat(ps).hasSize(1);
        P testP = ps.iterator().next();
        assertThat(testP.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPs() throws Exception {
        // Initialize the database
        pRepository.saveAndFlush(p);

        // Get all the ps
        restPMockMvc.perform(get("/app/rest/ps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(p.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getP() throws Exception {
        // Initialize the database
        pRepository.saveAndFlush(p);

        // Get the p
        restPMockMvc.perform(get("/app/rest/ps/{id}", p.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(p.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingP() throws Exception {
        // Get the p
        restPMockMvc.perform(get("/app/rest/ps/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateP() throws Exception {
        // Initialize the database
        pRepository.saveAndFlush(p);

        // Update the p
        p.setNombre(UPDATED_NOMBRE);
        restPMockMvc.perform(post("/app/rest/ps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(p)))
                .andExpect(status().isOk());

        // Validate the P in the database
        List<P> ps = pRepository.findAll();
        assertThat(ps).hasSize(1);
        P testP = ps.iterator().next();
        assertThat(testP.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteP() throws Exception {
        // Initialize the database
        pRepository.saveAndFlush(p);

        // Get the p
        restPMockMvc.perform(delete("/app/rest/ps/{id}", p.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<P> ps = pRepository.findAll();
        assertThat(ps).hasSize(0);
    }
}
