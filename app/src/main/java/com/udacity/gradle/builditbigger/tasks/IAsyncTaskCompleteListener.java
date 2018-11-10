package com.udacity.gradle.builditbigger.tasks;

public interface IAsyncTaskCompleteListener<T>
{
  public void onTaskComplete(T result);
}
