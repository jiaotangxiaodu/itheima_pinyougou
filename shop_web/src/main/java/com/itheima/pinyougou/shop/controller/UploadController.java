package com.itheima.pinyougou.shop.controller;

import com.itheima.pinyougou.entity.Result;
import com.itheima.pinyougou.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
public class UploadController {

    @Autowired
    private FastDFSClient fastDFSClient;


    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("upload")
    public Result getCategroyOptionsByParentId(MultipartFile file){
        String extName = null;

        try {
            String originalFilename = file.getOriginalFilename();
            extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            String result = fastDFSClient.uploadFile(file.getBytes(), extName);
            result = "http://"+FILE_SERVER_URL +"/"+ result;
            return new Result(true,result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }

}
