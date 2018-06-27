package com.monitorTest;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
//import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import emailUtil.Email;

@Controller
public class MainController {
	
	Map<String, Object> map;
	
	@Autowired
	ScheduledTasks scheduler;
	
	Email email = new Email();
	Statistics stats;
	
    @RequestMapping("/")
    public String home() throws IOException {  
    	/*try {
    		map = GetJson.getDiskUsage("http://192.168.4.130:8080");
    	}catch(IOException e) {}*/
    	GetJson.getDiskUsage("http://192.168.4.130:8080");
        return "index";
    }
    
    @RequestMapping("/Send")
    public String sendEmail() {
    	email.sendEmail();
    	return "sent";
    }
    
    @RequestMapping("/Stats")
    public ModelAndView statsPage() {
    	List<Statistics> list = new ArrayList<Statistics>();
    	stats = scheduler.getStats();
    	list.add(stats);
    	return new ModelAndView("statsPage","stats", list);
    }
}
