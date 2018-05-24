package com.waben.stock.applayer.operation.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.waben.stock.applayer.operation.business.UploadBusiness;

@RestController
@RequestMapping("/file")
public class UploadController {

    @Autowired
    private UploadBusiness uploadBusiness;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file){
        String resultPath = "";
        try {
           resultPath =  uploadBusiness.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
        return resultPath;
    }

}
