package org.amit.elasticsearch.model;

public class CPNMSNMapping {

	private String cpnDetails;
	private String msnProductName1;
	private String msnProductName2;
	private String msnProductName3;
	private String msnProductName4;
	private String msnProductName5;

	public CPNMSNMapping()
	{
		this.msnProductName1="";
		this.msnProductName2="";
		this.msnProductName3="";
		this.msnProductName4="";
		this.msnProductName5="";
	}
	
	public String getCpnDetails() {
		return cpnDetails;
	}

	public void setCpnDetails(String cpnDetails) {
		this.cpnDetails = cpnDetails;
	}

	public String getMsnProductName1() {
		return msnProductName1;
	}

	public void setMsnProductName1(String msnProductName1) {
		this.msnProductName1 = msnProductName1;
	}

	public String getMsnProductName2() {
		return msnProductName2;
	}

	public void setMsnProductName2(String msnProductName2) {
		this.msnProductName2 = msnProductName2;
	}

	public String getMsnProductName3() {
		return msnProductName3;
	}

	public void setMsnProductName3(String msnProductName3) {
		this.msnProductName3 = msnProductName3;
	}

	public String getMsnProductName4() {
		return msnProductName4;
	}

	public void setMsnProductName4(String msnProductName4) {
		this.msnProductName4 = msnProductName4;
	}

	public String getMsnProductName5() {
		return msnProductName5;
	}

	public void setMsnProductName5(String msnProductName5) {
		this.msnProductName5 = msnProductName5;
	}

}
