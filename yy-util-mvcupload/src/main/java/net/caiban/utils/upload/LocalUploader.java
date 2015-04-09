/**
 * 
 */
package net.caiban.utils.upload;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author parox
 *
 */
public class LocalUploader extends AbstractUploader{
//	/**
//	 * Default file input name
//	 */
//	final static String DEFAULT_INPUT="uploadfile";

	public LocalUploader(String inputName) {
		super(inputName);
	}

	@Override
	protected UploadResult putObject(MultipartFile file, String path,
			String originalName, String resultName) throws IOException,
			UploadException {
		
		File upfile = new File(path + resultName);
		try {
			file.transferTo(upfile);
			UploadResult result = new UploadResult(path, originalName, resultName);
			return result;
		} catch (IllegalStateException e) {
			throw new UploadException("Error upload file, uploaded path is "+path+originalName+" Error message: "+e.getMessage(), e);
		} catch (IOException e) {
			throw new UploadException("Error upload file, uploaded path is "+path+originalName+" Error message: "+e.getMessage(), e);
		}
	}
}
