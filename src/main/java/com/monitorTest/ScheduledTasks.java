package com.monitorTest;

import java.util.HashMap;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
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
//	private final String PATH = "/home/justin/Documents/workspace/MonitoringTest/";
	
	@Value("${cpuLoadThresh}")
	private double cpuLoadThresh;
	
	@Value("${memThresh}")
	private double memThresh;
	
	@Value("${diskThresh}")
	private double diskThresh;
	
	/**
	 * 
	 */
	@Scheduled(fixedRateString = "${checkRate}")
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
	 * 
	 */
	@Scheduled(fixedRateString = "${checkRate}", initialDelayString = "${initialDelay}")
	public void checkStats() {
		//here is where we will check against thresholds, then email accordingly
		//Check Status
		if(!stats.getStatus().equals("UP") && !stats.getStatusFlag()) {
			System.out.println("Application is down currenly...");
		}
		//Check cpu load
		if(stats.getLoadAvgPerCore() > cpuLoadThresh && !stats.getCpuFlag()) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get("HIGH_CPU.txt"));
				String body = new String(encoded, StandardCharsets.UTF_8);
				System.out.println("Load average to high sending email report");
				email.sendEmail(body);
				stats.setCpuFlag(true);
			}catch(IOException e) {}
		}
		//check memory
		if((double)stats.getMemUsed() / (double)stats.getMem() >= memThresh && !stats.getMemFlag()) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get("HIGH_MEMORY.txt"));
				String body = new String(encoded, StandardCharsets.UTF_8);
				System.out.println("Memory usage high sending email report");
				email.sendEmail(body);
				stats.setMemFlag(true);
			}catch(IOException e) {}
		}
		//check diskSpace (have not tested this as I don't know how to eat through all of my diskspace....
		if(stats.getDiskUsed() / stats.getDiskFree() > diskThresh && !stats.getDiskFlag()) {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get("HIGH_DISK_USE.txt"));
				String body = new String(encoded, StandardCharsets.UTF_8);
				System.out.println("Disk Space low sending email report");
				email.sendEmail(body);
				stats.setDiskFlag(true);
			}catch(IOException e) {}
		}
		if(stats.getLoadAvgPerCore() < 1)
			stats.setCpuFlag(false);
		if((double)stats.getMemUsed() / (double)stats.getMem() < 0.7 )
			stats.setMemFlag(false);
		if(stats.getDiskUsed() / stats.getDiskFree() <= diskThresh)
			stats.setDiskFlag(false);
		if(stats.getStatus().equals("UP"))
			stats.setStatusFlag(false);
	}
	
	/**
	 * 
	 * @return
	 */
	public Statistics getStats(){
		return stats;
	}
}
