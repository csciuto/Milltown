package sciuto.corey.milltown.model.board;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameDate implements Serializable {

	private static final long serialVersionUID = 5490496891921278457L;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
	private Calendar calendar =  Calendar.getInstance();
	
	public GameDate(){
		calendar.set(1820,0,1);
	}
	
	public void tick(){
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
	}
	
	@Override
	public String toString(){
		return dateFormat.format(calendar.getTime());
	}
}
