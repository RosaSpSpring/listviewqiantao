package com.ko.listviewqiantao;

import android.app.Application;
import android.content.Context;

/**
 * @author lxm
 * @version 2019/6/24-16:16
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
public class MyApplication extends Application {
	private static Context context;
	@Override
	public void onCreate() {
		super.onCreate();
	    context = getApplicationContext();
	}
	public static Context getContent(){
		return  context;
	}
}
