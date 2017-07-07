package org.demo;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	 @RequestMapping("/api/hello1")
	 public String greet() {
	  return "Hello from the demoServerApp side!!!";
	 }
	 
	 @RequestMapping("/api2/hello2")
	 public String greet2() {
	  return "Hello from the demoServerApp2/api2/hello2";
	 }
}
