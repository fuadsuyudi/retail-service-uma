package net.suyudi.retail_uma.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException { 

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BaseException() {
		super();
	}

	private String code = "500";

	private String message = "";

	private HttpStatus status = HttpStatus.EXPECTATION_FAILED;

	private Object data = null;

}