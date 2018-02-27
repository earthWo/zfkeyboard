package win.whitelife.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * @author wuzefeng
 * @date 2018/2/24
 */
public class FontSizeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private RadioButton mBigButton;

    private RadioButton mNormalButton;

    private RadioButton mSmallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_size);
        init();
    }


    public void init(){
        mBigButton=findViewById(R.id.tv_font_size_big);
        mNormalButton=findViewById(R.id.tv_font_size_normal);
        mSmallButton=findViewById(R.id.tv_font_size_small);
        String fontSize=OptionDataHelper.getData(this,OptionDataHelper.FONT_SIZE);
        mBigButton.setOnCheckedChangeListener(this);
        mNormalButton.setOnCheckedChangeListener(this);
        mSmallButton.setOnCheckedChangeListener(this);
        mBigButton.setChecked(mBigButton.getTag().toString().equals(fontSize));
        mSmallButton.setChecked(mSmallButton.getTag().toString().equals(fontSize));
        mNormalButton.setChecked(!mBigButton.isChecked()&&!mSmallButton.isChecked());
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            OptionDataHelper.saveData(this,OptionDataHelper.FONT_SIZE,buttonView.getTag().toString());
        }
    }

}
