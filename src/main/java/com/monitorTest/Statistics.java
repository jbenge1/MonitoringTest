package com.monitorTest;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {

	private String status;
	private int memFree,memUsed,mem;
	private long diskFree,diskUsed,diskSpace;
	private double loadAverage;
	private int cores;
	
	private boolean cpuFlag, memFlag, statusFlag, diskFlag;
	
	private double loadAvgPerCore;
	
	//The idea is to have a string mapping the type of alert, to a list of all alerts
	//which may be deleted after being displayed? or written to a log file after a period of time
	//example "HIGH_CPU_USAGE -> ["High cpu usage loadavg:$loadvag on $date" -> "High cpu usage...."]
	private HashMap<String, ArrayList<String>> alerts;
	
	
	public Statistics(String status, int memFree, int memUsed, int mem, long diskFree, long diskUsed, long diskSpace,
			double loadAverage, int cores) {
		super();
		this.status = status;
		this.memFree = memFree;
		this.memUsed = memUsed;
		this.mem = mem;
		this.diskFree = diskFree;
		this.diskUsed = diskUsed;
		this.diskSpace = diskSpace;
		this.loadAverage = loadAverage;
		this.cores = cores;
		
		loadAvgPerCore = loadAverage / cores;
		cpuFlag = false;
		memFlag = false;
		statusFlag = false;
		diskFlag = false;
		
	}

	public void addAlert(String type, String alert) {
		alerts.get(type).add(alert);
		
	}
	public Statistics() {}
	
	public void setLoadAvgPerCore(double temp) {
		this.loadAvgPerCore = temp;
	}
	
	public double getLoadAvgPerCore(){
		return this.loadAvgPerCore;
	}
	
	public void setCores(int cores) {
		this.cores = cores;
	}
	
	public int getCores() {
		return this.cores;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setMemFree(int memFree) {
		this.memFree = memFree / 10000;
	}
	
	public void setMemUsed(int memUsed) {
		this.memUsed = memUsed / 10000;
	}
	
	public void setMem(int mem) {
		this.mem = mem / 10000;
	}
	
	public void setDiskFree(long diskFree) {
		this.diskFree = diskFree / 1000000;
	}
	
	public void setDiskUsed(long diskUsed) {
		this.diskUsed = diskUsed / 1000000;
	}
	
	public void setDiskSpace(long diskSpace) {
		this.diskSpace = diskSpace / 1000000;
	}
	
	public void setLoadAverage(double loadAvg) {
		this.loadAverage = loadAvg;
	}
	
	public void setCpuFlag(boolean flag) {
		this.cpuFlag = flag;
	}
	
	public void setDiskFlag(boolean flag) {
		this.diskFlag = flag;
	}
	
	public void setMemFlag(boolean flag) {
		this.memFlag = flag;
	}
	
	public void setStatusFlag(boolean flag) {
		this.statusFlag = flag;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public int getMemFree() {
		return this.memFree;
	}
	
	public int getMemUsed() {
		return this.memUsed;
	}
	
	public int getMem() {
		return this.mem;
	}
	
	public long getDiskFree() {
		return this.diskFree;
	}
	
	public long getDiskUsed() {
		return this.diskUsed;
	}
	
	public long getDiskSpace() {
		return this.diskSpace;
	}
	
	public double getLoadAverage() {
		return this.loadAverage;
	}

	public boolean getCpuFlag() {
		return this.cpuFlag;
	}
	
	public boolean getMemFlag() {
		return this.memFlag;
	}
	
	public boolean getDiskFlag() {
		return this.diskFlag;
	}
	
	public boolean getStatusFlag() {
		return this.statusFlag;
	}
}
