package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.study.interceptor.LoggerInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	// 해당 인터페이스를 구현하면 @EnableWebMvc의 자동 설정을 베이스로 가져감


	/*  
	정리전
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new LoggerInterceptor()) // InterceptorRegistry의 addInterceptor( ) 메서드를 이용하여 인터셉터 클래스를 등록
		.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**", "/images/**", "/js/**", "/login", "/loginCheck");	// 인터셉터 호출에서 제외할 URL 또는 PATH  , addPathPatterns( )는 경로 추가.
	}
	*/

	@Override
	// 애플리케이션 내에 인터셉터를 등록해주는 기능.
	public void addInterceptors(InterceptorRegistry registry) {
		
		final String[] excludePath = {

			"/css/**", 
			"/fonts/**", 
			"/plugin/**", 
			"/scripts/**", 
			"/images/**", 
			"/js/**", 
			"/login", 
			"/loginCheck",
			"/logout"

		};

		// InterceptorRegistry의 addInterceptor( ) 메서드를 이용하여 인터셉터 클래스를 등록
		// 인터셉터 호출에서 제외할 URL 또는 PATH  , addPathPatterns( )는 경로 추가.
		registry.addInterceptor(new LoggerInterceptor()).excludePathPatterns(excludePath);	
	}



	// CommonsMultipartResolver Bean 등록
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
		multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024); // 파일당 업로드 크기 제한 (5MB)
		return multipartResolver;
	}

}
