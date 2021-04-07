package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.Mvcregisterdao;
import kr.or.bit.dto.registerdto;

/*
 	/aaa.do
 	/mvc.do
 	/아무거나.do
  */
@WebServlet("*.do")
public class RegisterFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RegisterFrontController() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 요청주소
    	// 목록보기 : list.do
    	// 글쓰기   : write.do
    	// 글쓰기 처리 : writeok.do
    	// 오면 doProcess 실행
    	
    	// 서비스 판단방법 : command 
    	//                url
    	// 1. command 방식 :  servlet.do?cmd=login&id=kglim&pwd=1004   > cmd > if(cmd.equals("login"))
        // 2. url 방식 :      login.do?id=kglim&pwd=1004  :  login.do > url 주소로 요청을 판단
    	
    	// 한글처리
    	request.setCharacterEncoding("UTF-8");
    	
    	// 데이터 받기
    	String command = request.getParameter("cmd");
    	
    	// 요청 판단해서 서비스 만들기
    	String viewpage="";
    	
    	if(command.equals("register")) { //?cmd=register
    		// 화면전달
    		viewpage = "/WEB-INF/Register/Register.jsp";
    		
    	}else if(command.equals("registerok")) {
    		// 로직처리
    		// 추가적인 데이터
    		int id =Integer.parseInt(request.getParameter("id"));
    		String pwd = request.getParameter("pwd");
    		String email = request.getParameter("email");
    		
    		
    		
    		// controller -> service 요청 -> dao 요청 ->
    		registerdto dto = new registerdto();
    		dto.setId(id);
    		dto.setPwd(pwd);
    		dto.setEmail(email);
    		dto.toString();
    		
    		Mvcregisterdao dao = new Mvcregisterdao();
    		int result = dao.writeok(dto);
    		
    		String resultdata="";
    		if(result>0) {
    			resultdata = "welcome to bit"+dto.getId()+"님";
    			
    		}else {
    			resultdata="Insert Fail retry";
    		}
    		
    		// 결과 저장
    		request.setAttribute("data", resultdata);
    		
    		//view page
    		viewpage = "/WEB-INF/Register/Register_welcome.jsp";
    		
    		
    		
    	}
    	
    	RequestDispatcher dis= request.getRequestDispatcher(viewpage);
    	
    	dis.forward(request, response);
    
    
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
