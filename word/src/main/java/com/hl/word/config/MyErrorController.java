package com.hl.word.config;

import com.hl.word.domain.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常响应
 *
 * @author ws
 * @date 2018/12/24 12:12
 */
@ControllerAdvice
public class MyErrorController {

	/**
	 * 系统异常处理，比如：404,500
	 * @param
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result defaultErrorHandler(Exception e) {
		e.printStackTrace();
		if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
			return Result.failResult("找不到！");
		} else {
			return Result.failResult("系统异常");
		}
	}

//	/**
//	 * 业务异常处理
//	 * @param req
//	 * @param e
//	 * @return
//	 * @throws Exception
//	 */
//	@ExceptionHandler(value = ErrorReportException.class)
//	@ResponseBody
//	public Result errorReportHandler(ErrorReportException e) {
//		e.printStackTrace();
//		String errorCode = e.getErrorInfo().getErrorCode();
//		String message = PropertiesUtils.getMessage(errorCode, errorCode);
//		return Result.failResult(message);
//	}
//
//	/**
//	 * 业务异常处理
//	 * @param req
//	 * @param e
//	 * @return
//	 * @throws Exception
//	 */
//	@ExceptionHandler(value = ApiErrorException.class)
//	@ResponseBody
//	public Result apiHandler(ApiErrorException e) {
//		e.printStackTrace();
//		String message = e.getMessage();
//		return Result.failResult(message);
//	}

}
