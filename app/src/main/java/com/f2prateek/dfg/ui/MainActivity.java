/**
 * Copyright 2013 prateek
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.dfg.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import butterknife.InjectView;
import com.f2prateek.dfg.AppConstants;
import com.f2prateek.dfg.R;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    Bus BUS;
    @InjectView(R.id.pager)
    ViewPager pager;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager.setAdapter(new DeviceFragmentPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(sharedPreferences.getInt(AppConstants.KEY_PREF_DEFAULT_DEVICE, 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BUS.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BUS.unregister(this);
    }
}
