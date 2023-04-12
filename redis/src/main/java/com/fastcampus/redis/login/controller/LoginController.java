package com.fastcampus.redis.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    private static final String NAME = "name";

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String name) {
        session.setAttribute(NAME, name);
        return (String) session.getAttribute(NAME);
    }

    @GetMapping("/login")
    public String getLoginInfo(HttpSession session) {
        Object name = session.getAttribute(NAME);

        if (name == null) {
            return null;
        }
        return (String) name;
    }

    @DeleteMapping("/login")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute(NAME);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
