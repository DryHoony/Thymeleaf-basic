package hello.thymeleaf.basic;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello Spring!");

        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) { // SpringEL - 타임리프에서의 변수 표현식

        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    @GetMapping("/basic-objects") // 기본 객체들
    public String basicObjects(Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {

        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data){
            return "Hello " + data;
        }
    }

    @GetMapping("/date") // 유틸리티 객체와 날짜
    public String data(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }



    @GetMapping("/link") // URL 링크, 이건 쫌 중요하다!!
    public String link(Model model){
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");

        return "basic/link";
    }


    @GetMapping("/literal") // 리터럴(Literals) : 소스 코드상 고정된 값을 말한다. 작은 따옴표로 감싸는 것 주의!
    public String literal(Model model) {
        model.addAttribute("data", "Spring");
        return "basic/literal";
    }


    @GetMapping("/operation") // 연산
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");
        return "basic/operation";
    }



    @GetMapping("/attribute") // 속성(Attribute) 값 설정, th:*
    public String attribute() {
        return "basic/attribute";
    }


    @GetMapping("/each") // 반복, th:each
    public String each(Model model){
        addUsers(model);
        return "basic/each";
    }

    @GetMapping("/condition") // 조건부 평가
    public String condition(Model model) {
        addUsers(model);
        return "basic/condition";
    }

    @GetMapping("/comments") // 주석 - HTML 주석 / 타임리프 파서 주석 / 타임리프 프로토타입 주석
    public String comments(Model model) {
        model.addAttribute("data", "Spring!");
        return "basic/comments";
    }


    @GetMapping("/block") // 블록 - HTML 태그가 아닌 타임리프의 유일한 자체 태그
    public String block(Model model) {
        addUsers(model);
        return "basic/block";
    }

    @GetMapping("/javascript") // 자바스크립트 인라인 - 자바스크립트에서 타임리프를 편리하게 사용
    public String javaScript(Model model){

        model.addAttribute("user", new User("userA", 10));
        addUsers(model);

        return "basic/javascript";
    } // 웹 브라우저에서 '페이지 소스보기' 로 확인




    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }



}
