package com.bta.twitter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetTweets {
    
    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name) {
     
      return "helloworld";
    
    }
    
}
