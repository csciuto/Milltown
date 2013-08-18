package sciuto.corey.milltown.engine.exception;

import java.io.File;

public class SaveGameException extends Exception {

	private static final long serialVersionUID = 4446441473687457743L;
	
	private final File faultFile;
	
	public SaveGameException(File file, Exception e){
		super(e);
		this.faultFile = file;
	}

	public File getFaultFile() {
		return faultFile;
	}
}
