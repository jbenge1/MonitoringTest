package com.monitorTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

//import emailUtil.Email;

public class GetJson {

	
	//private static double maxThreshhold = 0.5;
	@Autowired
	//private static Email email; //= new Email();
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String readJsonFromUrl(String url) {
		HashMap<String,Object> map = null;
		try {
			InputStream is = new URL(url + "/health").openStream();
			map = new ObjectMapper().readValue(is, HashMap.class);
			is.close();
		}
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		return (String)map.get("status");
	}
	
	/**
	 * If we're over a certain threshold here, 
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String,Object> getDiskUsage(String url) {
		HashMap<String,Object> diskSpace = null;
		try {
			InputStream is = new URL(url + "/health").openStream();
			HashMap<String,Object> map = new ObjectMapper().readValue(is, HashMap.class);
			diskSpace = (HashMap<String,Object>)map.get("diskSpace");
			System.out.println(diskSpace.get("status"));
			
			is.close();
} 
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		return diskSpace;
	}

	/**
	 * 
	 * @param url
	 */
	@SuppressWarnings("unchecked")
	public static void getMemory(String url) {
		try {
			InputStream is = new URL(url + "/metrics").openStream();
			HashMap<String,Object> map = new ObjectMapper().readValue(is,HashMap.class);
			long mem  = (Long)map.get("mem");
			long free = (Long)map.get("mem.free");
			long used = mem - free;
			@SuppressWarnings("unused")
			long threshHold = used / mem;
			
			is.close();
		} 
		catch (MalformedURLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * 
	 * @param url
	 */
	@SuppressWarnings("unchecked")
	public static void getCPULoad(String url) {
		try {
			InputStream is = new URL(url + "/metrics").openStream();
			HashMap<String,Object> map = new ObjectMapper().readValue(is, HashMap.class);
			@SuppressWarnings("unused")
			double loadAvg = (Double)map.get("systemload.average");
			
			is.close();
		}
		catch(MalformedURLException e) {e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
	}
	/**
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getStats(String url) {
		HashMap<String, Object> stats = new HashMap<String,Object>();
		try {
			InputStream metrics = new URL(url + "/metrics").openStream();
			InputStream health  = new URL(url + "/health").openStream();
			
			HashMap<String,Object> map = new ObjectMapper().readValue(metrics, HashMap.class);
			
			stats.put("mem", map.get("mem"));
			stats.put("mem.free", map.get("mem.free"));
			stats.put("systemload.average", map.get("systemload.average"));
			stats.put("mem.used", (Integer)map.get("mem") - (Integer)map.get("mem.free"));
			System.out.println(map.get("processors"));
			stats.put("cores", map.get("processors"));
			
			map = new ObjectMapper().readValue(health, HashMap.class);
			HashMap<String,Object> diskSpace = (HashMap<String,Object>)map.get("diskSpace");
			stats.put("diskSpace", diskSpace.get("total"));
			stats.put("diskSpace.free", diskSpace.get("free"));
			stats.put("diskSpace.used", (Long)diskSpace.get("total") - (Long)diskSpace.get("free"));
			stats.put("status", map.get("status"));
			
		}
		catch(MalformedURLException e ) {e.printStackTrace();}
		catch(IOException e) 			{e.printStackTrace();}
		
		return stats;
	}
}
