package sciuto.corey.milltown.engine;

import java.text.NumberFormat;

import org.apache.commons.lang3.mutable.MutableLong;

public class DollarAmount extends MutableLong {

	private static final long serialVersionUID = 2599755666817112910L;

	private NumberFormat currency = NumberFormat.getCurrencyInstance();
	
	public DollarAmount(long l){
		super(l);
		currency.setMaximumFractionDigits(0);
	}
	
	@Override
	public String toString(){
		return currency.format(this.longValue());
	}
	
}
