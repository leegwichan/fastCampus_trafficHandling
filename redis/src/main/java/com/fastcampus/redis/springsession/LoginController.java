package com.fastcampus.redis.springsession;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class LoginController {

    private final HashMap<String, String> sessionStorage = new HashMap<>();

    @PostMapping("/login")
    public Session login(HttpSession session, @RequestParam String name) {
        sessionStorage.put(session.getId(), name);
        return new Session(session.getId(), name);
    }

    @GetMapping("/login")
    public Session getloginInfo(HttpSession session) {
        String name = sessionStorage.get(session.getId());

        if (name == null) {
            return null;
        }
        return new Session(session.getId(), name);
    }

    @DeleteMapping("/login")
    public void logout(HttpSession session) {
        sessionStorage.remove(session.getId());
    }
}

@AllArgsConstructor
@Getter
class Session {
    private String sessionId;
    private String name;
}
