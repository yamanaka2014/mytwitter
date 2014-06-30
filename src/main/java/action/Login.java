package mytwitter.action;

import java.io.*;//IOException;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.Driver;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Properties;
import javax.naming.InitialContext;
import javax.servlet.*;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
import javax.servlet.http.*;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import javax.sql.*;

public class Login extends HttpServlet {

  /**
   * doGet
   *
   * @param request
   * @param response
   */
  protected void doGet(HttpServletRequest request , HttpServletResponse response){}
              //throws ServletException, IOException {}

  /**
   * doPost
   *
   * @param request
   * @param response
   */
  protected void doPost(HttpServletRequest request
             , HttpServletResponse response)
              throws ServletException, IOException {

    String nextPage = "/index.jsp";

    // エンコーディング
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html; UTF-8");

    // フォームデータより名前を取得
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");

    ServletContext sc = getServletContext();

    if(userName.length() == 0){
      RequestDispatcher rd = sc.getRequestDispatcher(nextPage);
      rd.forward(request, response);
      return;
    }

    if(password.length() == 0){
      RequestDispatcher rd = sc.getRequestDispatcher(nextPage);
      rd.forward(request, response);
      return;
    }

//* DB接続（今回は使用しない）
/*
    String driverName = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost/mytwitter";
    String user = "universal";
    String pass = "N94jwI5k";
*/
/**
    try {
      Class.forName(driverName);
      con = DriverManager.getConnection("jdbc:mysql://localhost/mytwitter","universal","N94jwI5k");
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    } catch (SQLException e){
      e.printStackTrace();
    }
**/

    int count = 0;
    Connection con = null;
    try{
      //Connection con = DriverManager.getConnection(url, user, password);
      //Class.forName(mysql.mysql.jdbc.Driver);
      //con = DriverManager.getConnection("jdbc:mysql://localhost/mytwitter","universal","N94jwI5k");
      //Statement stmt = conn.createStatement();

      //Context ctx = new InitialContext();
      System.out.println("DBと接続開始");
      InitialContext ctx = new InitialContext();
      DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MySQLDS");
      con = ds.getConnection();
      System.out.println("DBと接続完了");
      // プレースホルダで SQL 作成
      String sql = "SELECT COUNT(*) AS CNT FROM USER WHERE user_name = ? AND login_password = ?;";
      // SQL をプリコンパイル
      PreparedStatement pstmt = con.prepareStatement(sql);
      // パラメーターセット
      pstmt.setString(1, userName);
      pstmt.setString(2, password);
      //ResultSet rs = stmt.executeQuery(sql);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        count = rs.getInt("CNT");
      }
      rs.close();
      //stmt.close();
      pstmt.close();
      con.close();
    } catch (SQLException e){
      System.out.println("SQLException:" + e.getMessage());
      //ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher(nextPage);
      rd.forward(request, response);
    } catch (Exception e){
      System.out.println("Exception:" + e.getMessage());
      //out.println("SQLException:" + e.getMessage());
      //ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher(nextPage);
      rd.forward(request, response);
    }

    if(count == 1){
      // ログイン成功
      //HttpSession session = req.getSession(true);
      //session.setAttribute("id", userName);
 
      
      //RequestDispatcher rd = sc.getRequestDispatcher("/main");
      //rd.forward(request, response);
      HttpSession session = request.getSession(true);
      session.setAttribute("userName", userName);
      nextPage = "/main";
      RequestDispatcher rd = sc.getRequestDispatcher(nextPage);
      rd.forward(request, response);
    } else{
      //ログイン失敗
      //resp.sendRedirect("/index.jsp");
      //ServletContext sc = getServletContext();
      System.out.println("ログイン失敗");
      RequestDispatcher rd = sc.getRequestDispatcher(nextPage);
      rd.forward(request, response);
    }



/*
    // サーブレットコンテキストを取得
    ServletContext sc = getServletContext();

    // 次に遷移するページ(初期値)
    String nextPage = "/main";

    // 未入力チェック
    if (userName.length() == 0) {

      nextPage = "/index.jsp";
    }

    // ページ遷移
    //sc.getRequestDispatcher(nextPage).forward(request, response);
    //response.sendRedirect(nextPage);
    //ServletContext sc = getServletContext();
    RequestDispatcher rd = sc.getRequestDispatcher(nextPage);
    rd.forward(request, response);
*/

/** テスト用表示
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out=response.getWriter();
    out.println("<html><head>");
    out.println("<title>Hello, World!!</title>");
    out.println("</head><body>");
    out.println("<p>Hello,World!!</p>");
    out.println("</body></html>");
**/
  }
}
