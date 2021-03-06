package com.monitorTest;

//import java.util.ArrayList;
//import java.util.List;
import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;

import emailUtil.Email;

@Controller
public class MainController {
	
//	Map<String, Object> map;
	
	@Autowired
	ScheduledTasks scheduler;
	
	Email email = new Email();
	Statistics stats;
	
    @RequestMapping("/")
    public String home() throws IOException {  
    	GetJson.getDiskUsage("http://192.168.4.130:8080");
        return "index";
    }

    @RequestMapping("/Stats")
    @ResponseBody
    public Statistics statsPage() {
    	stats = scheduler.getStats();
    	return stats;
    }
}
