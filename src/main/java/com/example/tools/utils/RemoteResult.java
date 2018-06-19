package com.example.tools.utils;

import java.io.Serializable;

/**
 * <pre>
 * 远程接口值对象，此对象使用说明
 * 		使用时，判断isSuccess返回值，true表示业务成功、false表示接口调用失败
 * 		errorCode，用于判断失败原因(非系统错误)，系统预设错误码，用负数表示：-1表示参数不合法，用户自定义错误码使用正数表示，0表示无错误
 * </pre>
 * @author	zhanglikun
 * @date	2015年10月22日 下午1:53:46
 * @param <T>
 */
public class RemoteResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isSuccess = true ;	// 接口调用是否成功(业务)，系统错误、业务失败都将返回false
	private int errorCode ;				// 错误状态码，发生可处理错误时，将错误转换为错误码
	private String errorMsg ="ok";				// 自定义错误信息，发生可处理错误时，返回自定义信息
	private T result ;					// 接口返回结果(Void表示无返回值)
	private Exception exceptionStack ;	// 异常堆栈信息，需要提供调试功能时，将异常加入此堆栈中，便于协调调用方调试，仅作调试用

	public RemoteResult() {
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public Exception getExceptionStack() {
		return exceptionStack;
	}

	/**
	 * 设置异常堆栈信息时，success状态将被置为false
	 * @param exceptionStack
	 */
	public void setExceptionStack(Exception exceptionStack) {
		this.isSuccess = false ;
		this.exceptionStack = exceptionStack;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String toString() {
		return "RemoteResult{" +
				"isSuccess=" + isSuccess() +
				", errorCode=" + getErrorCode() +
				", errorMsg='" + getErrorMsg() + '\'' +
				", result=" + getResult() +
				", exceptionStack=" + getExceptionStack() +
				'}';
	}

	public void setErrorMsg(String errorMsg) {


		this.errorMsg = errorMsg;
	}
}
