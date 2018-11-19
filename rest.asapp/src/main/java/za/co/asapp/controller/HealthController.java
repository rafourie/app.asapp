package za.co.asapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import za.co.asapp.beans.Health;

@Controller
public class HealthController {

  @ResponseBody
  @GetMapping("/health")
  public Health health(@RequestParam(name="name", required=false, defaultValue="Java Fan") String name) {
    return new Health();
  }
}
