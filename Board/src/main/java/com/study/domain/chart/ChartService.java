package com.study.domain.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ChartService {

    @Autowired
    private ChartMapper chartMapper;

    public StatisticsLineResVO getLine(ChartRequestVO request) throws Exception {

        //StatisticsLineResVO result = new StatisticsLineResVO();
        
        // 전일 ~ 1주일 전 까지 날짜
        List<String> labels = new ArrayList<>();

        // 그냥 다 밑에다 선언하면 안되나?
		Integer[] men = new Integer[labels.size()]; // 일단 0으로 설정??
		Integer[] women = new Integer[labels.size()];
		Integer[] total = new Integer[labels.size()];
        //

        try {
            log.debug(request.getStartDt());
            log.debug(request.getEndDt());

            // 날짜 비교를 위해
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(request.getStartDt()));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(sdf.parse(request.getEndDt()));

            while(cal1.compareTo(cal2) != 1) {  // 1 크다 0같다 -1 작다
				labels.add(sdf.format(cal1.getTime()));
				cal1.add(Calendar.DATE, 1);     // 해당 부분 주석 -> 무한루프
			}
			log.debug("label : {}", labels);

            men = new Integer[labels.size()];
			women = new Integer[labels.size()];
			total = new Integer[labels.size()];

            // 여기부터 확인 
            List<StatisticsGenderBarVO> line = new ArrayList<>();  // 리스트 찍어보기

            line = chartMapper.getListLine(request);
            //log.debug("line : {} " , line);
            // line = chartMapper.getListLine2(request)


            for(int i = 0; i < labels.size(); i++) {
				men[i] = women[i] = total[i] = 0; // 제거하고 해보기 , 초기화 꼭 해야하는지
				for(StatisticsGenderBarVO vo : line) {
					if(labels.get(i).equals(vo.getLabel())) {
						men[i] = vo.getMen();
						women[i] = vo.getWomen();
						total[i] = vo.getTotal();
						break;
					}
				}
			}

            // return 생성자 사용하지 않고 set으로 해보기
            /*  
            result.setLabels(labels.stream().toArray(String[]::new));
            result.setMen(men);
            result.setWomen(women);
            result.setTotal(total);
            */

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new StatisticsLineResVO(labels.stream().toArray(String[]::new), men, women, total);	// String[]
        //return result;
    }
    

}
