/**
 * 
 */
package net.caiban.utils.upload;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.caiban.utils.upload.filter.AbstractUploadFilter;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * @author parox
 *
 */
public abstract class AbstractUploader {
	
	private String inputName;
	
	final static String DEFAULT_INPUT="uploadfile";
	
	public AbstractUploader(String inputName){
		this.inputName = inputName;
	}
	
	protected abstract UploadResult putObject(MultipartFile file, String path,
			String originalName, String resultName) throws IOException,
			UploadException;

	public UploadResult upload(MultipartFile file, String path,
			String rename, AbstractUploadFilter filter) throws UploadException, IOException {
		
		if(file==null){
			throw new UploadException("MultipartFile unavailable.");
		}
		
		String originalName = file.getOriginalFilename();

		if (Strings.isNullOrEmpty(originalName)) {
			throw new UploadException("Empty name of uploaded file.");
		}
		
		if(filter!=null){
			filter.filter(originalName);
		}
		
		String resultName = originalName;
		if (!Strings.isNullOrEmpty(rename)) {
			int start = originalName.lastIndexOf(".");
			start = start == -1?0:start;
			String suffix = originalName.substring(start, originalName.length());
			suffix = suffix.startsWith(".")?suffix:"."+suffix;
			resultName = rename + suffix;
		}

		if (!path.endsWith("/")) {
			path = path + "/";
		}
		
		return putObject(file, path, originalName, resultName);
	}
	
	public Map<String, UploadResult> batchUpload(
			HttpServletRequest request, String path, String rename,
			AbstractUploadFilter filter){
		
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		
		Map<String, UploadResult> resultMap = Maps.newHashMap();
		int i=0;
		for(String inputFileName: fileMap.keySet()){
			try {
				String renamePlus = null;
				if(!Strings.isNullOrEmpty(rename)){
					renamePlus = rename+"-"+String.valueOf(i+1);
					i++;
				}
				UploadResult result = upload(fileMap.get(inputFileName), path, renamePlus, filter);
				resultMap.put(inputFileName, result);
			} catch (IOException e) {
				UploadResult result = new UploadResult();
				result.setError(e.getMessage());
				resultMap.put(inputFileName, result);
			} catch (UploadException e) {
				UploadResult result = new UploadResult();
				result.setError(e.getMessage());
				resultMap.put(inputFileName, result);
			}
		}
		
		return resultMap;
	}
	
	public MultipartFile getMultipartFile(HttpServletRequest request, String fileInputName){
		MultipartRequest multipartRequest = (MultipartRequest) request;
		if(Strings.isNullOrEmpty(fileInputName)){
			Map<String, MultipartFile> map = multipartRequest.getFileMap();
			for(String k: map.keySet()){
				return map.get(k);
			}
			return null;
		}else{
			MultipartFile file = multipartRequest.getFile(fileInputName);
			return file;
		}
	}
	
	public MultipartFile getMultipartFile(HttpServletRequest request){
//		if(Strings.isNullOrEmpty(inputName)){
//			return getMultipartFile(request, DEFAULT_INPUT);
//		}
		return getMultipartFile(request, inputName);
	}
	
	public UploadResult upload(HttpServletRequest request,
			String path) throws IOException, UploadException {
		return upload(getMultipartFile(request), path, null, null);
	}
	
	public UploadResult upload(HttpServletRequest request,
			String path, AbstractUploadFilter filter) throws IOException,
			UploadException {
		return upload(getMultipartFile(request), path, null, filter);
	}
	
	public Map<String, UploadResult> batchUpload(
			HttpServletRequest request, String path){
		return batchUpload(request, path, null, null);
	}
	
	public Map<String, UploadResult> batchUpload(
			HttpServletRequest request, String path, String rename){
		return batchUpload(request, path, rename, null);
	}
	
	public Map<String, UploadResult> batchUpload(
			HttpServletRequest request, String path,
			AbstractUploadFilter filter){
		return batchUpload(request, path, null,filter);
	}
}
