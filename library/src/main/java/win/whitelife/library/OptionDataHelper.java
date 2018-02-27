package win.whitelife.library;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author wuzefeng
 * @date 2018/2/24
 */
public class OptionDataHelper {


    private static final String OPTION_FILE="ZF_KEYBROAD";

    public static final String FONT_SIZE="FONT_SIZE";

    public static final String SKIN="SKIN";

    public static void saveData(Context context,String key,String value){
        SharedPreferences settings= context.getSharedPreferences(OPTION_FILE, 0);
        SharedPreferences.Editor edit = settings.edit();
        edit.putString(key,value);
        edit.commit();
    }


    public static String getData(Context context,String key){
        SharedPreferences settings= context.getSharedPreferences(OPTION_FILE, 0);
        return settings.getString(key,null);
    }
}
