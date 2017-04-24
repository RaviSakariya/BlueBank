package bluebankapp.swe443.bluebankappandroid;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.System.exit;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by bbui1 on 4/24/2017.
 */

@RunWith(AndroidJUnit4.class)
public class CreateAccountAndroidTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public void dummySetup() {
        ViewInteraction dummyUser = onView(withId(R.id.userNameInput))
                .perform(replaceText("dummy"), closeSoftKeyboard());

        ViewInteraction dummyPass = onView(withId(R.id.passwordInput))
                .perform(replaceText("Password1"), closeSoftKeyboard());

        ViewInteraction dummyIP = onView(withId(R.id.ipAddrInput))
                .perform(replaceText("1"), closeSoftKeyboard());
    }

    @Test
    public void createAccountTest(){
        //dummySetup();
        String nullString = null;
        // enter ip address in order to create account, click create account button
        ViewInteraction dummyIP = onView(withId(R.id.ipAddrInput))
                .perform(replaceText("1"), closeSoftKeyboard());
        ViewInteraction createAccountButtonClick = onView(
                allOf(withText("Create Account"), isDisplayed()));
        createAccountButtonClick.perform(click());

        // Get Views from CreateAccountActivity
        ViewInteraction nameBox = onView(withId(R.id.fullNameEdit));
        ViewInteraction ssnBox = onView(withId(R.id.ssnEdit));
        ViewInteraction dobBox = onView(withId(R.id.birthdayEdit));
        ViewInteraction emailBox = onView(withId(R.id.emailEdit));
        ViewInteraction usernameBox = onView(withId(R.id.usernameEdit));
        ViewInteraction passBox = onView(withId(R.id.passwordEdit));
        ViewInteraction initialBox = onView(withId(R.id.initialDepositEdit));

        // Attempt to create account without entering anything
        ViewInteraction createAccount2 = onView(
                allOf(withText("Create Account"), isDisplayed()));
        createAccount2.perform(click());
        // name error appears
        nameBox.check(matches(hasErrorText("Please enter name")));

        // Add text to Name, error should be gone
        // ssn error should appear
        nameBox.perform(typeText("Brandon"), closeSoftKeyboard());
        createAccount2.perform(click());
        ssnBox.check(matches(hasErrorText("Please enter ssn")));

        // Add text to ssn, error should be gone
        // dob error should appear
        // TODO ENFORCE THE SSN LEN TO 4 -- THIS PASSES
        ssnBox.perform(typeText("0"), closeSoftKeyboard());
        createAccount2.perform(click());
        dobBox.check(matches(hasErrorText("Please enter DOB")));

        // Add text to dob, error should be gone
        // dob error should appear
        // TODO ENFORCE THE DATE -- THIS PASSES
        dobBox.perform(typeText("0"), closeSoftKeyboard());
        createAccount2.perform(click());
        usernameBox.check(matches(hasErrorText("Please enter username")));

        // Add text to username, error should be gone
        // password error should appear
        usernameBox.perform(typeText("bbui"), closeSoftKeyboard());
        createAccount2.perform(click());
        passBox.check(matches(hasErrorText("Please enter password")));

        // Add text to password, new error appears
        // initial error should appear
        passBox.perform(typeText("1"), closeSoftKeyboard());
        createAccount2.perform(click());
        passBox.check(matches(hasErrorText("Password must be at least 8 characters")));

        // Add 2345678 to pass, error should disappear
        passBox.perform(typeText("2345678"), closeSoftKeyboard());
        createAccount2.perform(click());
        initialBox.check(matches(hasErrorText("Please enter initial amount")));

        initialBox.perform(typeText("0"), closeSoftKeyboard());
//        createAccount2.perform(click());
//        ViewInteraction toastCheck = onView(
//                withText("Create request sent.")).
//                check(matches(isDisplayed()));
    }

}



