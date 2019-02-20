package mmt.source.com.babitafuels.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.dao.UserDetailsDao;
import mmt.source.com.babitafuels.util.DbHelper;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);


        DbHelper.createVendorDbHelper(SplashScreenActivity.this);
        UserDetailsDao vendorDetailsDao = new UserDetailsDao();
        vendorDetailsDao.getUserDetails();

        imageView.startAnimation(animation_2);
        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_3);
                finish();
                if(FuelMnt.getInstance().getUsrInfo().getUsrId().equals("-1")) {
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(getBaseContext(), HomeScreenNewActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}