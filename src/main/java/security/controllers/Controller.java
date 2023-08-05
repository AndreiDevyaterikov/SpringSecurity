package security.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Controller {

    @PostMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/admin")
    public String admin() {
        return "admin";
    }
}
