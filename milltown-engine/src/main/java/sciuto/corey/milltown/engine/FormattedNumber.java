package sciuto.corey.milltown.engine;

import java.text.NumberFormat;

import org.apache.commons.lang3.mutable.MutableLong;

public class FormattedNumber extends MutableLong {

	private static final long serialVersionUID = -1282240201842305260L;
	
	private NumberFormat number = NumberFormat.getIntegerInstance();
	
	public FormattedNumber(long l){
		super(l);
	}
	
	@Override
	public String toString(){
		return number.format(this.longValue());
	}

}
