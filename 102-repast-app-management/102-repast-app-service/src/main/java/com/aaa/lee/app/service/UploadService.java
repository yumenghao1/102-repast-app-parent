package com.aaa.lee.app.service;


import com.aaa.lee.app.model.OrderReturnApply;
import com.aaa.lee.app.properties.FtpProperties;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.utils.DateUtil;
import com.aaa.lee.app.utils.FileNameUtil;
import com.aaa.lee.app.utils.FtpUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * ftp Service层
 */
@Service
@Transactional
public class UploadService {
    @Autowired
    private FtpProperties properties;

    //    @Autowired
//    private PictureMapper pictureMapper;
//
    public Map<String, Object> upload(MultipartFile[] files, Long orderId) {
        Map<String, Object> map = new HashMap<>();
        String newNames = "";
        boolean success = false;
        try {
            for (MultipartFile file : files) {
                if ("".equals(file.getOriginalFilename())) {
                    continue;
                }
                String oldName = file.getOriginalFilename();
                String newName = FileNameUtil.getFileName(orderId);
                newName += oldName.substring(oldName.lastIndexOf("."));
                String filePath = DateUtil.formatDate(new Date(), DateUtil.DATE_TYPE);
                newNames += newNames + properties.getHttpPath() + filePath + "/" + newName;
                boolean b = FtpUtil.uploadFile(properties.getIp(), Integer.valueOf(properties.getPort()), properties.getUsername(), properties.getPassword(), properties.getBasePath(), filePath, newName, file.getInputStream());
                if (b) {
                    newNames += ",";
                    success = b;
                } else {
                    map.put("code", LoginStatus.LOGIN_FAILED.getCode());
                    map.put("msg", "上传失败");
                    return map;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code", LoginStatus.LOGIN_FAILED.getCode());
            map.put("msg", "上传失败");
        }
        if (success) {
            map.put("code", LoginStatus.LOGIN_SUCCESS.getCode());
            map.put("msg", "全部上传成功");
            map.put("data", newNames);
        }
        return map;
    }
}
