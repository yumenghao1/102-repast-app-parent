package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.OrderReturnApply;
import com.aaa.lee.app.service.OrderItemService;
import com.aaa.lee.app.service.OrderReturnApplyService;
import com.aaa.lee.app.service.OrderService;
import com.aaa.lee.app.service.UploadService;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OrderReturnApplyController extends BaseController {
    @Autowired
    private OrderReturnApplyService orderReturnApplyService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderitemService;
    @Autowired
    private UploadService uploadService;

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 提交退货申请
     * @date create in 2019/12/28 16:55
     **/
    @PostMapping(value = "/addReturnApply", headers = "content-type=multipart/form-data")
    public Integer addReturnApply(@RequestParam("token") String token, @RequestParam("orderSn") String orderSn, @RequestParam("reason") String reason, @RequestPart("files") MultipartFile[] files) {
        try {
            return orderReturnApplyService.addOrderReturnApply(orderSn, files, reason, orderService, uploadService);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
