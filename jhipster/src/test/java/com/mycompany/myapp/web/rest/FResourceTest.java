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
import com.mycompany.myapp.domain.F;
import com.mycompany.myapp.repository.FRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FResource REST controller.
 *
 * @see FResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    

    @Inject
    private FRepository fRepository;

    private MockMvc restFMockMvc;

    private F f;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FResource fResource = new FResource();
        ReflectionTestUtils.setField(fResource, "fRepository", fRepository);
        this.restFMockMvc = MockMvcBuilders.standaloneSetup(fResource).build();
    }

    @Before
    public void initTest() {
        f = new F();
        f.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createF() throws Exception {
        // Validate the database is empty
        assertThat(fRepository.findAll()).hasSize(0);

        // Create the F
        restFMockMvc.perform(post("/app/rest/fs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(f)))
                .andExpect(status().isOk());

        // Validate the F in the database
        List<F> fs = fRepository.findAll();
        assertThat(fs).hasSize(1);
        F testF = fs.iterator().next();
        assertThat(testF.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFs() throws Exception {
        // Initialize the database
        fRepository.saveAndFlush(f);

        // Get all the fs
        restFMockMvc.perform(get("/app/rest/fs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(f.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getF() throws Exception {
        // Initialize the database
        fRepository.saveAndFlush(f);

        // Get the f
        restFMockMvc.perform(get("/app/rest/fs/{id}", f.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(f.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingF() throws Exception {
        // Get the f
        restFMockMvc.perform(get("/app/rest/fs/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateF() throws Exception {
        // Initialize the database
        fRepository.saveAndFlush(f);

        // Update the f
        f.setNombre(UPDATED_NOMBRE);
        restFMockMvc.perform(post("/app/rest/fs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(f)))
                .andExpect(status().isOk());

        // Validate the F in the database
        List<F> fs = fRepository.findAll();
        assertThat(fs).hasSize(1);
        F testF = fs.iterator().next();
        assertThat(testF.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteF() throws Exception {
        // Initialize the database
        fRepository.saveAndFlush(f);

        // Get the f
        restFMockMvc.perform(delete("/app/rest/fs/{id}", f.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<F> fs = fRepository.findAll();
        assertThat(fs).hasSize(0);
    }
}
