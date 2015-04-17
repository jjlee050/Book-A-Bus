package com.example.book_a_bus.tasks;

import android.os.AsyncTask;

import com.example.book_a_bus.listeners.TaskListener;

abstract class BaseTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	private TaskListener mCallback;
	
	public void setTaskListener(TaskListener listener) {
		this.mCallback = listener;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		if(mCallback != null){
			mCallback.onPreExecute(this.getClass());
		}
	}

	@Override
	protected void onProgressUpdate(Progress... values) {
		super.onProgressUpdate(values);
		
		if(mCallback != null){
			mCallback.onProgressUpdate(getClass(), values);
		}
	}
	
	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		
		if(mCallback != null){
			mCallback.onPostExecute(getClass(), result);
		}
	}

}