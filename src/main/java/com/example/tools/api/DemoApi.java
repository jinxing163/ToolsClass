package com.example.tools.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JinXing
 * @date 2018/9/5 19:11
 */
@RestController
@RequestMapping("/demo")
public class DemoApi {

    /**
     * localhost:8088/demo/saveQuestion?content=11
     * @author JinXing
     * @date 2018/9/5 19:12
     */
    @RequestMapping("/saveQuestion")
    public String saveQuestion(String content){


        return content;
    }


}
