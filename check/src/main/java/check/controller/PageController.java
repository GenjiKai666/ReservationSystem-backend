package check.controller;

import check.client.ReservationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {
    @Autowired
    ReservationClient reservationClient;
    @GetMapping(value = "/check",produces = MediaType.TEXT_HTML_VALUE)
    public String checkPage(){
        String page_html = "<html>\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>签到页面</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"text-align:center\">\n" +
                "  <form action=http://localhost:8083/check method=post>\n" +
                "    <label for=\"bookName\">订单ID</label>\n" +
                "    <input type=\"number\" id=\"id\" name=\"id\" placeholder=\"输入订单ID\">\n" +
                "    <input type=\"submit\" value=\"提交\">\n" +
                "  </form>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return page_html;
    }
    @PostMapping(value = "/check",produces = MediaType.TEXT_HTML_VALUE)
    public String checkPost(@RequestParam("id") Integer id){
        reservationClient.check(id);
        return checkPage();
    }
}
