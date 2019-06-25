package com.ko.listviewqiantao;

/**
 * @author lxm
 * @version 2019/6/25-15:39
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * 订单按时间排序，最近的日期显示在上面
 */
public class ComparatorDate implements Comparator {
	public static final String TAG = "ComparatorDate";


	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	public int compare(Object obj1, Object obj2) {
		CurrentBean.DataBean mybean1 = (CurrentBean.DataBean) obj1;
		CurrentBean.DataBean mybean2 = (CurrentBean.DataBean) obj2;

		//   return t1.getTradetime().compareTo(t2.getTradetime());  // 时间格式不好，不然可以直接这样比较
		Date d1, d2;
		try {
			d1 = format.parse(mybean1.getDate());
			d2 = format.parse(mybean2.getDate());
		} catch (ParseException e) {
			// 解析出错，则不进行排序
			Log.e(TAG, "ComparatorDate--compare--SimpleDateFormat.parse--error");
			return 0;
		}
		if (d1.before(d2)) {
			return 1;
		} else {
			return -1;
		}
	}
}

