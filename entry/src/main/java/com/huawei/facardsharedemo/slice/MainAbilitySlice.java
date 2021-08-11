package com.huawei.facardsharedemo.slice;

import com.huawei.facardsharedemo.ResourceTable;
import com.huawei.facardsharedemo.utils.DateUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainAbilitySlice extends AbilitySlice {
    private static final int TIME_LENGTH = 2;
    private static final long SEND_PERIOD = 1000L;
    private Text dateText;
    private Text hourText;
    private Text minText;
    private Text secondText;
    private Timer timer;
    private MyEventHandle myEventHandle;
    private AbilitySlice slice = this;
    private EventRunner runner;

    private Runnable runnable = new Runnable() {
        private void initHandler() {
            runner = EventRunner.getMainEventRunner();
            if (runner == null) {
                return;
            }
            myEventHandle = new MyEventHandle(runner);
        }

        @Override
        public void run() {
            // 初始化认证对象
            initHandler();
        }
    };

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_form_image_with_info_date_card_2_4);
        initComponent();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runnable.run();
                        myEventHandle.sendEvent(1);
                    }
                },
                0,
                SEND_PERIOD);
    }

    /**
     * Init Component
     */
    private void initComponent() {
        Calendar now = Calendar.getInstance();
        Component dateComponent = slice.findComponentById(ResourceTable.Id_date);
        if (dateComponent != null && dateComponent instanceof Text) {
            dateText = (Text) dateComponent;
            dateText.setText(DateUtils.getCurrentDate("yyyy-MM-dd"));
        }
        Component hourComponent = slice.findComponentById(ResourceTable.Id_hour);
        if (hourComponent != null && hourComponent instanceof Text) {
            hourText = (Text) hourComponent;
            int hour = now.get(Calendar.HOUR_OF_DAY);
            setTextValue(hour, hourText);
        }
        Component minComponent = findComponentById(ResourceTable.Id_min);
        if (minComponent != null && minComponent instanceof Text) {
            minText = (Text) minComponent;
            int min = now.get(Calendar.MINUTE);
            setTextValue(min, minText);
        }
        Component secComponent = findComponentById(ResourceTable.Id_sec);
        if (secComponent != null && secComponent instanceof Text) {
            secondText = (Text) secComponent;
            int second = now.get(Calendar.SECOND);
            setTextValue(second, secondText);
        }
    }

    private void setTextValue(int now, Text text) {
        if (String.valueOf(now).length() < TIME_LENGTH) {
            text.setText("0" + now);
        } else {
            text.setText(now + "");
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    private class MyEventHandle extends EventHandler {
        MyEventHandle(EventRunner runner) throws IllegalArgumentException {
            super(runner);
        }

        @Override
        protected void processEvent(InnerEvent event) {
            super.processEvent(event);
            int eventId = event.eventId;
            if (eventId == 1) {
                // 更新页面
                initComponent();
            }
        }
    }

    @Override
    protected void onStop() {
        timer.cancel();
    }
}
