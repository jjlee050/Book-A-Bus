package com.example.book_a_bus.listeners;

public interface TaskListener {
	public abstract void onPreExecute(Class<?> cname);
	public abstract void onProgressUpdate(Class<?> cname, Object... values);
	public abstract void onPostExecute(Class<?> cname, Object result);
}