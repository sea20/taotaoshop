package com.taotao.controller;



import com.taotao.service.PictureUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片储存
 */
@Controller
public class PictureController {
    @Autowired
    PictureUploadService service;
    @Autowired
   HttpServletRequest request;
    @ResponseBody
    @RequestMapping("/pic/upload")
    public Map pictureUpload(MultipartFile uploadFile){
        //System.out.println(uploadFile);
        String realPath = request.getSession().getServletContext().getRealPath("image");
        //System.out.println(realPath);
        Map map = service.uploadPicture(uploadFile, realPath);
        return map;
    }
}
