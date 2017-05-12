package edu.pitt.cs.cs1635.studybuddies;

import com.stephentuso.welcome.*;
/**
 * Created by nick on 4/18/17.
 */

public class MyWelcomeActivity extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorAccent)
                .page(new TitlePage(R.drawable.logo,
                        "StudyBuddies")
                )
                .page(new BasicPage(R.drawable.studying,
                        "Interact with new classmates",
                        "")
                        .background(R.color.colorPrimary)
                )
                .page(new BasicPage(R.drawable.ic_favorite_black_24dp,
                        "Set your favorite classes",
                        "")
                )
                .page(new BasicPage(R.drawable.stupidquestion,
                        "Q&A System to share between groups",
                        "")
                        .background(R.color.colorPrimary)
                )
                .swipeToDismiss(true)
                .build();
    }
}
