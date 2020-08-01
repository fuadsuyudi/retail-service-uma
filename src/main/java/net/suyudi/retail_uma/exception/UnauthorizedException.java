package net.suyudi.retail_uma.exception;

import org.springframework.http.HttpStatus;

/* 
* 
indicates that the request has not been applied because 
it lacks valid authentication credentials for the target resource.
* 
*/
public class UnauthorizedException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
		this.setCode("401");
		this.setMessage("Unauthorized");
		this.setStatus(HttpStatus.UNAUTHORIZED);
	}

	public UnauthorizedException(String field) {
		this.setCode("401");
		this.setMessage(field + " Unauthorized");
		this.setStatus(HttpStatus.UNAUTHORIZED);
	}

	public UnauthorizedException(String message, String code) {
		this.setCode(code);
		this.setMessage(message);
		this.setStatus(HttpStatus.UNAUTHORIZED);
	}
}