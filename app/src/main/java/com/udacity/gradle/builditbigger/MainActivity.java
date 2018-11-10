package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.jokes_ui.JokeActivity;
import com.udacity.gradle.builditbigger.idling.SimpleIdlingResource;
import com.udacity.gradle.builditbigger.tasks.IAsyncTaskCompleteListener;
import com.udacity.gradle.builditbigger.tasks.JokeAsyncTask;

public class MainActivity extends AppCompatActivity
{
  @Nullable
  private SimpleIdlingResource idlingResource;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if(id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void tellJoke(View view)
  {
    if(idlingResource != null)
    {
      // we are not idle
      idlingResource.setIdleState(false);
    }

    new JokeAsyncTask(this, new JokeAsyncTaskCompleteListener()).execute();
  }

  @VisibleForTesting
  @NonNull
  public IdlingResource getIdlingResource()
  {
    if(idlingResource == null)
    {
      idlingResource = new SimpleIdlingResource();
    }
    return idlingResource;
  }

  class JokeAsyncTaskCompleteListener implements IAsyncTaskCompleteListener<JokeAsyncTask.Result>
  {
    @Override
    public void onTaskComplete(JokeAsyncTask.Result result)
    {
      if(result != null)
      {
        // we are idle
        if(idlingResource != null)
        {
          idlingResource.setIdleState(true);
        }

        String joke = result.joke;

        Intent intent = new Intent(MainActivity.this, JokeActivity.class);

        intent.putExtra(getString(R.string.joke_text_view_string_key), joke);

        startActivity(intent);
      }
    }
  }
}
