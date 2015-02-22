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
import com.mycompany.myapp.domain.Fs;
import com.mycompany.myapp.repository.FsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FsResource REST controller.
 *
 * @see FsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FsResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";
    

    @Inject
    private FsRepository fsRepository;

    private MockMvc restFsMockMvc;

    private Fs fs;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FsResource fsResource = new FsResource();
        ReflectionTestUtils.setField(fsResource, "fsRepository", fsRepository);
        this.restFsMockMvc = MockMvcBuilders.standaloneSetup(fsResource).build();
    }

    @Before
    public void initTest() {
        fs = new Fs();
        fs.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createFs() throws Exception {
        // Validate the database is empty
        assertThat(fsRepository.findAll()).hasSize(0);

        // Create the Fs
        restFsMockMvc.perform(post("/app/rest/fss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fs)))
                .andExpect(status().isOk());

        // Validate the Fs in the database
        List<Fs> fss = fsRepository.findAll();
        assertThat(fss).hasSize(1);
        Fs testFs = fss.iterator().next();
        assertThat(testFs.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFss() throws Exception {
        // Initialize the database
        fsRepository.saveAndFlush(fs);

        // Get all the fss
        restFsMockMvc.perform(get("/app/rest/fss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(fs.getId().intValue()))
                .andExpect(jsonPath("$.[0].nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getFs() throws Exception {
        // Initialize the database
        fsRepository.saveAndFlush(fs);

        // Get the fs
        restFsMockMvc.perform(get("/app/rest/fss/{id}", fs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fs.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFs() throws Exception {
        // Get the fs
        restFsMockMvc.perform(get("/app/rest/fss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFs() throws Exception {
        // Initialize the database
        fsRepository.saveAndFlush(fs);

        // Update the fs
        fs.setNombre(UPDATED_NOMBRE);
        restFsMockMvc.perform(post("/app/rest/fss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fs)))
                .andExpect(status().isOk());

        // Validate the Fs in the database
        List<Fs> fss = fsRepository.findAll();
        assertThat(fss).hasSize(1);
        Fs testFs = fss.iterator().next();
        assertThat(testFs.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteFs() throws Exception {
        // Initialize the database
        fsRepository.saveAndFlush(fs);

        // Get the fs
        restFsMockMvc.perform(delete("/app/rest/fss/{id}", fs.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fs> fss = fsRepository.findAll();
        assertThat(fss).hasSize(0);
    }
}
