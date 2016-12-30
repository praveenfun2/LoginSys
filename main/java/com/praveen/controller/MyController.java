package com.praveen.controller;

import javax.servlet.http.HttpServletRequest;

import com.praveen.service.UserService;
import com.sendgrid.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.praveen.model.User;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
public class MyController {

    @Autowired
    private UserService usersService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String verify(@RequestParam String verificationCode, ModelMap map) throws UnsupportedEncodingException {
        verificationCode = URLDecoder.decode(verificationCode, "UTF-8");
        if (usersService.uuidexists(verificationCode)) {
            if (usersService.isVerifed(verificationCode, 1)) map.addAttribute("message", "ALREADY VERIFIED");
            else {
                usersService.verifyUser(verificationCode);
                map.addAttribute("message", "SUCCESSFULLY VERIFIED");
            }
        } else map.addAttribute("message", "NO SUCH USER");
        map.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, ModelMap model) {
        if (request.getSession(false) != null && request.getSession(false).getAttribute("email") != null) {
            model.addAttribute("message", "Welcome " + request.getSession().getAttribute("email"));
            return "logedin";
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(ModelMap model) {
        User user1=new User();
        user1.setUserType(1);
        model.addAttribute("User", user1);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String saveuser(@ModelAttribute User user, ModelMap map) throws UnsupportedEncodingException {
        user.setVerified(false);
        String uuid = UUID.randomUUID().toString();
        user.setVerificationCode(uuid);
        if (usersService.saveUser(user)) {
            map.addAttribute("message", "PLEASE CONFIRM YOUR EMAIL");
            String s = "http://localhost:8080/verify?verificationCode=" + URLEncoder.encode(uuid, "UTF-8");
            SendMail(user.getEmail(), s);
        } else
            map.addAttribute("message", "INFORMATION NOT VALID");
        User user1=new User();
        user1.setUserType(1);
        map.addAttribute("User", user1);
        return "signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginuser(@RequestParam("g-recaptcha-response") String grr, @ModelAttribute User user, ModelMap model,
                            HttpServletRequest request) throws IOException {
        String formData = "secret=6LeTFg4UAAAAAJoIOBoTCXwraW26oOZfaI4yymBx&response=" + URLEncoder.encode(grr, "UTF-8");
        URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
        HttpsURLConnectionImpl urlConnection = (HttpsURLConnectionImpl) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Tpe", "application/x-www-form-urlencoded");
        urlConnection.setDoOutput(true);
        urlConnection.connect();
        urlConnection.getOutputStream().write(formData.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String s = "", temp;
        while ((temp = bufferedReader.readLine()) != null) s += "\n" + temp;
        JSONObject jsonObject = new JSONObject(s.substring(1));
        if (jsonObject.getBoolean("success")) {
            if (usersService.findUser(user.getEmail(), user.getPassword()).size() > 0) {
                if (usersService.isVerifed(user.getEmail(), 2)) {
                    model.addAttribute("message", "WELCOME "+user.getEmail()+"\n"+user.getUserType()+"\n"+user.getPassword());
                    request.getSession().setAttribute("email", user.getEmail());
                    return "logedin";
                } else model.addAttribute("message", "YOUR EMAIL IS NOT VERIFIED");
            } else model.addAttribute("message", "NO USER FOUND");
            return "login";

        } else {
            model.addAttribute("message", "CAPTCHA WRONG");
            model.addAttribute("User", new User());
            return "login";
        }
    }

    private void SendMail(String t, String c) {
        Email from = new Email("praveen@LoginSys.com");
        String subject = "Email Verification";
        Email to = new Email(t);
        Content content = new Content("text/plain", c);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
