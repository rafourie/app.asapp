package za.co.asapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import za.co.asapp.beans.Registration;
import za.co.asapp.beans.RegistrationReply;

import java.util.UUID;

@Controller
public class RegistrationController {

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST, value = "/register/student")
  RegistrationReply registerStudent(@RequestBody Registration studentregd) {
    RegistrationReply stdregreply = new RegistrationReply();
    stdregreply.setName(studentregd.getName());
    stdregreply.setAge(studentregd.getAge());
    stdregreply.setRegistrationNumber(UUID.randomUUID().toString());
    stdregreply.setRegistrationStatus("Successful");
    return stdregreply;
  }
}
