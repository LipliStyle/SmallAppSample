/*
 * Copyright 2011, 2012 Sony Corporation
 * Copyright (C) 2012-2013 Sony Mobile Communications AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package liplis.small.sample;

import java.util.Timer;
import java.util.TimerTask;

import liplis.small.sample.R;

import com.sony.smallapp.SmallAppWindow;
import com.sony.smallapp.SmallApplication;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainApplication extends SmallApplication {

	final static String TAG = "MyService";
	final int INTERVAL_PERIOD = 5000;
	Timer timer = new Timer();
	ImageView iv;
	int idx = 0;




    ///====================================================================
    ///
    ///                           イベントハンドラ
    ///
    ///====================================================================

    @Override
    public void onCreate() {
        super.onCreate();

        //レイアウト読み込み
        //サイズ変更する場合、選択させてからこの処理をすれば良いと思われる。
        setContentView(R.layout.widget);

        //アプリタイトル設定
        setTitle(R.string.app_name);

        //ウインドウ属性の取得
        SmallAppWindow.Attributes attr = getWindow().getAttributes();

        //幅設定
        attr.width = getResources().getDimensionPixelSize(R.dimen.width);
        //高さ設定
        attr.height = getResources().getDimensionPixelSize(R.dimen.height);

        //サイズ変更許可
        attr.flags |= SmallAppWindow.Attributes.FLAG_RESIZABLE;

        //タイトルバー除去
        attr.flags |= SmallAppWindow.Attributes.FLAG_NO_TITLEBAR;

        //ハードウェアアクセれレーションレンダリングオン
        attr.flags |= SmallAppWindow.Attributes.FLAG_HARDWARE_ACCELERATED;

        //設定の反映
        getWindow().setAttributes(attr);

        //オプションメニューの初期化
        setupOptionMenu();

        //イメージビューの初期化
        iv = (ImageView) findViewById(R.id.liplisImage);

        //画像変更テスト
        test();

        //タイマーテスト
        initTimer();
    }

    private void onUpdate()
    {
    	if (idx == 0)
    	{
    		setImage(R.drawable.normal_1_1_1);
    		idx = 1;
    	}
    	else if(idx == 1)
    	{
    		setImage(R.drawable.normal_1_2_1);
    		idx = 2;
    	}
    	else if(idx == 2)
    	{
    		setImage(R.drawable.normal_1_3_1);
    		idx = 3;
    	}
    	else if(idx == 3)
    	{
    		setImage(R.drawable.normal_1_2_1);
    		idx = 0;
    	}
    	else
    	{
    		setImage(R.drawable.normal_1_1_1);
    		idx = 1;
    	}
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        handler=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupOptionMenu() {
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);

        final View optionMenu = header.findViewById(R.id.option_menu);
        optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainApplication.this, optionMenu);
                popup.getMenuInflater().inflate(R.menu.menus, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainApplication.this,
                                R.string.menu_clicked, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popup.show();
            }
        });

        /* Deploy the option menu in the header area of the titlebar */
        getWindow().setHeaderView(header);
    }

    ///====================================================================
    ///
    ///                           初期化処理
    ///
    ///====================================================================

    /// <summary>
    //  MethodType : base
    /// MethodName : -
    /// ハンドラーを定期実行する設定。
    /// 以下のとおり記述し、runを実行すると、メソッド内で設定したインターバルで
    /// 繰り返し実行される。
    /// </summary>
	Handler handler = new Handler();
	boolean running = true;
	Runnable runnable = new Runnable() {
		public void run() {
			if (running) {
				onUpdate();
			}
			handler.postDelayed(runnable, 100);
		}
	};


    private void initTimer()
    {
    	runnable.run();
    }


    ///====================================================================
    ///
    ///                           一般処理
    ///
    ///====================================================================


    private void test() {
        iv.setImageResource(R.drawable.normal_1_1_1);
    }

    private void setImage(int id) {
        iv.setImageResource(id);
    }



}
