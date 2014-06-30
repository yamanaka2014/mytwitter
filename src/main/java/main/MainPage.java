package mytwitter.main;

import java.io.*;//IOException;
import javax.naming.InitialContext;
import java.sql.*;
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

public class MainPage extends HttpServlet {

  protected void concreteResponse(HttpServletRequest request , HttpServletResponse response)
              throws ServletException, IOException {
    ServletContext sc = getServletContext();

    HttpSession session = request.getSession(false);
    if(session==null){
      RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
      rd.forward(request, response);

    }

    String userName = (String) session.getAttribute("userName");

    showPage(request,response,userName);
  }

  /**
   * doGet
   *
   * @param request
   * @param response
   */
  protected void doGet(HttpServletRequest request , HttpServletResponse response)
              throws ServletException, IOException {
    ServletContext sc = getServletContext();

    HttpSession session = request.getSession(false);
    if(session==null){
      RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
      rd.forward(request, response);

    }

    String userName = (String) session.getAttribute("userName");

    showPage(request,response,userName);
  }

  /**
   * doPost
   *
   * @param request
   * @param response
   */
  protected void doPost(HttpServletRequest request
             , HttpServletResponse response)
              throws ServletException, IOException {

    // エンコーディング
    response.setContentType("text/html; UTF-8");
    ServletContext sc = getServletContext();

    HttpSession session = request.getSession(false);
    if(session==null){
      RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
      rd.forward(request, response);

    }

    String userName = (String) session.getAttribute("userName");


/*
    // サーブレットコンテキストを取得
    ServletContext sc = getServletContext();

    // 次に遷移するページ(初期値)
    String nextPage = "/index.jsp";

    // 未入力チェック
    if (userName.length() == 0) {

      nextPage = "/index.jsp";
    }
*/

/* 短文一覧取得 */

    showPage(request,response,userName);

    // ページ遷移
    //sc.getRequestDispatcher(nextPage).forward(request, response);
    //response.sendRedirect(nextPage);

  }

  public void showPage(HttpServletRequest request
             , HttpServletResponse response
             , String userName)
              throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out=response.getWriter();
    out.println("<html><head>");
    out.println("<title>短文投稿</title>");
    out.println("</head><body>");
    out.println("<center>");
    out.println("<h4>短文投稿Webアプリ</h4>");
    out.println("<br><br><br>");
    out.println("<form action=\"contribution\"  method=\"post\">");
    out.println("<textarea name=\"shortSentence\" cols=\"20\" rows=\"5\" maxlength=\"100\" style=\"width:50%;height:50%;\">");
    out.println("</textarea><br>");
    out.println("<input type=\"submit\" value=\"投稿\"></input>");
    out.println("</form><br>");
/*
    Connection con = null;
    try{
      System.out.println("DBと接続開始");
      InitialContext ctx = new InitialContext();
      DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MySQLDS");
      con = ds.getConnection();
      System.out.println("DBと接続完了");
      // プレースホルダで SQL 作成
      String sql = "SELECT s.CONTRIBUTION_DATE, s.SHORT_SENTENCE FROM SHORT_SENTENCE s, USER u WHERE s.USER_ID = u. USER_ID AND USER_NAME = ?;";
      // SQL をプリコンパイル
      PreparedStatement pstmt = con.prepareStatement(sql);
      // パラメーターセット
      pstmt.setString(1, userName);
      //ResultSet rs = stmt.executeQuery(sql);
      System.out.println("SQL実行");
      ResultSet rs = pstmt.executeQuery();
      System.out.println("SQ接続完了");
      java.sql.Date contributionDate = null;
      String date = "";
      String shortSentence = "";
      int count = 0;
      while(rs.next() || count < 10){
        //contributionDate = rs.geDate("CONTRIBUTION_DATE");
        date = rs.getString("CONTRIBUTION_DATE");
        //date = contributionDate.toString();
        shortSentence = rs.getString("SHORT_SENTENCE");
        out.println("<br><p>");
        out.println(userName);
        out.println(" ");
        out.println(date);
        out.println("<br>");
        out.println(shortSentence);
        out.println("<br><hr>");
        count++;

      }
      rs.close();
      //stmt.close();
      pstmt.close();
      con.close();
    } catch (SQLException e){
      System.out.println("SQLException:" + e.getMessage());
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/main");
      rd.forward(request, response);
    } catch (Exception e){
      System.out.println("Exception:" + e.getMessage());
      //out.println("SQLException:" + e.getMessage());
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/main");
      rd.forward(request, response);
    }
*/
    out.println("</center></body></html>");

  }

}
