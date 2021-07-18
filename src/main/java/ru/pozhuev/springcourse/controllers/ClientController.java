package ru.pozhuev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pozhuev.springcourse.models.Client;
import ru.pozhuev.springcourse.models.Crypto;
import ru.pozhuev.springcourse.models.Sign;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping("/client")
public class ClientController {
    @GetMapping()
    public String start(@ModelAttribute("client") Client client, Model model) {

        return "client/start";
    }

    @PostMapping()
    public String sign(@ModelAttribute("client") @Valid Client client,
                       BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors())
            return "redirect:/client";

        // SIGN BULLETIN
        String bulletinSign;
        try {
            bulletinSign = Crypto.sign(client.getBulletin(), Crypto.stringToPrivateKey(client.getPrivateKey()));
        }
        catch(Exception exception){
            return "client/start";
        }
        client.setBulletinSign(bulletinSign);

        // MASK PUBLIC KEY
        //client.setPublicKeyMasked();
        return "client/start";
    }
}
