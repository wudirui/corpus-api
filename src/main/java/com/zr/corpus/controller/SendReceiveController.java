package com.zr.corpus.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.deploy.util.StringUtils;
import com.zr.corpus.common.PageUtils;
import com.zr.corpus.service.CorpusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/wch")
public class SendReceiveController {
    @Autowired
    CorpusService corpusService;

    @RequestMapping(value = "getSentence", method = RequestMethod.POST)
    public Object receiveMsg(@RequestBody JSONObject jsonObject) {
        JSONObject params = jsonObject.getJSONObject("params");
        Integer index = jsonObject.getInteger("index");
        String region = StringUtils.join(params.getJSONArray("region"),",");
        params.put("region",region);
//        String name = params.getString("name");
//        String dialect = params.getString("dialect");
//        String sex = params.getString("sex");
        PageHelper.startPage(index, 1);

        List<Map<String, Object>> list = corpusService.getOne(params);
        PageInfo<Map<String, Object>> info = new PageInfo<>(list);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setCode("0");
        pageUtils.setCount(String.valueOf(info.getTotal()));
        pageUtils.setData(info.getList());
        return pageUtils;
    }

    @RequestMapping(value = "sendAudio", method = RequestMethod.POST)
    public String sendMsg(@RequestParam("audioFile") MultipartFile audioFile, @RequestParam("name") String name,
                          @RequestParam("sex") String sex, @RequestParam("address") String address,
                          @RequestParam("dialect") String dialect, @RequestParam("sentenceId") String sentenceId) throws IOException {
        String preffix = "data:audio/wav;base64,";
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String audioStr = preffix + base64Encoder.encode(audioFile.getBytes()).replaceAll("[\\s*\t\n\r]", "");

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = formatter.format(date);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("audioStr", audioStr);
        map.put("sex", sex);
        map.put("dialect", dialect);
        map.put("address", address);
        map.put("sentenceId", sentenceId);
        map.put("createTime", createTime);
        corpusService.addCorpus(map);
        System.out.println(name);
        return "ok";
    }
}
