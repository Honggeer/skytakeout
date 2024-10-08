package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common api")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;


    @PostMapping("/upload")
    @ApiOperation("File upload")
    public Result<String> upload(MultipartFile file){
        log.info("file upload: {}",file);
        try {
            String originalFileName = file.getOriginalFilename();
            String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
            String objName = UUID.randomUUID().toString()+extention;
            String filePath = aliOssUtil.upload(file.getBytes(),objName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("file upload failed: {}",e );
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
