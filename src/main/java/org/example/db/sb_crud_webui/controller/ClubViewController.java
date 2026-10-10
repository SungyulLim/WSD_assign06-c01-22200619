package org.example.db.sb_crud_webui.controller;

import org.example.db.sb_crud_webui.dto.ClubRequest;
import org.example.db.sb_crud_webui.service.ClubService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClubViewController {
    private final ClubService clubService;

    public ClubViewController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/clubs";
    }

    @GetMapping("/clubs")
    public String list(Model model) {
        model.addAttribute("members", clubService.findAll());
        model.addAttribute("memberCount", clubService.count());
        return "club/list";
    }

    @GetMapping("/clubs/new")
    public String form() {
        return "club/form";
    }

    @PostMapping("/clubs")
    public String create(@ModelAttribute ClubRequest request) {
        clubService.create(request);
        return "redirect:/clubs";
    }
}
