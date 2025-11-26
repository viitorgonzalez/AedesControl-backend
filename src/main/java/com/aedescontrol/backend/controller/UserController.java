package com.aedescontrol.backend.controller;

import com.aedescontrol.backend.dto.LoginRequest;
import com.aedescontrol.backend.dto.LoginResponse;
import com.aedescontrol.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;


}
