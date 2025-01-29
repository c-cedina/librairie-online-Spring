package com.example.librairie_online.service;

import com.example.librairie_online.entity.Mangaka;
import com.example.librairie_online.entity.Mangaka.MangakaId;
import com.example.librairie_online.repository.MangakaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MangakaServiceTest {

    @Mock
    private MangakaRepository mangakaRepository;

    @InjectMocks
    private MangakaService mangakaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        Mangaka mangaka = new Mangaka();
        mangakaService.create(mangaka);
        verify(mangakaRepository, times(1)).save(mangaka);
    }

    @Test
    public void testRead() {
        Mangaka mangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "M", "Japonaise");
        when(mangakaRepository.findAll()).thenReturn(Arrays.asList(mangaka));

        List<Mangaka> mangakas = mangakaService.read();
        assertNotNull(mangakas);
        assertEquals(1, mangakas.size());
        verify(mangakaRepository, times(1)).findAll();
    }

    @Test
    public void testReadById_Found() {
        Mangaka mangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "M", "Japonaise");
        when(mangakaRepository.findById(any(MangakaId.class))).thenReturn(Optional.of(mangaka));

        Mangaka foundMangaka = mangakaService.readById(new MangakaId("Test", "Mangaka"));
        assertNotNull(foundMangaka);
        verify(mangakaRepository, times(1)).findById(any(MangakaId.class));
    }

    @Test
    public void testReadById_NotFound() {
        when(mangakaRepository.findById(any(MangakaId.class))).thenReturn(Optional.empty());

        Mangaka foundMangaka = mangakaService.readById(new MangakaId("Test", "Mangaka"));
        assertNull(foundMangaka);
        verify(mangakaRepository, times(1)).findById(any(MangakaId.class));
    }

    @Test
    public void testUpdate() {
        Mangaka dbMangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "M", "Japonaise");
        Mangaka updatedMangaka = new Mangaka(new MangakaId("Test", "Mangaka"), "F", "Française");

        when(mangakaRepository.findById(any(MangakaId.class))).thenReturn(Optional.of(dbMangaka));

        mangakaService.update(new MangakaId("Test", "Mangaka"), updatedMangaka);

        assertEquals("Française", dbMangaka.getNationalite());
        assertEquals("F", dbMangaka.getSexe());
        verify(mangakaRepository, times(1)).findById(any(MangakaId.class));
        verify(mangakaRepository, times(1)).save(dbMangaka);
    }

}
