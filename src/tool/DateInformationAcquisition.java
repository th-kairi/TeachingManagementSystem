package tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateInformationAcquisition {

	/**
	 * 指定したフォーマットに修正して文字列（String型）を返すメソッド
	 *
	 *
	 * @param argDate：LocalDateTime型の日付
	 * @param format：yyyy/MM/dd or yyyy-MM-dd
	 * @return 変換した日付（String型）
	 */
	public String getDateFormatter(LocalDateTime argDate, String format){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        String formatDate = argDate.format(dateTimeFormatter);

		return formatDate;
	}

	/**
	 * 月末日付を返します。
	 *
	 * @param argDate：LocalDateTime型の日付
	 * @return 月末日付
	 */
	public int getLastDate(String strDate) {
        System.out.println("===========================================================================");

	    int yyyy = Integer.parseInt(strDate.substring(0,4));
	    int MM = Integer.parseInt(strDate.substring(5,7));
	    int dd = Integer.parseInt(strDate.substring(8,10));
	    Calendar cal = Calendar.getInstance();
	    cal.set(yyyy,MM-1,dd);
	    int lastDate = cal.getActualMaximum(Calendar.DATE);

	    System.out.println(strDate + "を引数で受け取りました");
	    System.out.println("受け取った日付を基準日に割り出した最終日は" + lastDate + "日です");
        System.out.println("===========================================================================");
	    return lastDate;
	}



}
