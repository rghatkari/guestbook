package org.rghatkari.guestbook.controller;

import org.rghatkari.guestbook.model.GuestBookModel;
import org.rghatkari.guestbook.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class GuestBookController {

    @Autowired
    private GuestbookService guestbookService;

    @GetMapping("messages")
    public String getUserGuestbookMessages(Model model) {
        List<GuestBookModel> guestBookModels = guestbookService.getUserGuestbookMessages();
        if (!CollectionUtils.isEmpty(guestBookModels)) {
            model.addAttribute("guestBookModels", guestBookModels);
            return "index";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/message")
    public String submitGuestbook(GuestBookModel guestBookModel) {
        String response = guestbookService.saveGuestbookMessage(guestBookModel);
        if (response.equalsIgnoreCase("success")) {
            return "redirect:/messages";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editGuestBook(@PathVariable(name = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("home");
        if (id != null) {
            GuestBookModel guestBookModel = guestbookService.getGuestBookMessageById(id);
            if (guestBookModel != null) {
                modelAndView.addObject("guestBookModel", guestBookModel);
            }
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteGuestBook(@PathVariable(name = "id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("home");
        if (id != null) {
            guestbookService.deleteMessage(id);
            return "redirect:/";
        } else {
            return "redirect:/error";

        }
    }
}
