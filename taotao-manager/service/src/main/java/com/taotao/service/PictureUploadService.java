package com.taotao.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

public interface PictureUploadService {
    public Map uploadPicture(MultipartFile file, String realPath);
}
