package com.udacity.gradle.builditbigger.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class JokeAsyncTask extends AsyncTask<Void, Void, JokeAsyncTask.Result>
{
  private static MyApi myApi = null;

  private final WeakReference<Context> context;
  private IAsyncTaskCompleteListener<Result> listener;

  public class Result
  {
    public String joke;

    Result(String joke)
    {
      this.joke = joke;
    }
  }

  public JokeAsyncTask(Context context, IAsyncTaskCompleteListener<Result> listener)
  {
    this.context = new WeakReference<>(context);
    this.listener = listener;
  }

  @Override
  protected Result doInBackground(Void... voids)
  {
    Context weakContext = context.get();

    if(weakContext != null)
    {
      if(myApi == null)
      {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl(weakContext.getString(R.string.google_cloud_root_url))
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                  @Override
                  public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException
                  {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                  }
                });

        myApi = builder.build();
      }

      try
      {
        return new Result(myApi.getJoke().execute().getData());
      }
      catch(IOException e)
      {
        e.printStackTrace();
        return null;
      }

    }
    else
    {
      // invalid context
      return null;
    }
  }

  @Override
  protected void onPostExecute(Result result)
  {
    super.onPostExecute(result);
    listener.onTaskComplete(result);
  }
}
