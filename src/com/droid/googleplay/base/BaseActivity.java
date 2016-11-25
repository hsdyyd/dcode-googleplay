package com.droid.googleplay.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
* @author yidong
* @date 2016年11月25日 下午3:15:20
* @version 1.0
* @Description 
*/
public abstract class BaseActivity extends ActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		init();
		initView();
		initActionBar();
		initData();
		initListener();
	}

	public void init()
	{
	}

	public abstract void initView();

	public void initActionBar()
	{
	}

	public void initData()
	{
	}

	public void initListener()
	{
	}
}
