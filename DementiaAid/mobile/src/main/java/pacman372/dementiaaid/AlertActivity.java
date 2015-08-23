package pacman372.dementiaaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by jieliang on 23/08/2015.
 */
public class AlertActivity extends ActionBarActivity

{

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Button test_popup_button=(Button) findViewById(R.id.test_popup_button);
        test_popup_button.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     startActivity(new Intent(AlertActivity.this,Pop.class));

                                                 }

                                             }

        );



    }


}
