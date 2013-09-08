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
    ///                           �C�x���g�n���h��
    ///
    ///====================================================================

    @Override
    public void onCreate() {
        super.onCreate();

        //���C�A�E�g�ǂݍ���
        //�T�C�Y�ύX����ꍇ�A�I�������Ă��炱�̏���������Ηǂ��Ǝv����B
        setContentView(R.layout.widget);

        //�A�v���^�C�g���ݒ�
        setTitle(R.string.app_name);

        //�E�C���h�E�����̎擾
        SmallAppWindow.Attributes attr = getWindow().getAttributes();

        //���ݒ�
        attr.width = getResources().getDimensionPixelSize(R.dimen.width);
        //�����ݒ�
        attr.height = getResources().getDimensionPixelSize(R.dimen.height);

        //�T�C�Y�ύX����
        attr.flags |= SmallAppWindow.Attributes.FLAG_RESIZABLE;

        //�^�C�g���o�[����
        attr.flags |= SmallAppWindow.Attributes.FLAG_NO_TITLEBAR;

        //�n�[�h�E�F�A�A�N�Z�ꃌ�[�V���������_�����O�I��
        attr.flags |= SmallAppWindow.Attributes.FLAG_HARDWARE_ACCELERATED;

        //�ݒ�̔��f
        getWindow().setAttributes(attr);

        //�I�v�V�������j���[�̏�����
        setupOptionMenu();

        //�C���[�W�r���[�̏�����
        iv = (ImageView) findViewById(R.id.liplisImage);

        //�摜�ύX�e�X�g
        test();

        //�^�C�}�[�e�X�g
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
    ///                           ����������
    ///
    ///====================================================================

    /// <summary>
    //  MethodType : base
    /// MethodName : -
    /// �n���h���[�������s����ݒ�B
    /// �ȉ��̂Ƃ���L�q���Arun�����s����ƁA���\�b�h���Őݒ肵���C���^�[�o����
    /// �J��Ԃ����s�����B
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
    ///                           ��ʏ���
    ///
    ///====================================================================


    private void test() {
        iv.setImageResource(R.drawable.normal_1_1_1);
    }

    private void setImage(int id) {
        iv.setImageResource(id);
    }



}
