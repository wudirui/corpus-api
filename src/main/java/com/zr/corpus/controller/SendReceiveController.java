package com.zr.corpus.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zr.corpus.common.PageUtils;
import com.zr.corpus.service.CorpusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/wch")
public class SendReceiveController {
    @Autowired
    CorpusService corpusService;

    @RequestMapping(value = "receive",method = RequestMethod.POST)
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

    @RequestMapping("send")
    public String sendMsg(@RequestParam MultipartFile file, @RequestParam Map<String, Object> map) {
        return "ok";
    }
}
