package com.zr.corpus.dao;

import java.util.List;
import java.util.Map;

public interface CorpusDao {
    List<Map<String, Object>> getOne(Map<String, Object> params);
}
