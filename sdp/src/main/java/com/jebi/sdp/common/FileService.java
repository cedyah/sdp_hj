package com.jebi.sdp.common;

import java.io.File;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jebi.sdp.model.FileVO;

/**
 * 첨부파일 저장/삭제 및 업로드 경로·파일명 처리
 */
@Component
public class FileService {

    /**
     * 첨부파일 서버에 저장
     * @param MultipartFile 저장될 파일, FileVO 저장된 파일 정보가 담겨진 object
     * @return boolean
     */
	public boolean uploadFile(MultipartFile multipartFile, FileVO fileVO) throws Exception{
		boolean result = false;
		
        File file = new File(fileVO.getFile_path());
        if(file.exists() == false){
            file.mkdirs();
        }

        if(multipartFile.isEmpty() == false){
        	file = new File(fileVO.getFile_path() + fileVO.getFile_nm());
        	multipartFile.transferTo(file);
        	result = true;
        }

        return result;
    }
	
	
	/**
	 * 첨부파일 서버에서 삭제
	 * @param FileVO 저장된 파일 정보가 담겨진 object
	 * @return void
	 */
	public void deleteFile(FileVO fileVO) throws Exception{
		String path = fileVO.getFile_path() + fileVO.getFile_nm(); // 삭제할 파일의 경로
		
		File file = new File(path);
		if(file.exists() == true){
			file.delete();
		}
	}

	public static boolean makeDir(String uploadDir) {
		boolean result = false;

		String[] uploadDirArr = uploadDir.split("/");
		String path = "";

		try {
			for (int i = 0; i < uploadDirArr.length; i++) {
				path += (uploadDirArr[i] + "/");

				File tmp = new File(path);

				if (!tmp.exists()) {
					// tmp.mkdir();
					tmp.mkdirs();
					// tmp.setWritable( true, true);
				}
			}

			result = true;

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	public static String getFileAlias(String filename) {
		String origFname = filename;
		String saveFname = "";
		try {
			Thread.sleep(1);// 딜레이
			if (origFname != null) {
				int ext_pos = origFname.lastIndexOf(".");
				String ext = origFname.substring(ext_pos);

				Date time = new Date();
				saveFname = time.getTime() + ext;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saveFname;
	}
}
