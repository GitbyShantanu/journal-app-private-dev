package net.engineeringdigest.journalApp.serviceTests;

import net.engineeringdigest.journalApp.dto.JournalEntryDTO;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ExtendWith(MockitoExtension.class) // Mockito ke mocks enable
class JournalServiceTest {

    @Mock
    private JournalEntryRepository journalEntryRepository; // mocked repo

    @Mock
    private UserService userService; // mocked dependent service

    @InjectMocks
    private JournalService journalService; // inject mocks into service


    @Test
    void testSaveEntry2_AddsToUserJournalList() {
        // Prepare user
        User user = new User();
        user.setUserName("Ram");
        user.setJournalEntryList(new ArrayList<>()); // empty list initially

        // Prepare JournalEntryDTO instead of JournalEntry entity
        JournalEntryDTO entryDto = new JournalEntryDTO();
        entryDto.setTitle("Test Title");
        entryDto.setContent("Test Content");

        // Mock userService.findByUserName
        when(userService.findByUserName("Ram")).thenReturn(user);

        // Mock repository save to return a JournalEntry entity (service internally maps DTO -> entity)
        JournalEntry savedEntry = new JournalEntry();
        savedEntry.setTitle(entryDto.getTitle());
        savedEntry.setContent(entryDto.getContent());
        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(savedEntry);

        // Mock saveUser (does nothing)
        doNothing().when(userService).saveUser(user);

        // Call actual service method
        journalService.saveEntry(entryDto, "Ram");

        // Assertions: savedEntry added to user's journal list
        assertTrue(user.getJournalEntryList().contains(savedEntry));

        // Verify repository save called exactly once
        verify(journalEntryRepository, times(1)).save(any(JournalEntry.class));
        // Verify userService saveUser called exactly once
        verify(userService, times(1)).saveUser(user);
    }


    @Test
    void testGetAllEntries() {
        // findAll returns empty list
        when(journalEntryRepository.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(journalService.getAllEntries()); // should not be null
    }

    @Test
    void testDeleteById_RemovesEntryFromUser() {
        User user = new User();
        user.setUserName("Ram");
        JournalEntry entry = new JournalEntry();
        ObjectId id = new ObjectId();
        entry.setId(id);
        user.setJournalEntryList(new ArrayList<>());
        user.getJournalEntryList().add(entry); // add entry to user list

        // mocks for dependent calls
        when(userService.findByUserName("Ram")).thenReturn(user);
        doNothing().when(userService).saveUser(user);
        doNothing().when(journalEntryRepository).deleteById(id);

        // call delete method
        boolean removed = journalService.deleteById(id, "Ram");

        // assertions: removed and list updated
        assertTrue(removed);
        assertFalse(user.getJournalEntryList().contains(entry));
    }
}
