package net.engineeringdigest.journalApp.serviceTests;

import net.engineeringdigest.journalApp.dto.JournalEntryDTO;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JournalServiceTest {

    @Mock
    private JournalEntryRepository journalEntryRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private JournalService journalService;

    @Test
    void testSaveEntry_AddsToUserJournalList() {
        User user = new User();
        user.setUserName("Ram");
        user.setJournalEntryList(new ArrayList<>());

        JournalEntryDTO dto = new JournalEntryDTO();
        dto.setTitle("Title");
        dto.setContent("Content");

        when(userService.findByUserName("Ram")).thenReturn(user);

        JournalEntry savedEntry = new JournalEntry();
        savedEntry.setTitle(dto.getTitle());
        savedEntry.setContent(dto.getContent());

        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(savedEntry);
        doNothing().when(userService).saveUser(user);

        journalService.saveEntry(dto, "Ram");

        assertTrue(user.getJournalEntryList().contains(savedEntry));
        verify(journalEntryRepository, times(1)).save(any(JournalEntry.class));
        verify(userService, times(1)).saveUser(user);
    }
}
