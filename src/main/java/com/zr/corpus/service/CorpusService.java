package com.zr.corpus.service;

import java.util.List;
import java.util.Map;

public interface CorpusService {
    List<Map<String, Object>> getOne(Map<String, Object> params);

    void addCorpus(Map<String, String> map);
}
