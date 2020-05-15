package com.staffGauge.survey.cargo.controller;

import com.cargo.api.ApiCargoService;
import com.cargo.dao.Cargo;
import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.cargo.thrift.CargoServiceProvider;
import com.staffGauge.survey.cargo.util.JSONString;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Mr.F on 2020/2/6
 */
@Controller
@RequestMapping(path = "/cargo", produces = {"application/json;charset=UTF-8"})
public class CargoController {
    @Autowired
    private CargoServiceProvider cargoServiceProvider;
    @Reference
    private ApiCargoService cargoService;

    @RequestMapping("/upimg")
    @PostMapping
    @ResponseBody
    public String up(@RequestParam("file") MultipartFile picture) {
        //获取文件在服务器的储存位置
        String path = "/home/diamond/桌面/staffGaugesurvey/match/";
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
        String msg = "";
        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            //ToDo 调用接口
            msg = cargoServiceProvider.getCargoService("127.0.0.1", 8000, path + fileName);
            System.out.println(msg);
        } catch (Exception e) {

        }
        return JSONString.getJSONString(200, msg);
    }


    @RequestMapping("/getAllCargoList")
    @GetMapping
    @ResponseBody
    public PageInfo<Cargo> getAllCargoList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        Cargo cargo = new Cargo();
        cargo.setPageNum(pageNum);
        cargo.setPageSize(pageSize);
        return cargoService.selectAllCargoList(cargo);
    }

    @RequestMapping("/getCargoList")
    @GetMapping
    @ResponseBody
    public PageInfo<Cargo> getCargoList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("username") String username) {
        Cargo cargo = new Cargo();
        cargo.setPageNum(pageNum);
        cargo.setPageSize(pageSize);
        cargo.setPerson(username);
        return cargoService.selectCargoList(cargo);
    }

    @RequestMapping("/insertCargo")
    @PostMapping
    @ResponseBody
    public String insertCargo(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        Cargo cargo = new Cargo();
        cargo.setFreightersNum(data.get("freightersNum"));
        cargo.setStartWeight(Float.parseFloat(data.get("startWeight")));
        cargo.setEndWeight(Float.parseFloat(data.get("endWeight")));
        cargo.setGoodsWeight(Float.parseFloat(data.get("goodsWeight")));
        cargo.setPerson(data.get("person"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        cargo.setTime(df.format(new Date()));
        if (cargoService.insertCargolList(cargo)) return JSONString.getJSONString(200, "写入货物成功");
        else return JSONString.getJSONString(500, "服务器异常");
    }
}
