package com.example.android.jokes_ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.jokes_ui.databinding.ActivityJokeBinding;

public class JokeActivity extends AppCompatActivity
{
  private ActivityJokeBinding activityJokeBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    activityJokeBinding = DataBindingUtil.setContentView(this, R.layout.activity_joke);

    if(savedInstanceState == null)
    {
      Intent intent = getIntent();

      activityJokeBinding.tvJoke.setText(intent.getStringExtra(getString(R.string.joke_text_view_string_key)));
    }
    else
    {
      activityJokeBinding.tvJoke.setText(savedInstanceState.getString(getString(R.string.joke_text_view_string_key)));
    }

    if(getSupportActionBar() != null)
    {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
    super.onSaveInstanceState(outState);

    outState.putString(getString(R.string.joke_text_view_string_key), activityJokeBinding.tvJoke.getText().toString());
  }
}
