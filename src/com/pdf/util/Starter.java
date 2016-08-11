package com.pdf.util;

public interface Starter {

	void start();
	
	void onSuccess(String message);
	
	void onFail(String message);
}
