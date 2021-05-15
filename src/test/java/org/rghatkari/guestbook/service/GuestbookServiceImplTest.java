package org.rghatkari.guestbook.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rghatkari.guestbook.entities.GuestBook;
import org.rghatkari.guestbook.model.GuestBookModel;
import org.rghatkari.guestbook.repository.GuestbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService guestbookService;

    @MockBean
    private GuestbookRepository guestbookRepository;

    @Test
    @DisplayName("fetch all guest book messages")
    void findAllMessages() {
        GuestBook guestBook = new GuestBook();
        guestBook.setUserId(1);
        guestBook.setName("Birthday");
        guestBook.setMessage("Wish you happy birthday");
        List<GuestBook> guestBookList = new ArrayList<>();
        guestBookList.add(guestBook);

        doReturn(guestBookList).when(guestbookRepository).fetchAllGuestBookMessages();

        List<GuestBookModel> list = guestbookService.getUserGuestbookMessages();
        Assertions.assertTrue(!CollectionUtils.isEmpty(list));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    @DisplayName("save guestbook message")
    void saveGuestbookMessage() {
        GuestBookModel guestBookModel = new GuestBookModel();
        guestBookModel.setName("birthday");
        guestBookModel.setMessage("birthday wishes");

        GuestBook guestBook = new GuestBook();
        guestBook.setName("birthday");
        guestBook.setMessage("birthday wishes");
        guestBook.setUserId(1);
        guestBook.setApproved(false);
        guestBook.setCreatedAt(new Date());
        guestBook.setUpdatedAt(new Date());

        doReturn(guestBook).when(guestbookRepository).save(guestBook);
        String response = guestbookService.saveGuestbookMessage(guestBookModel);
    }

    @Test
    @DisplayName("get all the guestbook message for given id")
    void getMessageById() {
        GuestBook guestBook = new GuestBook();
        guestBook.setUserId(1);
        guestBook.setName("Birthday");
        guestBook.setMessage("Wish you happy birthday");
        doReturn(Optional.of(guestBook)).when(guestbookRepository).findById(1);
        GuestBookModel model = guestbookService.getGuestBookMessageById(1);

        Assertions.assertTrue(guestBook.getName().equalsIgnoreCase("Birthday"));
    }

    @Test
    @DisplayName("Delete the guest book message for given id")
    void deleteMessage() {
        GuestBook guestBook = new GuestBook();
        guestBook.setUserId(1);
        guestBook.setName("Birthday");
        guestBook.setMessage("Wish you happy birthday");
        doReturn(Optional.of(guestBook)).when(guestbookRepository).findById(1);
        doNothing().when(guestbookRepository).delete(guestBook);
        guestbookService.deleteMessage(1);
        verify(guestbookRepository, times(1)).delete(guestBook);
    }
}
