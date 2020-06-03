package krunal.com.example.cameraapp.ui.login;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import krunal.com.example.cameraapp.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>( LoginActivity.class );

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.WRITE_EXTERNAL_STORAGE" );

    @Test
    public void loginActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf( withId( R.id.username ),
                        childAtPosition(
                                allOf( withId( R.id.container ),
                                        childAtPosition(
                                                withId( android.R.id.content ),
                                                0 ) ),
                                0 ),
                        isDisplayed() ) );
        appCompatEditText.perform( replaceText( "123456" ), closeSoftKeyboard() );

        ViewInteraction appCompatEditText2 = onView(
                allOf( withId( R.id.password ),
                        childAtPosition(
                                allOf( withId( R.id.container ),
                                        childAtPosition(
                                                withId( android.R.id.content ),
                                                0 ) ),
                                1 ),
                        isDisplayed() ) );
        appCompatEditText2.perform( replaceText( "123456" ), closeSoftKeyboard() );

        ViewInteraction appCompatButton = onView(
                allOf( withId( R.id.login ), withText( "Sign in" ),
                        childAtPosition(
                                allOf( withId( R.id.container ),
                                        childAtPosition(
                                                withId( android.R.id.content ),
                                                0 ) ),
                                2 ),
                        isDisplayed() ) );
        appCompatButton.perform( click() );

        ViewInteraction appCompatEditText3 = onView(
                allOf( withId( R.id.editText2 ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.nav_host_fragment ),
                                        0 ),
                                0 ),
                        isDisplayed() ) );
        appCompatEditText3.perform( replaceText( "12345A" ), closeSoftKeyboard() );

        ViewInteraction appCompatButton2 = onView(
                allOf( withId( R.id.startCameraSearch ), withText( "Search" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.nav_host_fragment ),
                                        0 ),
                                1 ),
                        isDisplayed() ) );
        appCompatButton2.perform( click() );

        ViewInteraction appCompatImageButton = onView(
                allOf( withContentDescription( "Open navigation drawer" ),
                        childAtPosition(
                                allOf( withId( R.id.toolbar ),
                                        childAtPosition(
                                                withClassName( is( "android.support.design.widget.AppBarLayout" ) ),
                                                0 ) ),
                                1 ),
                        isDisplayed() ) );
        appCompatImageButton.perform( click() );

        ViewInteraction navigationMenuItemView = onView(
                allOf( childAtPosition(
                        allOf( withId( R.id.design_navigation_view ),
                                childAtPosition(
                                        withId( R.id.nav_view ),
                                        0 ) ),
                        2 ),
                        isDisplayed() ) );
        navigationMenuItemView.perform( click() );

        ViewInteraction appCompatImageButton2 = onView(
                allOf( withContentDescription( "Open navigation drawer" ),
                        childAtPosition(
                                allOf( withId( R.id.toolbar ),
                                        childAtPosition(
                                                withClassName( is( "android.support.design.widget.AppBarLayout" ) ),
                                                0 ) ),
                                1 ),
                        isDisplayed() ) );
        appCompatImageButton2.perform( click() );

        ViewInteraction navigationMenuItemView2 = onView(
                allOf( childAtPosition(
                        allOf( withId( R.id.design_navigation_view ),
                                childAtPosition(
                                        withId( R.id.nav_view ),
                                        0 ) ),
                        3 ),
                        isDisplayed() ) );
        navigationMenuItemView2.perform( click() );

        ViewInteraction appCompatImageButton3 = onView(
                allOf( withContentDescription( "Open navigation drawer" ),
                        childAtPosition(
                                allOf( withId( R.id.toolbar ),
                                        childAtPosition(
                                                withClassName( is( "android.support.design.widget.AppBarLayout" ) ),
                                                0 ) ),
                                1 ),
                        isDisplayed() ) );
        appCompatImageButton3.perform( click() );

        openActionBarOverflowOrOptionsMenu( getInstrumentation().getTargetContext() );

        ViewInteraction appCompatImageButton4 = onView(
                allOf( withContentDescription( "Open navigation drawer" ),
                        childAtPosition(
                                allOf( withId( R.id.toolbar ),
                                        childAtPosition(
                                                withClassName( is( "android.support.design.widget.AppBarLayout" ) ),
                                                0 ) ),
                                1 ),
                        isDisplayed() ) );
        appCompatImageButton4.perform( click() );

        ViewInteraction navigationMenuItemView3 = onView(
                allOf( childAtPosition(
                        allOf( withId( R.id.design_navigation_view ),
                                childAtPosition(
                                        withId( R.id.nav_view ),
                                        0 ) ),
                        2 ),
                        isDisplayed() ) );
        navigationMenuItemView3.perform( click() );

        ViewInteraction appCompatButton3 = onView(
                allOf( withId( R.id.startCamera ), withText( "Scan" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.nav_host_fragment ),
                                        0 ),
                                1 ),
                        isDisplayed() ) );
        appCompatButton3.perform( click() );

        ViewInteraction appCompatButton4 = onView(
                allOf( withId( R.id.startCamera ), withText( "Scan" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( android.R.id.content ),
                                        0 ),
                                1 ),
                        isDisplayed() ) );
        appCompatButton4.perform( click() );

        ViewInteraction floatingActionButton = onView(
                allOf( withId( R.id.Save ),
                        childAtPosition(
                                childAtPosition(
                                        withId( android.R.id.content ),
                                        0 ),
                                4 ),
                        isDisplayed() ) );
        floatingActionButton.perform( click() );

        ViewInteraction floatingActionButton2 = onView(
                allOf( withId( R.id.clear ),
                        childAtPosition(
                                childAtPosition(
                                        withId( android.R.id.content ),
                                        0 ),
                                3 ),
                        isDisplayed() ) );
        floatingActionButton2.perform( click() );
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText( "Child at position " + position + " in parent " );
                parentMatcher.describeTo( description );
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches( parent )
                        && view.equals( ((ViewGroup) parent).getChildAt( position ) );
            }
        };
    }
}
