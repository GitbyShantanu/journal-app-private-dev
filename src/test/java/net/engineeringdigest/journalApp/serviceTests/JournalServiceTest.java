package net.engineeringdigest.journalApp.serviceTests;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Mockito ke mocks enable
class JournalServiceTest {

    @Mock
    private JournalEntryRepository journalEntryRepository; // mocked repo

    @Mock
    private UserService userService; // mocked dependent service

    @InjectMocks
    private JournalService journalService; // inject mocks into service

    @Test
    void testSaveEntry_AddsToUserJournalList() {
        User user = new User();
        user.setUserName("Ram");
        user.setJournalEntryList(new ArrayList<>()); // empty list initially
        JournalEntry entry = new JournalEntry();

        // findByUserName call will return our mocked user
        when(userService.findByUserName("Ram")).thenReturn(user);
        // save will just return the same journal entry
        when(journalEntryRepository.save(entry)).thenReturn(entry);
        // saveUser does nothing (mocked)
        doNothing().when(userService).saveUser(user);

        // call actual method
        journalService.saveEntry(entry, "Ram");

        // Assertions: entry added to user's journal list
        assertTrue(user.getJournalEntryList().contains(entry));
        // verify repository save called exactly once
        verify(journalEntryRepository, times(1)).save(entry);
        // verify userService saveUser called exactly once
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
