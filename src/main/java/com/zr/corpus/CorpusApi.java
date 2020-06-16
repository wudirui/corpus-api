package com.zr.corpus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.zr.corpus.dao"})
public class CorpusApi {
    public static void main(String[] args) {
        SpringApplication.run(CorpusApi.class);
    }
}
