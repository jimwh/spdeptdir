package edu.columbia.cuitei.deptdir.controller;

import edu.columbia.cuitei.deptdir.domain.User;
import edu.columbia.cuitei.deptdir.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Resource private UserService userService;

    @GetMapping("/login")
    public String login() {
        log.info("login form...");
        return "login";
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        log.info("registration form...");
        final ModelAndView modelAndView = new ModelAndView();
        final User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    // https://cuit-1xtjdh1.ais.columbia.edu:8443/j_spring_cas_security_check has to be on CAS server

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid final User user, final BindingResult bindingResult) {
        log.info("create new user...");
        final ModelAndView modelAndView=new ModelAndView();
        final User userExists = userService.findUserByEmail(user.getEmail());
        if(userExists != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
        }
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @GetMapping("/admin/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("admin/home ...name={}", auth.getName());
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}