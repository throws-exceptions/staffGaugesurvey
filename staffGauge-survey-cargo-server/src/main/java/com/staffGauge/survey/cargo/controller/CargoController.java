package com.staffGauge.survey.cargo.controller;

import com.staffGauge.survey.cargo.util.JSONString;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Mr.F on 2020/2/6
 */
public class CargoController {

    @RequestMapping("/up")
    @PostMapping
    @ResponseBody
    public String up(@RequestParam("file") MultipartFile picture) {
        //获取文件在服务器的储存位置
        String path = "/home/diamond/桌面/staffGaugesurvey/vue-manger/src/assets/Cargo";
        File filePath = new File(path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            //ToDo 调用接口
        } catch (IOException e) {

        }
        return JSONString.getJSONString(200, "上传图片成功");
    }

    @RequestMapping("/Caculate")
    @PostMapping
    @ResponseBody
    public String Caculate(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        //给定数
        int Fp = Integer.valueOf(data.get("Fp"));
        int Fs = Integer.valueOf(data.get("Fs"));
        int Mp = Integer.valueOf(data.get("Mp"));
        int Ms = Integer.valueOf(data.get("Ms"));
        int Ap = Integer.valueOf(data.get("Ap"));
        int As = Integer.valueOf(data.get("As"));
        int dF = Integer.valueOf(data.get("dF"));
        int dA = Integer.valueOf(data.get("dA"));
        int dM = Integer.valueOf(data.get("dM"));
        int Lbp = Integer.valueOf(data.get("Lbp"));
        //平均吃水
        int Fps = (Fp + Fs) / 2;
        int Aps = (Ap + As) / 2;
        int Mps = (Mp + Ms) / 2;
        //校正前吃水差
        int T = Aps - Fps;
        //校正值
        int temp = (Lbp + dF - dA);
        int Fc = (T * dF) / temp;
        int Ac = (T * dA) / temp;
        int Mc = (T * dM) / temp;
        //校正后水尺
        int Fm = Fps + Fc;
        int Am = Aps + Ac;
        int Mm = Mps + Mc;
        //校正后吃水差
        int Tc = Am - Fm;
        //平均吃水
        int MFA = (Fm + Am) / 2;
        int D_M = (Fm + Am + 6 * Mm) / 8;
        return JSONString.getJSONString("D_M", String.valueOf(D_M));
    }
}
