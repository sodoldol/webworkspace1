package com.kh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestGetServlet
 */
@WebServlet("/test.do")
public class RequestGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RequestGetServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("잘 실행되나?");
		/*
		 * Get방식으로 요청했다면 해당 doGet메소드가 호출됨
		 * 
		 * 첫 번째 매개변수인 HttpServletRquest request에는 요청 시 전달된 내용들이 담김(사용자가 입력한 값,
		 * 요청 전송방식, 요청한 사용자의 ip등등)
		 * 
		 * 두 번째 매개변수인 HttpServletResponse response에는 요청 처리 후 응답을 할 때 사용하는 객체
		 * 
		 * 우선 요청을 처리하기 위해 요청 시 전달된 값(사용자가 입력한 값)들을 뽑는다
		 * request의 parameter영역 안에 존재 -> key-value세트로 담겨있(name속성값-value속성값)
		 * 
		 * 따라서 request의 parameter영역으로 부터 전달된 데이터를 뽑는 메소드로
		 * 
		 * - request.getParameter("키값") : String(그에 해당하는 value값) -> 무조건 문자열 형태로
		 * 반환되기 때문에 다른 자료형으로 변경하려면 파싱해야함
		 * 
		 * - request.getParameterValues("키값") : String[](그에 해당하는 value값들) -> 하나의
		 * key값으로 여러 개 value값들을 전달받을 경우(체크박스)문자열 배열형으로 변환 가능
		 * */
		
		String name = request.getParameter("name"); //"유진", ""(텍스트 상자가 빈 경우 빈 문장열이 넘어감)
		String gender = request.getParameter("gender"); //"F"/"M"/null -> 라디오 버튼 체크 안 한경우
		int age = Integer.parseInt(request.getParameter("age")); // "20", ""(값이 없는 경우 NumberFormatException발생)
		// 무조건 문자열 형태이기 때문에 파싱 필요
		
		//체크박스와 같이 복수 개의 정보를 받을 때는 배열로 받아야함
		String[] foods = request.getParameterValues("food"); //["한식", "일식"]/null(체크한 게 없다면 null반환)
		
		/*
		 * 뽑아낸 값들을 가지고 요청처리 해줘야함(db와 상호작용함)
		 * 보통의 흐름 : service의 메소드 호출 시 뽑은 값들을 전달(wrapping) - dao호출 - db sql문 결과값 반환
		 * 
		 * int result = new MemberService().insertMember(m);
		 * if(result > 0) : 성공이라고 보고 commit, 실패 시 rollback(Service가 처리)
		 * Service와 dao단은 거의 변동없이 view와 controller단의 소스코드 상의 변화가 많을 것이다
		 * 
		 * 위 요청처리를 다 했다는 가정 하에 사용자가 보게 될 응답페이지를 꾸밀 예정
		 * 
		 * 장점 : java코드 내에 작성하기 때문에 반복문, 조건문 등 유용한 메소드 활용 가능
		 * 단점 : 복잡, 혹시라도 추후 html을 수정하고자 한다면 java소스코드 내에서 수정이 이루어지기 때문에
		 * 수정된 내용을 반영시키고자 한다면 서버를 restart해야함
		 * */
		
		// response객체를 통해 사용자에게 html(응답화면)전달
		// 1) 이제부터 내가 출력할(응답할) 내용은 문서형태의 html이고 문자셋은 utf-8이라는 것을 지정
		response.setContentType("text/html; charset=UTF-8");
		
		// 2) 응답하고자 하는 사용자와의 스트림을 생성
		PrintWriter out = response.getWriter();
		
		// 3) 생성된 스트림을 통해 응당 html구문을 한 줄씩 출력
		out.println("<html>");
		out.println("<head>");
		out.println("<style>");
		out.println("h2{color:red}");
		out.println("#name{color:orange}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<h2>개인정보 응답화면</h2>");
		
		//out.println("<span id='name'>" + name + "</span>");
		out.println("<span id='name'>" + name + "</span>");
		out.println("<span id='age'>" + age + "</span>");
		out.println("<span id='gender'>" + gender + "</span>");
		out.println("<span id='city'>" + request.getParameter("city") + "</span>");
		out.print("좋아하는 음식은 ::");
		if(foods == null) {
			out.print("없습니다");
		}else {
			out.println("<ul>");
		
			for(String food : foods) {
				out.printf("<li> %s </li>", food);
			}
			out.println("</ul>");
		}
		
		out.println("</body>");
		out.println("</html>");
		
		System.out.println(name);
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
