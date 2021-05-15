package org.rghatkari.guestbook.service;

import org.rghatkari.guestbook.model.GuestBookModel;

import java.util.List;

public interface GuestbookService {

    public String saveGuestbookMessage(GuestBookModel guestBookModel);
    public List<GuestBookModel> getUserGuestbookMessages();
    public GuestBookModel getGuestBookMessageById(Integer id);
    public void deleteMessage(Integer id);
}
