package com.study.domain.chart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {


    // 차트 서비스
    @Autowired 
    private ChartService chartService;


    // 파라미터로 search 등록 (startDt, endDt) 사용하기 위해
    @GetMapping("/chart/main.do")
    public String openPostWrite(ChartRequestVO search, Model model) throws Exception {

        List<String> labels = new ArrayList<>();
        System.out.println(labels.size());

        // 전일 ~일주일 간 방문자수 날짜
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);	// 전일 기준까지
		search.setEndDt(sdf.format(cal.getTime()));
		cal.add(Calendar.DATE, -7);	// 1주일 전 부터
		search.setStartDt(sdf.format(cal.getTime()));

        //StatisticsLineResVO data = chartService.getLine(search);    // 확인
        //model.addAttribute("data", data);
        model.addAttribute("data", chartService.getLine(search, "L1"));
        model.addAttribute("data2", chartService.getLine(search, "L2"));



        return "chart/main";
    }

    /*  
    @GetMapping("/chart/chart.do")
    public String openGetChart() {
        return "chart/chart";
    }
    */









}
