package com.example.book_a_bus.objectmodel;

public class SBSInfo {

    private String busService;
	private String busServiceNo;
	private String directionNo;
	private String category;
	private String startLocation;
	private String endLocation;
	private String amPeakFreq;
	private String amOffPeakFreq;
	private String pmPeakFreq;
	private String pmOffPeakFreq;
	private String locationLoop;
	
	public SBSInfo(){}
	

	public SBSInfo(String busService, String busServiceNo, String directionNo, String category,
			String startLocation, String endLocation, String amPeakFreq,
			String amOffPeakFreq, String pmPeakFreq, String pmOffPeakFreq,
			String locationLoop) {
		super();
        this.busService = busService;
		this.busServiceNo = busServiceNo;
		this.directionNo = directionNo;
		this.category = category;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.amPeakFreq = amPeakFreq;
		this.amOffPeakFreq = amOffPeakFreq;
		this.pmPeakFreq = pmPeakFreq;
		this.pmOffPeakFreq = pmOffPeakFreq;
		this.locationLoop = locationLoop;
	}

    public String getBusService() {
        return busService;
    }

    public void setBusService(String busService) {
        this.busService = busService;
    }

	public String getBusServiceNo() {
		return busServiceNo;
	}

	public void setBusServiceNo(String busServiceNo) {
		this.busServiceNo = busServiceNo;
	}

	public String getDirectionNo() {
		return directionNo;
	}

	public void setDirectionNo(String directionNo) {
		this.directionNo = directionNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getAmPeakFreq() {
		return amPeakFreq;
	}

	public void setAmPeakFreq(String amPeakFreq) {
		this.amPeakFreq = amPeakFreq;
	}

	public String getAmOffPeakFreq() {
		return amOffPeakFreq;
	}

	public void setAmOffPeakFreq(String amOffPeakFreq) {
		this.amOffPeakFreq = amOffPeakFreq;
	}

	public String getPmPeakFreq() {
		return pmPeakFreq;
	}

	public void setPmPeakFreq(String pmPeakFreq) {
		this.pmPeakFreq = pmPeakFreq;
	}

	public String getPmOffPeakFreq() {
		return pmOffPeakFreq;
	}

	public void setPmOffPeakFreq(String pmOffPeakFreq) {
		this.pmOffPeakFreq = pmOffPeakFreq;
	}

	public String getLocationLoop() {
		return locationLoop;
	}

	public void setLocationLoop(String locationLoop) {
		this.locationLoop = locationLoop;
	}
	
	

	
	
	
}
