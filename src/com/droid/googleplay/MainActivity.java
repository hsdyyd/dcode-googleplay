package com.droid.googleplay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author dongyi
 * 使用ActionBar时Activity继承自ActionBarActivity,需要引入v7包
 * 修改主题:@style/Theme.AppComp.xxx
 */
public class MainActivity extends ActionBarActivity
{
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mActionBar = getSupportActionBar();
		mActionBar.setTitle("GooglePlay");
//		mActionBar.setSubtitle("good app");
		mActionBar.setIcon(R.drawable.ic_launcher);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
