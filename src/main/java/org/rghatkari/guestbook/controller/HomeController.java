package org.rghatkari.guestbook.controller;

import org.rghatkari.guestbook.model.GuestBookModel;
import org.rghatkari.guestbook.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"/", "/index"})
public class HomeController {

    @Autowired
    private GuestbookService guestbookService;

    @GetMapping
    public String main(Model model) {
        List<GuestBookModel> guestBookModels = guestbookService.getUserGuestbookMessages();
        model.addAttribute("guestBookModels", guestBookModels);
        return "index";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        GuestBookModel guestBookModel = new GuestBookModel();
        model.addAttribute("guestBookModel", guestBookModel);
        return "home";
    }

    @GetMapping("/403")
    public String accessDeniedPage() {
        return "403";
    }
}
