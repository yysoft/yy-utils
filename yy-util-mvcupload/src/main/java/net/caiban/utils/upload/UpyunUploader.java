/**
 * 
 */
package net.caiban.utils.upload;

import java.io.IOException;

import main.java.com.UpYun;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author parox
 *
 */
public class UpyunUploader extends AbstractUploader{
	
	private UpYun upYun;

	public UpyunUploader(String inputName) {
		super(inputName);
	}
	
	public UpyunUploader(UpYun upYun){
		super(null);
		this.upYun = upYun;
	}
	
	public UpyunUploader(UpYun upYun, String inputName){
		super(inputName);
		this.upYun = upYun;
	}

	@Override
	protected UploadResult putObject(MultipartFile file, String path,
			String originalName, String resultName) throws IOException,
			UploadException {
		
		if(upYun==null){
			throw new UploadException("UpYun Client is null.");
		}
		
		UploadResult result = new UploadResult(path, originalName, resultName);
		if(upYun.writeFile(result.getFullPath(), file.getBytes(), true)){
			return result;
		}
		
		return null;
	}

}
