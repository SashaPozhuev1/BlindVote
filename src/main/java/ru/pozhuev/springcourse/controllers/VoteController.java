package ru.pozhuev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pozhuev.springcourse.dao.VoteDAO;
import ru.pozhuev.springcourse.models.Crypto;
import ru.pozhuev.springcourse.models.Vote;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping("/votes")
public class VoteController {

    private final VoteDAO voteDAO;

    @Autowired
    public VoteController(VoteDAO voteDAO) {
        this.voteDAO = voteDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("votes", voteDAO.index());
        return "votes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("vote", voteDAO.show(id));
        return "votes/show";
    }


    @GetMapping("new")
    public String newVote(@ModelAttribute("vote") Vote vote){
        return "votes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("vote") @Valid Vote vote,
                         BindingResult bindingResult) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (bindingResult.hasErrors())
            return "votes/new";

        // check bulletin sign valid and pub key certificate valid and existing in DB
        if( Crypto.verify(vote.getBulletinSign(), vote.getBulletin(), Crypto.stringToPublicKey(vote.getPublicKey()))
                && Crypto.verify(vote.getCertificate(), vote.getPublicKey(), voteDAO.getCertCenterPublicKey())
                && voteDAO.save(vote))
            return "redirect:/votes";
        else
            return "votes/new";
    }
}
