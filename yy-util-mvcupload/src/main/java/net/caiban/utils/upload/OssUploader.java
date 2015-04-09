/**
 * 
 */
package net.caiban.utils.upload;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * @author parox
 *
 */
public class OssUploader extends AbstractUploader{
	
	private OSSClient ossClient;
	private String bucket;
	
	public OssUploader(String inputName) {
		super(inputName);
	}

	public OssUploader (OSSClient ossClient, String bucket){
		super(null);
		
		this.ossClient = ossClient;
		this.bucket = bucket;
	}
	
	public OssUploader (OSSClient ossClient, String bucket, String inputName){
		super(inputName);
		
		this.ossClient = ossClient;
		this.bucket = bucket;
	}

	@Override
	protected UploadResult putObject(MultipartFile file, String path,
			String originalName, String resultName) throws IOException,
			UploadException {
		
		if(ossClient==null){
			throw new UploadException("OSS Client is null.");
		}
		
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentType(file.getContentType());
		
		InputStream is = file.getInputStream();
		objectMeta.setContentLength(is.available());
		
		UploadResult result = new UploadResult(path, originalName, resultName);
		ossClient.putObject(bucket, result.getFullPath(), is, objectMeta);
		//PutObjectResult ossResult = 
		return result;
	}
	
}
