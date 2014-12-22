/**
 * 
 */
package net.caiban.utils.upload.filter;

import net.caiban.utils.upload.UploadException;

/**
 * @author parox
 *
 */
public class JarUploadFilter extends AbstractUploadFilter{

	
	private enum include{
		JAR;
	}
	
	@Override
	public void filter(String originalFilename) throws UploadException {
		
		String suffix = getSuffix(originalFilename);
		
		try {
			include.valueOf(suffix.toUpperCase());
		} catch (Exception e) {
			throw new UploadException(suffix+" is not allowed.", e);
		}
		
	}

}
