package com.aaa.lee.app.controller;

import com.aaa.lee.app.annotation.TokenAnnocation;
import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.OrderReturnApply;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.utils.JSONUtil;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class OrderReturnController extends BaseController {
    @Autowired
    private IOrderApiService orderService;

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 退货申请
     * @date create in 2019/12/27 13:35
     **/
//    @ApiImplicitParams({@ApiImplicitParam(name = "files", value = "多个文件，", paramType = "form", allowMultiple = true, required = true, dataType = "file")})
    @ApiOperation(value = "多个文件上传", notes = "多个文件上传")
    @PostMapping(value = "/addReturnApply", headers = "content-type=multipart/form-data")
    @TokenAnnocation()
    public ResultData addReturnApply(String token, String orderSn, String reason, MultipartFile files, MultipartFile files1) {
        MultipartFile[] multipartFiles = new MultipartFile[2];
        multipartFiles[0] = files;
        multipartFiles[1] = files1;
        Integer i = orderService.addReturnApply(token, orderSn, reason, multipartFiles);
        if (null != i && i > 0) {
            return success();
        } else {
            return failed();
        }
    }


}
