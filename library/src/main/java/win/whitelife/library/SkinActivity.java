package win.whitelife.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * @author wuzefeng
 * @date 2018/2/24
 */
public class SkinActivity extends AppCompatActivity implements View.OnClickListener {

    private View mNightView;

    private View mLightView;

    private View mRedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        init();
    }


    public void init(){
        mNightView=findViewById(R.id.iv_night);
        mLightView=findViewById(R.id.iv_light);
        mRedView=findViewById(R.id.iv_red);
        mNightView.setOnClickListener(this);
        mLightView.setOnClickListener(this);
        mRedView.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        OptionDataHelper.saveData(this,OptionDataHelper.SKIN,v.getTag().toString());
        finish();
    }
}
