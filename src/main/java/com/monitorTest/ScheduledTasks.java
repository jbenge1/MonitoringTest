package com.monitorTest;

import java.util.HashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import emailUtil.Email;

@Configuration
@EnableScheduling
public class ScheduledTasks {

	private String url = "http://192.168.4.130:8080";
	private HashMap<String,Object> statsMap = null;
	private Statistics stats = new Statistics();
	private Email email = new Email();
		
	/**
	 * 
	 */
	@Scheduled(fixedRate = 300000)
	public void updateStats() {
		statsMap = GetJson.getStats(url);
		stats.setMem((Integer)statsMap.get("mem"));
		stats.setMemFree((Integer)statsMap.get("mem.free"));
		stats.setMemUsed((Integer)statsMap.get("mem.used"));
		stats.setLoadAverage((Double)statsMap.get("systemload.average"));

		stats.setDiskSpace((Long)statsMap.get("diskSpace"));
		stats.setDiskFree((Long)statsMap.get("diskSpace.free"));
		stats.setDiskUsed((Long)statsMap.get("diskSpace.used"));
		stats.setStatus((String)statsMap.get("status"));
		
		stats.setLoadAvgPerCore((Double)statsMap.get("systemload.average") / (Integer)statsMap.get("cores"));
		System.out.println("UPDATED @ -" + System.currentTimeMillis() / 1000);
	}
	
	/**
	 * Check the health of the application every five minutes, however
	 * we must wait a little bit (1 second) after the inital start of the 
	 * program to allow our variables to be initialized.. 
	 * I will also need to possibly add in an alerts variable to the statistics
	 * model that will be able to display all the alerts to some endpoint
	 * 
	 */
	@Scheduled(fixedRate = 300000, initialDelay = 1000)
	public void checkStats() {
		//here is where we will check against thresholds, then email accordingly
		System.out.println("Checking load average and status");
		if(!stats.getStatus().equals("UP")) {
			System.out.println("Application is down currenly...");
			email.sendEmail();
		}
		if(stats.getLoadAvgPerCore() > 1) {
			System.out.println("Load average to high sending email report");
			email.sendEmail();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Statistics getStats(){
		return stats;
	}
}
