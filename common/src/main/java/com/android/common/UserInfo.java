package com.android.common;


import android.app.job.JobInfo;

import java.util.List;

/**
 * 用户信息
 * Created by 顾磊 on 2019/1/15.
 */
public class UserInfo extends BaseBean {
    public int dayJoin;	//非必 加入吉利的天数

    public long dayToday;//非必须 当日时间字符串

    public String empNo;//非必须 工号
    public String headline;//非必须  头像
    public String id;//非必须  员工id

    public String name;//非必须  员工姓名

    public String email;//员工邮箱

    public boolean isLeader;//是否领导

    public List<JobInfo> list; //公司部门岗位
}
