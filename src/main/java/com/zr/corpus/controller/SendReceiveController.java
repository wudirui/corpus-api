package com.zr.corpus.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zr.corpus.common.PageUtils;
import com.zr.corpus.service.CorpusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/wch")
public class SendReceiveController {
    @Autowired
    CorpusService corpusService;

    @RequestMapping(value = "getSentence", method = RequestMethod.POST)
    public Object receiveMsg(@RequestBody JSONObject params) {
        int index = params.getInteger("index");
        PageHelper.startPage(index, 1);
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == null || entry.getValue() == "") {
                params.put((String) entry.getKey(), null);
            }
        }
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

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("audioStr", audioStr);
        map.put("sex", sex);
        map.put("dialect", dialect);
        map.put("address", address);
        map.put("sentenceId", sentenceId);
        corpusService.addCorpus(map);
        System.out.println(name);
        return "ok";
    }
}
