/**
 * 
 */
package net.caiban.utils.upload.filter;

import com.google.common.base.Strings;

import net.caiban.utils.upload.UploadException;

/**
 * @author parox
 *
 */
public abstract class AbstractUploadFilter {

	abstract public void filter(String originalFilename) throws UploadException;
	
	String getSuffix(String originalFilename) throws UploadException{
		if(Strings.isNullOrEmpty(originalFilename)){
			throw new UploadException("Empty origin file name.");
		}
		
		int start = originalFilename.lastIndexOf(".");
		
		if(start == -1){
			throw new UploadException("Can not identify suffix.");
		}
		
		String suffix = originalFilename.substring(start+1, originalFilename.length());
		
		if(Strings.isNullOrEmpty(suffix)){
			throw new UploadException("Can not identify suffix.");
		}
		
		return suffix;
	}
	
}
