package com.toolbox.kostovtd;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toolbox.kostovtd.util.AnimationsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by todor.kostov on 1.12.2016 г..
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private boolean isResized = false;
    private float screenDensityMultiplier = 0;

    @BindView(R.id.nav_bar)
    Toolbar toolbar;

    @BindView(R.id.main_container)
    RelativeLayout mainContainer;

    @BindView(R.id.header_container)
    LinearLayout headerContainer;

    @BindView(R.id.text_contacts)
    TextView textContacts;

    @BindView(R.id.text_partners)
    TextView textPartners;

    @BindView(R.id.text_settings)
    TextView textSettings;

    @BindView(R.id.text_erase_database)
    TextView textEraseDatabase;

    @BindView(R.id.text_erase_tags)
    TextView textEraseTags;

    @BindView(R.id.text_log_out)
    TextView textLogOut;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: hit");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        ButterKnife.bind(this);

        setUpRecyclerViewAndAdapter();

        setUpToolbar();

        screenDensityMultiplier = AnimationsUtil.getScreenDensity(MainActivity.this);
        Log.d(TAG, "screenDensityMultiplier: " + screenDensityMultiplier);
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: hit");
        super.onResume();
    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: hit");
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: hit");
        super.onDestroy();
    }


    private void setUpRecyclerViewAndAdapter() {
        List<Integer> data = new ArrayList<>();
        data.add(R.drawable.ic_account_balance_white);
        data.add(R.drawable.ic_backup_white);
        data.add(R.drawable.ic_build_white);
        data.add(R.drawable.ic_fingerprint_white);
        data.add(R.drawable.ic_menu_white);
        data.add(R.drawable.ic_account_balance_white);
        data.add(R.drawable.ic_backup_white);
        data.add(R.drawable.ic_build_white);
        data.add(R.drawable.ic_fingerprint_white);
        data.add(R.drawable.ic_menu_white);
        data.add(R.drawable.ic_account_balance_white);
        data.add(R.drawable.ic_backup_white);
        data.add(R.drawable.ic_build_white);
        data.add(R.drawable.ic_fingerprint_white);
        data.add(R.drawable.ic_menu_white);
        CustomAdapter adapter = new CustomAdapter(MainActivity.this, data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    /**
     * Set up the {@link Toolbar) the current screen
     */
    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.nav_bar);

        // set to be the same as the hamburger menu used for navigation drawer
        toolbar.setLogo(R.drawable.ic_menu_white);

        // need to set a click listener for the logo
        // in this particular case, the logo view is the
        // first (0) child of the Toolbar object
        toolbar.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle the scaling and translating
                // animations logic
                if(isResized) {
                    fadeOutAndTranslateHeaderContainer();
                    scaleUpAndTranslateMainContainer();
                    fadeOutAndTranslateSideMenu();
                    isResized = false;
                } else {
                    fadeInAndTranslateHeaderContainer();
                    scaleDownAndTranslateMainContainer();
                    fadeInAndTranslateSideMenu();
                    isResized = true;
                }
            }
        });
        setSupportActionBar(toolbar);
    }


    private void scaleDownAndTranslateMainContainer() {
        int pxMdpi = 170; // the default value for mdpi screens
                          // the actual px value will be calculated based on
                          // the current screenDensityMultiplier
        int actualPx = Math.round(pxMdpi * screenDensityMultiplier);

        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> scaleDownAndTranslateAnimatorList = AnimationsUtil.scaleXYAnimation(mainContainer, 1f, 0.8f, AnimationsUtil.ANIMATION_DURATION_500_MS);
        float translateXDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPx);
        Animator translateXAnimator =  AnimationsUtil.translateXAnimation(mainContainer, translateXDp, AnimationsUtil.ANIMATION_DURATION_500_MS);
        scaleDownAndTranslateAnimatorList.add(translateXAnimator);
        animatorSet.playTogether(scaleDownAndTranslateAnimatorList);
        animatorSet.start();
    }


    private void scaleUpAndTranslateMainContainer() {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> scaleUpAndTranslateAnimatorList = AnimationsUtil.scaleXYAnimation(mainContainer, 0.8f, 1f, AnimationsUtil.ANIMATION_DURATION_500_MS);
        Animator translateXAnimator =  AnimationsUtil.translateXAnimation(mainContainer, 0, AnimationsUtil.ANIMATION_DURATION_500_MS);
        scaleUpAndTranslateAnimatorList.add(translateXAnimator);
        animatorSet.playTogether(scaleUpAndTranslateAnimatorList);
        animatorSet.start();
    }


    private void fadeInAndTranslateSideMenu() {
        int pxYContactsMdpi = -55; // the default value for mdpi screens
                          // the actual px value will be calculated based on
                          // the current screenDensityMultiplier
        int actualPxYContactsMdpi = Math.round(pxYContactsMdpi * screenDensityMultiplier);

        AnimatorSet animatorSetContacts = new AnimatorSet();
        Animator fadeInContactsAnimator = AnimationsUtil.fadeAnimation(textContacts, AnimationsUtil.FULLY_TRANSPARENT,
                AnimationsUtil.FULLY_VISIBLE, AnimationsUtil.ANIMATION_DURATION_400_MS);
        float translateYContactsDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPxYContactsMdpi);
        Animator translateYContactsAnimator = AnimationsUtil.translateYAnimation(textContacts, translateYContactsDp, AnimationsUtil.ANIMATION_DURATION_400_MS);
        animatorSetContacts.play(fadeInContactsAnimator)
                .with(translateYContactsAnimator);


        int pxYPartnersMdpi = -55;
        int actualPxYPartnersMdpi = Math.round(pxYPartnersMdpi * screenDensityMultiplier);
        AnimatorSet animatorSetPartners = new AnimatorSet();
        Animator fadeInPartnersAnimator = AnimationsUtil.fadeAnimation(textPartners, AnimationsUtil.FULLY_TRANSPARENT,
                AnimationsUtil.FULLY_VISIBLE, AnimationsUtil.ANIMATION_DURATION_350_MS);
        float translateYPartnersDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPxYPartnersMdpi);
        Animator translateYPartnersAnimator = AnimationsUtil.translateYAnimation(textPartners, translateYPartnersDp, AnimationsUtil.ANIMATION_DURATION_350_MS);
        animatorSetPartners.play(fadeInPartnersAnimator).after(AnimationsUtil.ANIMATION_DELAY_200_MS)
                .with(translateYPartnersAnimator);


        int pxYSettingsMdpi = -55;
        int actualPxYSettingsMdpi = Math.round(pxYSettingsMdpi * screenDensityMultiplier);
        AnimatorSet animatorSetSettings = new AnimatorSet();
        Animator fadeInSettingsAnimator = AnimationsUtil.fadeAnimation(textSettings, AnimationsUtil.FULLY_TRANSPARENT,
                AnimationsUtil.FULLY_VISIBLE, AnimationsUtil.ANIMATION_DURATION_300_MS);
        float translateYSettingsDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPxYSettingsMdpi);
        Animator translateYSettingsAnimator = AnimationsUtil.translateYAnimation(textSettings, translateYSettingsDp, AnimationsUtil.ANIMATION_DURATION_300_MS);
        animatorSetSettings.play(fadeInSettingsAnimator).after(AnimationsUtil.ANIMATION_DELAY_400_MS)
                .with(translateYSettingsAnimator);


        int pxYDatabaseMdpi = -30;
        int actualPxYDatabaseMdpi = Math.round(pxYDatabaseMdpi * screenDensityMultiplier);
        AnimatorSet animatorSetEraseDatabase = new AnimatorSet();
        Animator fadeInDatabaseAnimator = AnimationsUtil.fadeAnimation(textEraseDatabase, AnimationsUtil.FULLY_TRANSPARENT,
                AnimationsUtil.FULLY_VISIBLE, AnimationsUtil.ANIMATION_DURATION_250_MS);
        float translateYDatabaseDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPxYDatabaseMdpi);
        Animator translateYDatabaseAnimator = AnimationsUtil.translateYAnimation(textEraseDatabase, translateYDatabaseDp, AnimationsUtil.ANIMATION_DURATION_250_MS);
        animatorSetEraseDatabase.play(fadeInDatabaseAnimator).after(AnimationsUtil.ANIMATION_DELAY_600_MS)
                .with(translateYDatabaseAnimator);


        int pxYTagsMdpi = -30;
        int actualPxYTagsMdpi = Math.round(pxYTagsMdpi * screenDensityMultiplier);
        AnimatorSet animatorSetEraseTags = new AnimatorSet();
        Animator fadeInTagsAnimator = AnimationsUtil.fadeAnimation(textEraseTags, AnimationsUtil.FULLY_TRANSPARENT,
                AnimationsUtil.FULLY_VISIBLE, AnimationsUtil.ANIMATION_DURATION_200_MS);
        float translateYTagsDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPxYTagsMdpi);
        Animator translateYTagsAnimator = AnimationsUtil.translateYAnimation(textEraseTags, translateYTagsDp, AnimationsUtil.ANIMATION_DURATION_200_MS);
        animatorSetEraseTags.play(fadeInTagsAnimator).after(AnimationsUtil.ANIMATION_DELAY_800_MS)
                .with(translateYTagsAnimator);


        int pxYLogOutMdpi = -30;
        int actualPxYLogOutMdpi = Math.round(pxYLogOutMdpi * screenDensityMultiplier);
        AnimatorSet animatorSetLogOut = new AnimatorSet();
        Animator fadeInLogOutAnimator = AnimationsUtil.fadeAnimation(textLogOut, AnimationsUtil.FULLY_TRANSPARENT,
                AnimationsUtil.FULLY_VISIBLE, AnimationsUtil.ANIMATION_DURATION_150_MS);
        float translateYLogOutDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPxYLogOutMdpi);
        Animator translateYLogOutAnimator = AnimationsUtil.translateYAnimation(textLogOut, translateYLogOutDp, AnimationsUtil.ANIMATION_DURATION_150_MS);
        animatorSetLogOut.play(fadeInLogOutAnimator).after(AnimationsUtil.ANIMATION_DELAY_1000_MS)
                .with(translateYLogOutAnimator);


        animatorSetContacts.start();
        animatorSetPartners.start();
        animatorSetSettings.start();
        animatorSetEraseDatabase.start();
        animatorSetEraseTags.start();
        animatorSetLogOut.start();
    }


    private void fadeOutAndTranslateSideMenu() {
        AnimatorSet animatorSetContacts = new AnimatorSet();
        Animator fadeOutContactsAnimator = AnimationsUtil.fadeAnimation(textContacts, AnimationsUtil.FULLY_VISIBLE,
                AnimationsUtil.FULLY_TRANSPARENT, AnimationsUtil.ANIMATION_DURATION_400_MS);
        Animator translateYContactsAnimator = AnimationsUtil.translateYAnimation(textContacts, 0, AnimationsUtil.ANIMATION_DURATION_400_MS);
        animatorSetContacts.play(fadeOutContactsAnimator)
                .with(translateYContactsAnimator);


        AnimatorSet animatorSetPartners = new AnimatorSet();
        Animator fadeOutPartnersAnimator = AnimationsUtil.fadeAnimation(textPartners, AnimationsUtil.FULLY_VISIBLE,
                AnimationsUtil.FULLY_TRANSPARENT, AnimationsUtil.ANIMATION_DURATION_350_MS);
        Animator translateYPartnersAnimator = AnimationsUtil.translateYAnimation(textPartners, 0, AnimationsUtil.ANIMATION_DURATION_350_MS);
        animatorSetPartners.play(fadeOutPartnersAnimator)
                .with(translateYPartnersAnimator);


        AnimatorSet animatorSetSettings = new AnimatorSet();
        Animator fadeOutSettingsAnimator = AnimationsUtil.fadeAnimation(textSettings, AnimationsUtil.FULLY_VISIBLE,
                AnimationsUtil.FULLY_TRANSPARENT, AnimationsUtil.ANIMATION_DURATION_300_MS);
        Animator translateYSettingsAnimator = AnimationsUtil.translateYAnimation(textSettings, 0, AnimationsUtil.ANIMATION_DURATION_300_MS);
        animatorSetSettings.play(fadeOutSettingsAnimator)
                .with(translateYSettingsAnimator);


        AnimatorSet animatorSetEraseDatabase = new AnimatorSet();
        Animator fadeOutDatabaseAnimator = AnimationsUtil.fadeAnimation(textEraseDatabase, AnimationsUtil.FULLY_VISIBLE,
                AnimationsUtil.FULLY_TRANSPARENT, AnimationsUtil.ANIMATION_DURATION_250_MS);
        Animator translateYDatabaseAnimator = AnimationsUtil.translateYAnimation(textEraseDatabase, 0, AnimationsUtil.ANIMATION_DURATION_250_MS);
        animatorSetEraseDatabase.play(fadeOutDatabaseAnimator)
                .with(translateYDatabaseAnimator);


        AnimatorSet animatorSetEraseTags = new AnimatorSet();
        Animator fadeOutTagsAnimator = AnimationsUtil.fadeAnimation(textEraseTags, AnimationsUtil.FULLY_VISIBLE,
                AnimationsUtil.FULLY_TRANSPARENT, AnimationsUtil.ANIMATION_DURATION_200_MS);
        Animator translateYTagsAnimator = AnimationsUtil.translateYAnimation(textEraseTags, 0, AnimationsUtil.ANIMATION_DURATION_200_MS);
        animatorSetEraseTags.play(fadeOutTagsAnimator)
                .with(translateYTagsAnimator);


        AnimatorSet animatorSetLogOut = new AnimatorSet();
        Animator fadeOutLogOutAnimator = AnimationsUtil.fadeAnimation(textLogOut, AnimationsUtil.FULLY_VISIBLE,
                AnimationsUtil.FULLY_TRANSPARENT, AnimationsUtil.ANIMATION_DURATION_150_MS);
        Animator translateYLogOutAnimator = AnimationsUtil.translateYAnimation(textLogOut, 0, AnimationsUtil.ANIMATION_DURATION_150_MS);
        animatorSetLogOut.play(fadeOutLogOutAnimator)
                .with(translateYLogOutAnimator);


        animatorSetLogOut.start();
        animatorSetEraseTags.start();
        animatorSetEraseDatabase.start();
        animatorSetSettings.start();
        animatorSetPartners.start();
        animatorSetContacts.start();
    }


    private void fadeOutAndTranslateHeaderContainer() {
        AnimatorSet animatorSetHeaderContainer = new AnimatorSet();
        Animator fadeOutHeaderContainerAnimator = AnimationsUtil.fadeAnimation(headerContainer, AnimationsUtil.FULLY_VISIBLE,
                AnimationsUtil.FULLY_TRANSPARENT, AnimationsUtil.ANIMATION_DURATION_400_MS);
        Animator translateYHeaderContainerAnimator = AnimationsUtil.translateYAnimation(headerContainer, 0, AnimationsUtil.ANIMATION_DURATION_400_MS);
        animatorSetHeaderContainer.play(fadeOutHeaderContainerAnimator)
                .with(translateYHeaderContainerAnimator);

        animatorSetHeaderContainer.start();
    }


    private void fadeInAndTranslateHeaderContainer() {
        int pxYHeaderContainerMdpi = -55;
        int actualPxYHeaderContainerMdpi = Math.round(pxYHeaderContainerMdpi * screenDensityMultiplier);

        AnimatorSet animatorSetHeaderContainer = new AnimatorSet();
        Animator fadeInHeaderContainerAnimator = AnimationsUtil.fadeAnimation(headerContainer, AnimationsUtil.FULLY_TRANSPARENT,
                AnimationsUtil.FULLY_VISIBLE, AnimationsUtil.ANIMATION_DURATION_400_MS);
        float translateYHeaderContainerDp = AnimationsUtil.convertPxToDip(MainActivity.this, actualPxYHeaderContainerMdpi);
        Animator translateYHeaderContainerAnimator = AnimationsUtil.translateYAnimation(headerContainer, translateYHeaderContainerDp,
                AnimationsUtil.ANIMATION_DURATION_400_MS);
        animatorSetHeaderContainer.play(fadeInHeaderContainerAnimator)
                .with(translateYHeaderContainerAnimator);

        animatorSetHeaderContainer.start();
    }


}