package pacman372.dementiaaid.EntityClasses;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jieliang on 22/09/2015.
 */
public class Notification {

   public Context context;
    public Intent intent;

    public void Notification(Context context,Intent intent)

    {
        this.context=context;
        this.intent=intent;

    }

}
