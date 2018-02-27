package win.whitelife.library;

import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author wuzefeng
 * @date 2018/2/12
 */
public class ZFIme extends InputMethodService implements KeyboardView.OnKeyboardActionListener,View.OnClickListener {

    private ZFKeyboardView keyboard;

    private Keyboard mKeyboardView;

    private String fontSize;

    private ConstraintLayout mLayout;

    private FrameLayout mFrameLayout;

    private String skin;

    private VoiceHelper mVoiceHelper;

    @Override
    public View onCreateInputView() {
        mLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.keyboard, null);
        mFrameLayout=mLayout.findViewById(R.id.fl_contain);
        mLayout.findViewById(R.id.iv_option).setOnClickListener(this);
        mLayout.findViewById(R.id.iv_skin).setOnClickListener(this);
        mLayout.findViewById(R.id.iv_voice).setOnClickListener(this);
        mLayout.findViewById(R.id.iv_close).setOnClickListener(this);
        fontSize=OptionDataHelper.getData(this,OptionDataHelper.FONT_SIZE);
        skin=OptionDataHelper.getData(this,OptionDataHelper.SKIN);
        createNormalKeyboard(R.xml.keyboard_keys);
        return mLayout;
    }


    private void createNormalKeyboard(int res){
        ViewGroup mViewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_normal,null);
        keyboard=mViewGroup.findViewById(R.id.keyboard);
        mKeyboardView = new Keyboard(this, res);
        keyboard.setKeyboard(mKeyboardView);
        keyboard.setOnKeyboardActionListener(this);
        keyboard.setFontSize(fontSize);
        keyboard.setSkin(skin);
        mFrameLayout.removeView(mViewGroup);
        mFrameLayout.addView(mViewGroup);
    }

    private void createSymbolKeyboard(int res){
        ViewGroup mViewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_symbol,null);
        keyboard=mViewGroup.findViewById(R.id.keyboard);
        mKeyboardView = new Keyboard(this, res);
        keyboard.setKeyboard(mKeyboardView);
        keyboard.setOnKeyboardActionListener(this);
        keyboard.setFontSize(fontSize);
        keyboard.setSkin(skin);
        mFrameLayout.removeView(mViewGroup);
        mFrameLayout.addView(mViewGroup);
        for(int i=0;i<mViewGroup.getChildCount();i++){
            View v=mViewGroup.getChildAt(i);
            if(v instanceof ImageView){
                v.setOnClickListener(this);
            }
        }
    }

    private void createNumberKeyboard(int res){
        createNormalKeyboard(res);
    }


    private void createVoiceKeyboard(){
        ViewGroup mViewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_voice, null);
        mFrameLayout.removeView(mViewGroup);
        mFrameLayout.addView(mViewGroup);
        mViewGroup.findViewById(R.id.iv_back).setOnClickListener(this);
        mViewGroup.findViewById(R.id.tv_douhao).setOnClickListener(this);
        mViewGroup.findViewById(R.id.tv_juhao).setOnClickListener(this);
        mViewGroup.findViewById(R.id.tv_wenhao).setOnClickListener(this);
        mViewGroup.findViewById(R.id.iv_space).setOnClickListener(this);
        mViewGroup.findViewById(R.id.iv_delete).setOnClickListener(this);
        mViewGroup.findViewById(R.id.iv_enter).setOnClickListener(this);
        if(mVoiceHelper==null){
            mVoiceHelper=new VoiceHelper((VoiceView) mViewGroup.findViewById(R.id.vv_voice),mVoiceCallback);
        }else{
            mVoiceHelper.setVoiceView((VoiceView) mViewGroup.findViewById(R.id.vv_voice));
        }
    }

    private VoiceHelper.VoiceCallback mVoiceCallback=new VoiceHelper.VoiceCallback() {
        @Override
        public void voiceCallback(String voice) {
            getCurrentInputConnection().commitText(voice,1);
        }

        @Override
        public void voiceFinalCallback(String voice) {

        }
    };


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        //SHIFT
        if(primaryCode==PrimaryCodeUtil.SHIFT_CODE){

        }else if(primaryCode==PrimaryCodeUtil.ENTER_CODE){
            getCurrentInputConnection().commitText("\n",1);
        }else if(primaryCode==PrimaryCodeUtil.DELETE_CODE){
            //删除
            CharSequence sequence= getCurrentInputConnection().getSelectedText(0);
            if(sequence!=null){
                getCurrentInputConnection().commitText("",1);
            }else{
                getCurrentInputConnection().deleteSurroundingText(1,0);
            }
        }else if(primaryCode==PrimaryCodeUtil.SPACE_CODE){
            getCurrentInputConnection().commitText(" ",1);
            //空格
        }else if(primaryCode==PrimaryCodeUtil.SYMBOL_CODE){
            //符号键盘
            createSymbolKeyboard(R.xml.keyboard_symbol_keys);
        }else if(primaryCode==PrimaryCodeUtil.CHINESE_CODE){
            //中文键盘
        }else if(primaryCode==PrimaryCodeUtil.NUMBER_CODE){
            //数字键盘
            createNumberKeyboard(R.xml.keyboard_number_keys);
        }else if(primaryCode==PrimaryCodeUtil.NORMAL_CODE){
            //普通键盘
            createNumberKeyboard(R.xml.keyboard_keys);
        }else{
            getCurrentInputConnection().commitText(Character.toString((char) primaryCode),1);
        }

    }

    @Override
    public void onText(CharSequence text) {

    }


    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_option){
            Intent intent=new Intent(this,OptionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(v.getId()==R.id.iv_skin){
            Intent intent=new Intent(this,SkinActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(v.getId()==R.id.iv_close){
            //关闭键盘
            hideWindow();
        }else if(v.getId()==R.id.iv_voice){
            //语音输入
            createVoiceKeyboard();
        }else if(v.getId()==R.id.iv_space){
            onKey(PrimaryCodeUtil.SPACE_CODE,null);
        }else if(v.getId()==R.id.iv_delete){
            onKey(PrimaryCodeUtil.DELETE_CODE,null);
        }else if(v.getId()==R.id.iv_enter){
            onKey(PrimaryCodeUtil.ENTER_CODE,null);
        }else if(v.getId()==R.id.iv_back){
            onKey(PrimaryCodeUtil.NORMAL_CODE,null);
        }else if(v.getId()==R.id.tv_douhao){
            //,
            onKey(44,null);
        }else if(v.getId()==R.id.tv_juhao){
            //.
            onKey(46,null);
        }else if(v.getId()==R.id.tv_wenhao){
            //?
            onKey(63,null);
        }
    }
}
