package com.taotao.service.impl;
/**
 * 上传图片service
 */

import com.taotao.service.PictureUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class PictureUploadServiceImpl implements PictureUploadService {
    @Value("${BASE_URL}")
    String BASE_URL;
   @Override
    public Map uploadPicture(MultipartFile file,String realPath) {
        Map map = new HashMap();
        if (file.isEmpty()) {
            map.put("error",1);
            map.put("message","存入图片为空");
            return map;
        }
        UUID uuid = UUID.randomUUID();
        String newName = uuid.toString();
        String oldName = file.getOriginalFilename();
       System.out.println("old="+oldName);
        String name = newName+oldName.substring(oldName.lastIndexOf("."));
       System.out.println(file.toString());
       realPath = realPath+"\\"+name;
       File dest = new File(realPath);
       System.out.println("BASE_URL="+BASE_URL);
       System.out.println("realPath"+realPath);
       System.out.println("url="+BASE_URL+"image/"+name);
        try {
            file.transferTo(dest); // 保存文件
            map.put("error",0);
            map.put("url",BASE_URL+"/image/"+name);
            return map;
        } catch (Exception e) {
            System.out.println("文件上传失败");
            map.put("error",1);
            map.put("message","文件上传失败");
            return map;
        }
    }
}
