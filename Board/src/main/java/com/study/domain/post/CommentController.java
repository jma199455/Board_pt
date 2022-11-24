package com.study.domain.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;

    /*  
    // Gson 사용
    // (0. new Gson().toJsonTree(commentList) 사용하기)
    @GetMapping(value = "/comments/{boardIdx}")
	public JsonObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) {

		JsonObject jsonObj = new JsonObject();

		List<CommentDto> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {

			JsonArray jsonArr = new Gson().toJsonTree(commentList).getAsJsonArray();
            
            //System.out.println("jsonArr ==========> " + jsonArr);

			jsonObj.add("commentList", jsonArr);
		}
        
		return jsonObj;
	}
    */

    
    /*  
    // Jackson 사용
    // List<CommentDto> JSONObject 리턴하기
	@GetMapping(value = "/comments/{boardIdx}")
	public JSONObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) throws JsonProcessingException, JSONException {
        
		List<CommentDto> commentList = commentService.getCommentList(params);

        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray();
        
        HashMap<String,Object> contents = null;
        CommentDto noticeInfo = new CommentDto();
        List<CommentDto> list = new ArrayList<CommentDto>();

        int listSize = commentList.size();

        for (int i=0; i < listSize; i++) {
            
            noticeInfo = commentList.get(i);
            CommentDto dto = new CommentDto();

            dto.setIdx(noticeInfo.getIdx());
            dto.setBoardIdx(noticeInfo.getBoardIdx());
            dto.setContent(noticeInfo.getContent());
            dto.setWriter(noticeInfo.getWriter());
            dto.setDeleteYn(noticeInfo.getDeleteYn());
            dto.setInsertTime(noticeInfo.getInsertTime());

            list.add(dto);
            
        }
        //String jsonInString = mapper.writeValueAsString(list);  // Json 문자열로 변환!!!
        //System.out.println(jsonInString);
        // JsonParser jsonParser = new JsonParser(); 사용할 수 가 없음.

        JSONObject jsonObj = new JSONObject();
        // jsonObj.put("commentList", jsonInString); Json 문자열이 리턴되고 있음.. 
        jsonObj.put("commentList", list); // list를 JSONObject에 넣어주면 정상적으로 데이터 확인이 되고 있음 , jackson은 따로 writeValueAsString와 JsonParser를 안해줘도 되는지....확실하지 않음..
        
        return jsonObj;

	}
    */
    
    /*  
    // Jackson 사용
    // (1. HashMap<String,Object> JSONObject 리턴하기 )
    @GetMapping(value = "/comments/{boardIdx}")
    public JSONObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) throws JsonProcessingException, JSONException {
        
        List<CommentDto> commentList = commentService.getCommentList(params);

        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray();


        CommentDto noticeInfo = null;
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        
        int listSize = commentList.size();
        for (int i=0; i < listSize; i++) {
            
            noticeInfo = commentList.get(i);

            HashMap<String,Object> contents = new HashMap<>();
            contents.put("idx", noticeInfo.getIdx());
            contents.put("boardIdx", noticeInfo.getBoardIdx());
            contents.put("content", noticeInfo.getContent());
            contents.put("writer", noticeInfo.getWriter());
            contents.put("deleteYn", noticeInfo.getDeleteYn());
            contents.put("insertTime", noticeInfo.getInsertTime());

            list.add(contents);
            
        }
        // String jsonInString = mapper.writeValueAsString(list);  // Json 문자열로 변환
        // JsonParser jsonParser = new JsonParser(); 사용할 수 가 없음.

        JSONObject jsonObj = new JSONObject();
        // jsonObj.put("commentList", jsonInString); Json 문자열이 리턴되고 있음.. 
        jsonObj.put("commentList", list);
        
        return jsonObj;

    }
    */

    // Gson 라이브러리 사용 
    // (1. List<CommentDto> JsonObject로 리턴하기 )
    @GetMapping(value = "/comments/{boardIdx}")
	public JsonObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) throws JsonProcessingException, JSONException {

		List<CommentDto> commentList = commentService.getCommentList(params);

        ObjectMapper mapper = new ObjectMapper(); // writeValueAsString 함수 사용
        List<CommentDto> list = new ArrayList<>(); 

        int listSize = commentList.size();

        //CommentDto dto = new CommentDto(); // 여기다 선언하면 리스트에 데이터 하나가 반복되서 들어감 확인!!!!!!!!! 
        JsonElement jsonElement = null;      // for문 밖에서 jsonObj.add 에 넣어주기 위해서

        for (int i=0; i < listSize; i++) {
            CommentDto noticeInfo = new CommentDto(); // for문 밖에 선언해도 상관없음 아래처럼.
                                                      // Ex) CommentDto noticeInfo = null
                                                      //     noticeInfo = new CommentDto();
            noticeInfo = commentList.get(i);

            CommentDto dto = new CommentDto();

            dto.setIdx(noticeInfo.getIdx());
            dto.setBoardIdx(noticeInfo.getBoardIdx());
            dto.setContent(noticeInfo.getContent());
            dto.setWriter(noticeInfo.getWriter());
            dto.setDeleteYn(noticeInfo.getDeleteYn());
            dto.setInsertTime(noticeInfo.getInsertTime());

            list.add(dto);  // List<CommentDto> 에 넣기
        }
            
        String temp = mapper.writeValueAsString(list);  // Json 문자열로 변환
        JsonParser jsonParser = new JsonParser();
        jsonElement = jsonParser.parse(temp);   // JsonParser.parse를 이용하여 문자열을 JsonElement로 변환

        JsonObject jsonObj = new JsonObject(); // 마지막 return 할 jsonObject 객체 선언
        jsonObj.add("commentList", jsonElement); // jsonElement 

        return jsonObj; // return
	}

    /*  
    // Gson 라이브러리 사용 
    // (1. HashMap<String,Object> JsonObject로 리턴하기 )
	@GetMapping(value = "/comments/{boardIdx}")
	public JsonObject getCommentList(@PathVariable("boardIdx") int boardIdx, @ModelAttribute("params") CommentDto params) throws JsonProcessingException, JSONException {

		List<CommentDto> commentList = commentService.getCommentList(params);

        ObjectMapper mapper = new ObjectMapper(); // writeValueAsString 함수 사용
        List<CommentDto> list = new ArrayList<>(); 

        int listSize = commentList.size();

        HashMap<String,Object> contents = null;
        JsonArray jsonArr = new JsonArray(); // JsonArray 선언 (for문 밖에서 return 해주기 위해서)

        for (int i=0; i < listSize; i++) {
            CommentDto noticeInfo = new CommentDto(); // for문 밖에 선언해도 상관없음
            noticeInfo = commentList.get(i);

            contents = new HashMap<>();
            // 각 필드 map에 mapping
            contents.put("idx", noticeInfo.getIdx());
            contents.put("boardIdx", noticeInfo.getBoardIdx());
            contents.put("content", noticeInfo.getContent());
            contents.put("writer", noticeInfo.getWriter());
            contents.put("deleteYn", noticeInfo.getDeleteYn());
            contents.put("insertTime", noticeInfo.getInsertTime());
              
            String temp = mapper.writeValueAsString(contents);  // Json 문자열로 변환
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(temp);   // JsonParser.parse를 이용하여 문자열을 JsonElement로 변환
            jsonArr.add(jsonElement);   // jsonArr.add(JsonElement or JsonObject가 들어간다!!);
            
        }

        JsonObject jsonObj = new JsonObject(); // 마지막 return 핧 jsonObject 객체 선언
        jsonObj.add("commentList", jsonArr); // 객체에 jsonArr 넣기

        return jsonObj; // return
	}
    */




}
