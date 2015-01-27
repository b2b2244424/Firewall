package com.android.firewall;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

import com.android.firewall.ui.activity.AddFromContactsActivity;
import com.android.firewall.ui.dialog.EntryEditDialog;
import com.android.firewall.ui.dialog.EntryEditDialog.OKListener;
import com.android.firewall.ui.fragment.BlacklistFragment;
import com.android.firewall.ui.fragment.RecordFragment;
import com.android.firewall.util.LogUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView mBt1, mBt2, mBt3, mBt4;
	private ImageView mSelBg;
	private LinearLayout mTab_item_container;
	private FragmentManager mFM = null;

	
	LinearLayout content_container, content_container2;

	Intent m_Intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		init();

		toBlacklistFm();
	}

	private void init() {
		mTab_item_container = (LinearLayout) findViewById(R.id.tab_item_container);

		mBt1 = (ImageView) findViewById(R.id.tab_bt_1);
		mBt2 = (ImageView) findViewById(R.id.tab_bt_2);
		mBt4 = (ImageView) findViewById(R.id.tab_bt_4);

		mBt1.setOnClickListener(this);
		mBt2.setOnClickListener(this);
		mBt3.setOnClickListener(this);
		mBt4.setOnClickListener(this);

		mSelBg = (ImageView) findViewById(R.id.tab_bg_view);
		content_container = (LinearLayout) findViewById(R.id.content_container);
		content_container2 = (LinearLayout) findViewById(R.id.content_container2);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		LayoutParams lp = mSelBg.getLayoutParams();
		lp.width = mTab_item_container.getWidth() / 4;
	}

	private int mSelectIndex = 0;
	private View last, now;
	View v1, v2;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tab_bt_1:
			last = mTab_item_container.getChildAt(mSelectIndex);
			now = mTab_item_container.getChildAt(0);
			startAnimation(last, now);
			mSelectIndex = 0;

			toBlacklistFm();
			break;
		case R.id.tab_bt_2:
			last = mTab_item_container.getChildAt(mSelectIndex);
			now = mTab_item_container.getChildAt(1);
			startAnimation(last, now);
			mSelectIndex = 1;
			
			toRecordFm();
			break;
		case R.id.tab_bt_4:
			last = mTab_item_container.getChildAt(mSelectIndex);
			now = mTab_item_container.getChildAt(3);
			startAnimation(last, now);
			mSelectIndex = 3;

			break;
		default:
			break;
		}
	}

	private void startAnimation(View last, View now) {
		TranslateAnimation ta = new TranslateAnimation(last.getLeft(),
				now.getLeft(), 0, 0);
		ta.setDuration(300);
		ta.setFillAfter(true);
		mSelBg.startAnimation(ta);
	}


	private void toBlacklistFm() {
		Fragment f = new BlacklistFragment();
		if (null == mFM)
			mFM = getFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.content_container, f);
		ft.commit();
	}
	

	private void toRecordFm() {
		Fragment f = new RecordFragment();
		if (null == mFM)
			mFM = getFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(R.id.content_container, f);
		ft.commit();
	}

	private static Boolean isQuit = false;
	private Timer timer = new Timer();

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "click back again to close",
						Toast.LENGTH_SHORT).show();
				TimerTask task = null;
				task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				timer.schedule(task, 2000);
			} else {
				finish();
			}
		} else {
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_new_item:
			EntryEditDialog dialog=new EntryEditDialog();
			dialog.show(getFragmentManager(), "Entry Edit Dialog");
			break;
		case R.id.add_from_contacts_item:
			Intent intent=new Intent(this, AddFromContactsActivity.class);
			startActivity(intent);
			break;
		case R.id.delete_all_item:
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}	

}

