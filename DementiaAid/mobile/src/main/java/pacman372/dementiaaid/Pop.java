package pacman372.dementiaaid;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by jieliang on 23/08/2015.
 */
public class Pop extends Activity{
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
           super.onCreate(savedInstanceState);

              setContentView(R.layout.popup_window);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));

    }

}
