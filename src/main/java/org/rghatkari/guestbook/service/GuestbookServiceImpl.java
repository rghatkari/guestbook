package org.rghatkari.guestbook.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.rghatkari.guestbook.entities.GuestBook;
import org.rghatkari.guestbook.entities.User;
import org.rghatkari.guestbook.model.GuestBookModel;
import org.rghatkari.guestbook.repository.GuestbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestbookServiceImpl implements GuestbookService{

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Override
    public List<GuestBookModel> getUserGuestbookMessages() {
        List<GuestBookModel> guestBookModels = null;
        try {
            List<GuestBook> guestBookList = guestbookRepository.fetchAllGuestBookMessages();
            if (!CollectionUtils.isEmpty(guestBookList)) {
                guestBookModels = guestBookList.stream().map(GuestbookServiceImpl::convert)
                        .collect(Collectors.toList());
            }
            return guestBookModels;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String saveGuestbookMessage(GuestBookModel guestBookModel) {
        try {
            if (guestBookModel != null && guestBookModel.getId() == null) {
                User user = this.getLoggedInUser();
                if (user != null) {
                    GuestBook guestBook = this.guestBookEntityMapper(guestBookModel, user.getId());
                    guestbookRepository.save(guestBook);
                }
            } else {
                Date date = new Date();
                guestbookRepository.updateGuestBookMessage(guestBookModel.getId(), guestBookModel.getName(),
                        guestBookModel.getMessage(), guestBookModel.getImage(), date);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    public GuestBook guestBookEntityMapper(GuestBookModel guestBookModel, Integer userId) {
        GuestBook guestBook = new GuestBook();
        guestBook.setUserId(userId);
        guestBook.setName(guestBookModel.getName());
        guestBook.setMessage(guestBookModel.getMessage());
        guestBook.setImage(guestBookModel.getImage());
        guestBook.setApproved(false);
        guestBook.setCreatedAt(new Date());
        guestBook.setUpdatedAt(new Date());

        return guestBook;
    }

    private static GuestBookModel convert(GuestBook guestBook) {
        GuestBookModel model = new GuestBookModel();
        model.setId(guestBook.getId());
        model.setName(guestBook.getName());
        model.setMessage(guestBook.getMessage());
        String base64Encoded = null;
        if (guestBook.getImage() != null) {
            try {
                byte[] bytes = guestBook.getImage();
                byte[] encodeBase64 = Base64.encodeBase64(bytes);
                base64Encoded = new String(encodeBase64, "UTF-8");
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            model.setBase64Image(base64Encoded);
            model.setImage(guestBook.getImage());
        }
        return model;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof GuestBookUserDetails) {
                return ((GuestBookUserDetails) principal).getLoggedInUser();
            }
        }
        return null;
    }

    @Override
    public GuestBookModel getGuestBookMessageById(Integer id) {
        GuestBook guestBook = guestbookRepository.findById(id).orElse(null);
        if (guestBook != null) {
            return GuestbookServiceImpl.convert(guestBook);
        } else {
            return null;
        }
    }


    @Override
    public void deleteMessage(Integer id) {
        GuestBook guestBook = guestbookRepository.findById(id).orElse(null);
        if (guestBook != null) {
            guestbookRepository.delete(guestBook);
        }
    }
}
