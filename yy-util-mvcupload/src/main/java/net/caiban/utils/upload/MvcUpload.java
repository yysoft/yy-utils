/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-17
 */
package net.caiban.utils.upload;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.caiban.utils.upload.filter.AbstractUploadFilter;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * @author mays (mays@caiban.net)
 * 
 *         created on 2014-12-22
 */
public class MvcUpload {
	
	/**
	 * Default file input name
	 */
	final static String DEFAULT_INPUT="uploadfile";
	
	public static UploadResult localUpload(HttpServletRequest request,
			String path) throws IOException, UploadException {
		return localUpload(getMultipartFile(request), path, null, null);
	}
	
	public static UploadResult localUpload(HttpServletRequest request,
			String path, AbstractUploadFilter filter) throws IOException,
			UploadException {
		return localUpload(getMultipartFile(request), path, null, filter);
	}
	
	public static Map<String, UploadResult> batchLocalUpload(
			HttpServletRequest request, String path){
		return batchLocalUpload(request, path, null, null);
	}
	
	public static Map<String, UploadResult> batchLocalUpload(
			HttpServletRequest request, String path,
			AbstractUploadFilter filter){
		return batchLocalUpload(request, path, null,filter);
	}
	
	public static Map<String, UploadResult> batchLocalUpload(
			HttpServletRequest request, String path, String rename,
			AbstractUploadFilter filter){
		
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		
		Map<String, UploadResult> resultMap = Maps.newHashMap();
		for(String inputFileName: fileMap.keySet()){
			try {
				UploadResult result = localUpload(fileMap.get(inputFileName), path, rename, filter);
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
	
	public static MultipartFile getMultipartFile(HttpServletRequest request, String fileInputName){
		MultipartRequest multipartRequest = (MultipartRequest) request;
		MultipartFile file = multipartRequest.getFile(fileInputName);
		return file;
	}
	
	public static MultipartFile getMultipartFile(HttpServletRequest request){
		return getMultipartFile(request, DEFAULT_INPUT);
	}

	public static UploadResult localUpload(MultipartFile file, String path,
			String rename, AbstractUploadFilter filter) throws IOException, UploadException {
		
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
				resultName = rename + "." + originalName.substring(start, originalName.length());
			}

			if (!path.endsWith("/")) {
				path = path + "/";
			}

			File upfile = new File(path + resultName);

			try {
				file.transferTo(upfile);
				UploadResult result = new UploadResult(path, originalName, resultName);
				return result;
			} catch (IllegalStateException e) {
				throw new RuntimeException("Error upload file, uploaded path is "+path+originalName, e);
			} catch (IOException e) {
				throw new UploadException("Error upload file, uploaded path is "+path+originalName, e);
			}
	}
	
}
