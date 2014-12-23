/**
 * 
 */
package net.caiban.utils.upload;

import java.io.Serializable;

import com.google.common.base.Strings;

/**
 * @author parox
 *
 */
public class UploadResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String original;
	private String rename; //also is result name
	private String path;
	
	private String error;
	
	public UploadResult(){
		
	}
	
	public UploadResult(String path, String original, String rename){
		this.original = original;
		this.path = path;
		this.rename = rename;
	}
	
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getRename() {
		return rename;
	}
	public void setRename(String rename) {
		this.rename = rename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFullPath() {
		if(!Strings.isNullOrEmpty(rename)){
			return path+rename;
		}
		
		if(!Strings.isNullOrEmpty(original)){
			return path+original;
		}
		return null;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
