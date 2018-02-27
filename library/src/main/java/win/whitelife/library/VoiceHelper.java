package win.whitelife.library;

import android.view.MotionEvent;
import android.view.View;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wuzefeng
 * @date 2018/2/26
 */
public class VoiceHelper implements View.OnTouchListener{

    private VoiceView mVoiceView;

    /**
     * SDK 内部核心 EventManager 类
     */
    private EventManager asr;

    private boolean isRecording;

    private Map<String,Object> recodingMap;

    /**
     * SDK 内部核心 事件回调类， 用于开发者写自己的识别回调逻辑
     */
    private EventListener eventListener=new EventListener() {
        @Override
        public void onEvent(String name, String params, byte[] data, int offset, int length) {
            if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
                // 引擎准备就绪，可以开始说话
            } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_BEGIN)) {
                // 检测到用户的已经开始说话
            } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_END)) {
                // 检测到用户的已经停止说话
            } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
                RecogResult recogResult = RecogResult.parseJson(params);
                // 临时识别结果, 长语音模式需要从此消息中取出结果
                String[] results = recogResult.getResultsRecognition();
                if (recogResult.isFinalResult()) {
                    mVoiceCallback.voiceFinalCallback(results[0]);
                } else if (recogResult.isPartialResult()) {
                    mVoiceCallback.voiceCallback(results[0]);
                } else if (recogResult.isNluResult()) {
                }
            } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
                // 识别结束， 最终识别结果或可能的错误

            } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_LONG_SPEECH)) { //长语音
                // 长语音识别结束
            } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_EXIT)) {
                //识别结束，释放资源
            }
        }
    };


    public VoiceHelper(VoiceView voiceView,VoiceCallback callback) {
        mVoiceView = voiceView;
        mVoiceCallback=callback;
        mVoiceView.setOnTouchListener(this);
        asr = EventManagerFactory.create(voiceView.getContext(), "asr");
        asr.registerListener(eventListener);
        recodingMap=new HashMap<>();
        recodingMap.put("accept-audio-data",false);
        recodingMap.put("accept-punctuation-data",false);
        recodingMap.put("accept-audio-volume",true);
        recodingMap.put("pid",15361);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //按下，未录音，未解析
        if(event.getAction()==MotionEvent.ACTION_DOWN&&!isRecording){
            mVoiceView.setText("正在输入");
            startRecordVoice();
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            //松开，未解析
            mVoiceView.setText("触摸输入");
            //结束录音
            asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0);
            isRecording=false;
        }
        return true;
    }


    public void setVoiceView(VoiceView voiceView) {
        mVoiceView = voiceView;
        mVoiceView.setOnTouchListener(this);
    }


    /**
     * 开始录音
     */
    private void startRecordVoice(){
        isRecording=true;
        String json = new JSONObject(recodingMap).toString();
        asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
    }

    private VoiceCallback mVoiceCallback;


    interface VoiceCallback{
        void voiceCallback(String voice);
        void voiceFinalCallback(String voice);
    }

}
