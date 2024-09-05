package com.boot.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.boot.DTO.ScreentbDTO;
import com.boot.Service.AreaService_2;
import com.boot.Service.MovieService_2;
import com.boot.Service.ScreenService_2;
import com.boot.Service.TheaterService_2;


@Controller
@Slf4j
@RequestMapping("/ticketing")
public class TicketingController {
	
	@Autowired
	private AreaService_2 areaservice;
	
	@Autowired
	private TheaterService_2 theaterservice;
	
	@Autowired
	private ScreenService_2 screenService;
	
	@Autowired
	private MovieService_2 movieService;
	
	@RequestMapping("/movieselect")
	public String Ticketing(Model model) {
		log.info("ticketing");
		
		model.addAttribute("area", areaservice.selectAll());
		model.addAttribute("date", areaservice.datedual(""));
		model.addAttribute("movie", movieService.selectAll());
		
		return "ticketing/movieselect";
	}
	
	@RequestMapping("/theatershow")
	public ModelAndView theatershow_ajax(@RequestParam HashMap<String, String> param) {
		log.info("@# theatershow_ajax");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ticketing/theater_ajax");
		mav.addObject("areano", param.get("areano"));
		mav.addObject("theater", theaterservice.theater(param));
		
		return mav;
	}

	@RequestMapping("/movieshow")
	public ModelAndView movieshow_ajax(@RequestParam HashMap<String, String> param) {
		log.info("@# movieshow_ajax");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ticketing/movie_ajax");
		mav.addObject("areano", param.get("areano"));
		mav.addObject("theaterno", param.get("theaterno"));
		mav.addObject("movie", movieService.selectAll());
		
		return mav;
	}
	
	@RequestMapping("/dateshow")
	public ModelAndView dateshow_ajax(@RequestParam HashMap<String, String> param) {
		log.info("@# dateshow_ajax");
		log.info("param: " + param);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ticketing/date_ajax");
		
		if(param.get("viewday").equals("no")) {
			// 오늘 날짜를 가져옵니다
	        LocalDate today = LocalDate.now();
	        // 날짜 형식을 설정합니다
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        // 포맷에 맞춰 문자열로 변환합니다
	        String formattedDate = today.format(formatter);
	        param.put("viewday", formattedDate);
		}
		
		mav.addObject("title", movieService.getTitle(param));
		mav.addObject("areano", param.get("areano"));
		mav.addObject("theaterno", param.get("theaterno"));
		mav.addObject("detailinfo", screenService.selectdtl(param));
		
		return mav;
	}
	
	@RequestMapping("/datetxt")
	public @ResponseBody Map<String, Object> datetxt() {
		log.info("@# datetxt");
		Map<String, Object> respones = new HashMap<>();
		
		String txt = areaservice.datedual("").get(0).getTxt();
		log.info("@# txt: " + txt);
		respones.put("date", txt);
		
		return respones;
	}
	
	@RequestMapping("/datetxtparam")
	public @ResponseBody Map<String, Object> datetxtparma(@RequestParam(value = "viewday") String viewday) {
		log.info("@# datetxtparam");
		Map<String, Object> respones = new HashMap<>();
		log.info("dates: " + viewday);
		String txt = areaservice.datedual(viewday).get(0).getTxt();
		log.info("txt: " + txt);
		log.info("@# txt: " + txt);
		respones.put("date", txt);
		
		return respones;
	}
	
	@RequestMapping("/seatselect")
	public String seatselect(@RequestParam HashMap<String, String> param, Model model) {
		log.info("@# seatselect");
		model.addAttribute("param", param);
		return "ticketing/seatselect";
	}
	
}