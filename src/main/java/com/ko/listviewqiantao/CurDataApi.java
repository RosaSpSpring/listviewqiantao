package com.ko.listviewqiantao;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author lxm
 * @version 2019/6/25-11:25
 * @des ${TODO}
 * @updateDes ${TODO}
 * @updateAuthor $Author$
 */
public interface CurDataApi {
	@GET("/co2Incubator/cur_data.json")
	Call<CurrentBean> getCurData();
}
