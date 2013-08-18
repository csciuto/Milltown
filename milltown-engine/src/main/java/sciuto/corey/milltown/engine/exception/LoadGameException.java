package sciuto.corey.milltown.engine.exception;

import java.io.File;

public class LoadGameException extends Exception{

	private static final long serialVersionUID = 6813244654822476234L;
	
	private final File faultFile;
	
	public LoadGameException(File file, Exception e){
		super(e);
		this.faultFile = file;
	}

	public File getFaultFile() {
		return faultFile;
	}
	
};
