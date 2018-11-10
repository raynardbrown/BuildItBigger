package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.ComponentNameMatchers;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.jokes_ui.JokeActivity;

import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest
{
  @Rule
  public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

  private IdlingResource idlingResource;

  @Before
  public void registerIdlingResource()
  {
    idlingResource = intentsTestRule.getActivity().getIdlingResource();

    IdlingRegistry.getInstance().register(idlingResource);
  }

  @After
  public void unregisterIdlingResource()
  {
    if(idlingResource != null)
    {
      IdlingRegistry.getInstance().unregister(idlingResource);
    }
  }

  @Test
  public void testJokeActivityGetsNonEmptyString()
  {
    Espresso.onView(ViewMatchers.withId(R.id.button_tell_joke))
            .perform(ViewActions.click());

    Intents.intended(IntentMatchers.hasComponent(ComponentNameMatchers.hasClassName(JokeActivity.class.getName())));

    Context context = InstrumentationRegistry.getTargetContext();

    Intents.intended(AllOf.allOf(
            IntentMatchers.toPackage(context.getPackageName()),
            IntentMatchers.hasExtra(context.getString(R.string.joke_text_view_string_key), context.getString(R.string.sample_joke))));
  }
}
