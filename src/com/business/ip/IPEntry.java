package com.business.ip;
//一条IP范围记录，不仅包括国家和区域，也包括起始IP和结束IP
public class IPEntry {
	public String beginIp;
	public String endIp;
	public String country;
	public String area;
	public IPEntry() {
		beginIp = "";
		endIp = "";
		country = "";
		area = "";
	}

	@Override
	public String toString() {
		return this.area + "  " + this.country + "  IP范围:" + this.beginIp + "-"
				+ this.endIp;
	}
}
