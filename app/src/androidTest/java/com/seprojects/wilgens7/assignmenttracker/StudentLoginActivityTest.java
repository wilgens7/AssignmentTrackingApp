package com.seprojects.wilgens7.assignmenttracker;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class StudentLoginActivityTest {

    @Rule
    public ActivityTestRule<StudentLoginActivity> rule  = new  ActivityTestRule<>(StudentLoginActivity.class);

    private StudentLoginActivity activity;

    private EditText username;
    private EditText password;

//    Instrumentation.ActivityMonitor monitor = getInstrumentation()
//            .addMonitor(StudentRegistrationActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        activity = rule.getActivity();

        username = activity.studentNumber;
        password = activity.studentPassword;
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }

    @Test //test case 1
    public void allNecessaryObjectsInstanciatedTest(){

        assertNotNull(withId(R.id.login_student_password_edittext));
        assertNotNull(withId(R.id.login_student_username_editext));
        assertNotNull(withId(R.id.student_login_button));
        assertNotNull(withId(R.id.login_link_to_register_edittext));
        assertNotNull(withId(R.id.login_page_title));
    }

    @Test //test case 2
    public void emptyUsernameAndPasswordTest(){

        onView(withId(R.id.login_student_username_editext)).perform(typeText("\n"));
        onView(withId(R.id.login_student_password_edittext)).perform(typeText("\n"));
        onView(withId(R.id.student_login_button)).perform(click());

        assertEquals("Check Missing Input Value", activity.loginResultMessage);
    }

    @Test //test case 3
    public void incorrectLoginInfoTest(){
        onView(withId(R.id.login_student_username_editext)).perform(typeText("alala\n"));
        onView(withId(R.id.login_student_password_edittext)).perform(typeText("5421\n"));
        onView(withId(R.id.student_login_button)).perform(click());

        assertEquals("Login failed...", activity.loginResultMessage);
    }

    @Test //test case 4
    public void correctLoginInfoTest(){
        onView(withId(R.id.login_student_username_editext)).perform(typeText("1234\n"));
        onView(withId(R.id.login_student_password_edittext)).perform(typeText("231\n"));
        onView(withId(R.id.student_login_button)).perform(click());

        assertNotEquals("Login failed...", activity.loginResultMessage);
        assertNotEquals("Check Missing Input Value", activity.loginResultMessage);
        assertNotEquals("", activity.loginResultMessage);
    }
}