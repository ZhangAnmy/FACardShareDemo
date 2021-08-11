package com.huawei.facardsharedemo;

import com.huawei.facardsharedemo.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.agp.components.ComponentProvider;
import ohos.event.intentagent.IntentAgent;
import ohos.event.intentagent.IntentAgentConstant;
import ohos.event.intentagent.IntentAgentHelper;
import ohos.event.intentagent.IntentAgentInfo;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.ArrayList;
import java.util.List;

public class MainAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(0, 0, "com.huawei.facardsharedemo.MainAbility");
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }

    protected ProviderFormInfo onCreateForm(Intent intent) {
        long formId = intent.getLongParam(AbilitySlice.PARAM_FORM_IDENTITY_KEY, 0);
        String formName = intent.getStringParam(AbilitySlice.PARAM_FORM_NAME_KEY);
        int specificationId = intent.getIntParam(AbilitySlice.PARAM_FORM_DIMENSION_KEY, 0);
        boolean tempFlag = intent.getBooleanParam(AbilitySlice.PARAM_FORM_TEMPORARY_KEY, false);

        // 获取自定义数据
        IntentParams intentParams = intent.getParam(AbilitySlice.PARAM_FORM_CUSTOMIZE_KEY);

        HiLog.info(LABEL_LOG, "onCreateForm: " + formId + " " + formName + " " + specificationId);
        // 开发者需要根据卡片的名称以及外观规格获取对应的xml布局并构造卡片对象，此处ResourceTable.Layout_form_ability_layout_2_2仅为示例
        ProviderFormInfo formInfo = new ProviderFormInfo(ResourceTable.Layout_form_image_with_info_date_card_2_2, this);
        // 获取卡片信息
//        String formData = getInitFormData(formName, specificationId);
        ComponentProvider componentProvider = new ComponentProvider();
        componentProvider.setText(ResourceTable.Id_title, "Form Data");
        // 针对title控件设置事件
//        componentProvider.setIntentAgent(ResourceTable.Id_title, startAbilityIntentAgent());
        formInfo.mergeActions(componentProvider);

        HiLog.info(LABEL_LOG, "onCreateForm finish.......");
        return formInfo;
    }

    private IntentAgent startAbilityIntentAgent() {
        final Intent intent = new Intent();
        final Operation operation = new Intent.OperationBuilder()
                .withFlags(Intent.FLAG_NOT_OHOS_COMPONENT)
                .withDeviceId("")
                .withBundleName("com.huawei.facardsharedemo")
                .withAbilityName("com.huawei.facardsharedemo.ShareAbility")
                .build();
        intent.setOperation(operation);
        final List<Intent> intentList = new ArrayList<>();
        intentList.add(intent);
        final List<IntentAgentConstant.Flags> flags = new ArrayList<>();
        flags.add(IntentAgentConstant.Flags.UPDATE_PRESENT_FLAG);
        final IntentAgentInfo paramsInfo = new IntentAgentInfo(10001,
                IntentAgentConstant.OperationType.START_ABILITY, flags, intentList, null);
        return IntentAgentHelper.getIntentAgent(this, paramsInfo);
    }

    @Override
    protected void onUpdateForm(long formId) {
        HiLog.info(LABEL_LOG, "onUpdateForm");
        super.onUpdateForm(formId);
    }

    @Override
    protected void onDeleteForm(long formId) {
        HiLog.info(LABEL_LOG, "onDeleteForm: formId=" + formId);
        super.onDeleteForm(formId);
    }

    @Override
    protected void onTriggerFormEvent(long formId, String message) {
        HiLog.info(LABEL_LOG, "onTriggerFormEvent: " + message);
        super.onTriggerFormEvent(formId, message);
    }
}
