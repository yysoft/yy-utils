/**
 * 
 */
package net.caiban.utils.upload;

/**
 * @author parox
 *
 */
public class UploadException extends Exception {

	private static final long serialVersionUID = 1L;

	public UploadException(){
		super();
	}
	
	public UploadException(String msg){
		super(msg);
	}
	
	public UploadException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
