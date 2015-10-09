package pacman372.dementiaaid;

import android.content.Context;
import android.content.Intent;

import junit.framework.TestCase;

import pacman372.dementiaaid.EntityClasses.Notification;
import pacman372.dementiaaid.Notification.customHandler;

/**
 * Created by fuqian on 8/09/2015.
 */
public class customHandlerTest extends TestCase {

    Notification notification;
    public Context context;
    public Intent intent;
    public void setUp() throws Exception {
        super.setUp();
        notification=new Notification();

    }

    public void tearDown() throws Exception {

    }

   public void testNotification()throws  Exception{

      notification.Notification(context, intent);


   }
}