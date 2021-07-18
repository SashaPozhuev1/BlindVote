package ru.pozhuev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pozhuev.springcourse.dao.CryptoDAO;
import ru.pozhuev.springcourse.models.Crypto;
import ru.pozhuev.springcourse.models.Sign;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping("/crypto")
public class CryptoController {
    private final CryptoDAO cryptoDAO;

    @Autowired
    public CryptoController(CryptoDAO cryptoDAO) {
        this.cryptoDAO = cryptoDAO;
    }

    @GetMapping()
    public String start(@ModelAttribute("sign") Sign sign, Model model) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        model.addAttribute("pub", Crypto.keyToString(cryptoDAO.getCertCenterPublicKey()));
        model.addAttribute("cert", sign.getCertificate());
        return "crypto/sign";
    }

    @PostMapping()
    public String sign(@ModelAttribute("sign") @Valid Sign sign, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors())
            return "redirect:/crypto";

        String certificate;
        try {
            certificate = Crypto.sign(sign.getPublicKey(), cryptoDAO.getCertCenterPrivateKey());
        }
        catch(Exception exception){
            return "crypto/sign";
        }

        sign.setCertificate(certificate);
        if(!cryptoDAO.save(sign)){
            return "crypto/sign";
        }

        model.addAttribute("pub", Crypto.keyToString(cryptoDAO.getCertCenterPublicKey()));
        model.addAttribute("cert", certificate);
        return "crypto/sign";
    }


}

