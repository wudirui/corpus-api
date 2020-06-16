package com.zr.corpus.service.impl;

import com.zr.corpus.dao.CorpusDao;
import com.zr.corpus.service.CorpusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CorpusServiceImpl implements CorpusService {
    @Autowired
    CorpusDao corpusDao;

    @Override
    public List<Map<String, Object>> getOne(Map<String, Object> params) {
        return corpusDao.getOne(params);
    }
}
