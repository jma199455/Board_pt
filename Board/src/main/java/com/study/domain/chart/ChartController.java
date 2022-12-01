package com.study.domain.chart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {


    // 홈
    @GetMapping("/chart/main.do")
    public String openPostWrite() {
        return "chart/main";
    }




}
