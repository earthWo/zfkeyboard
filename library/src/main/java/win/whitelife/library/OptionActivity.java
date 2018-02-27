package win.whitelife.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author wuzefeng
 * @date 2018/2/24
 */
public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        findViewById(R.id.tv_font_size).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_font_size){
            Intent intent=new Intent(this,FontSizeActivity.class);
            startActivity(intent);
        }
    }
}
