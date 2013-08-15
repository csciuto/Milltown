package sciuto.corey.milltown.map.swing;

public enum Speed {

	PAUSE("PAUSE", -1), SLOW("SLOW", 10000), MEDIUM("MEDIUM", 5000), FAST("FAST", 1000), WICKED_FAST("WICKED FAST", 500);

	private final String speedName;
	private final int speedInMilliseconds;

	private Speed(String txt, int speedInMilliseconds) {
		this.speedName = txt;
		this.speedInMilliseconds = speedInMilliseconds;
	}

	public String getSpeedName() {
		return speedName;
	}

	public int getSpeedInMilliseconds() {
		return speedInMilliseconds;
	}

}
