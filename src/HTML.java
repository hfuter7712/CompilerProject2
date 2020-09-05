import java.io.*;
import java.util.function.Predicate;

class HTML {

    void HtmlWrite(LALR lrH){
        try {
            System.out.println("asa"+lrH.ProjectSetNum);
            PrintStream old = System.out;
            PrintStream ps=new PrintStream("LALRPrint.html");
            System.setOut(ps);
            String pre=new String("<!doctype html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\t <link rel=\"stylesheet\" href=\"https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
                    "  <script src=\"https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js\"></script>\n" +
                    "  <script src=\"https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js\"></script>\n" +
                    "  <script src=\"https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
                    "\t  <link rel=\"stylesheet\" href=\"https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css\">\n" +
                    "<meta charset=\"utf-8\">\n" +
                    "<title>LALR项目集族可视化输出</title>\n" +
                    "\t<style>\n" +
                    "        .total{\n" +
                    "            text-align: center;\n" +
                    "            width: auto;\n" +
                    "\t\t\theight: auto;\n" +
                    "\t\t\tbackground-color:antiquewhite;\n" +
                    "\t\t\tfloat: left;\n" +
                    "        }\n" +
                    "\t\t.label{\n" +
                    "\t\t\twidth: 240px;\n" +
                    "\t\t\theight: 1000px;\n" +
                    "\t\t\tpadding: 10px;\n" +
                    "            text-align:center;\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\t\n" +
                    "\t\t}\n" +
                    "\t\t.title{\n" +
                    "\t\t\tfont-family:Consolas, \"Andale Mono\", \"Lucida Console\", \"Lucida Sans Typewriter\", Monaco, \"Courier New\", \"monospace\";\n" +
                    "\t\t\tfont-weight: 700;\n" +
                    "\t\t\tfont-size: 50px;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    "<div class=\"total\">\n" +
                    "<p class=\"title\">LALR(1)项目集族</p>");
            System.out.println(pre);
            for(int i=0;i<lrH.LALRNum;i++)
            {

                System.out.println("<div class=\"label\">\n" +
                        "\t\t<table border=\"1\" class=\"table table-striped table-dark\">");
                System.out.println("<th>L"+lrH.LALRProject[i].No+"</th>");
                for(int j=0;j<lrH.LALRProject[i].OwnNum;j++)
                {
                    StringBuffer SBOUT=new StringBuffer();
                    for(int k=0;k<lrH.LALRProject[i].OwnDetail[j].PreNum;k++)
                    {
                        SBOUT.append(lrH.LALRProject[i].OwnDetail[j].Pre[k]+" ");
                    }
                    System.out.println("<tr>\n<td>"+lrH.LALRProject[i].OwnDetail[j].Sen+"</td><td>"+ SBOUT.toString()+"</td></tr>");
                }
                System.out.println("</table></div>");
            }
            System.out.println("</table>\n</div>\n</div>\n\t\n" +
                    "<body>\n" +
                    "</body>\n" +
                    "</html>\n");
            System.setOut(old);
            System.out.println("这行语句重新定位到标准输出——屏幕");
            System.out.println("asa"+lrH.ProjectSetNum);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }













        try {
            PrintStream old = System.out;
            PrintStream ps=new PrintStream("LR1Print.html");
            System.setOut(ps);
            String pre=new String("<!doctype html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\t <link rel=\"stylesheet\" href=\"https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css\">\n" +
                    "  <script src=\"https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js\"></script>\n" +
                    "  <script src=\"https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js\"></script>\n" +
                    "  <script src=\"https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js\"></script>\n" +
                    "\t  <link rel=\"stylesheet\" href=\"https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css\">\n" +
                    "<meta charset=\"utf-8\">\n" +
                    "<title>LR1项目集族可视化输出</title>\n" +
                    "\t<style>\n" +
                    "        .total{\n" +
                    "            text-align: center;\n" +
                    "            width: auto;\n" +
                    "\t\t\theight: auto;\n" +
                    "\t\t\tbackground-color:antiquewhite;\n" +
                    "\t\t\tfloat: left;\n" +
                    "        }\n" +
                    "\t\t.label{\n" +
                    "\t\t\twidth: 240px;\n" +
                    "\t\t\theight: 1000px;\n" +
                    "\t\t\tpadding: 10px;\n" +
                    "            text-align:center;\n" +
                    "\t\t\tfloat: left;\n" +
                    "\t\t\t\n" +
                    "\t\t}\n" +
                    "\t\t.title{\n" +
                    "\t\t\tfont-family:Consolas, \"Andale Mono\", \"Lucida Console\", \"Lucida Sans Typewriter\", Monaco, \"Courier New\", \"monospace\";\n" +
                    "\t\t\tfont-weight: 700;\n" +
                    "\t\t\tfont-size: 50px;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    "<div class=\"total\">\n" +
                    "<p class=\"title\">LR(1)项目集族</p>");
            System.out.println(pre);
            for(int i=0;i<lrH.LR1Num;i++)
            {

                System.out.println("<div class=\"label\">\n" +
                        "\t\t<table border=\"1\" class=\"table table-striped table-dark\">");
                System.out.println("<th>L"+i+"</th>");
                for(int j=0;j<lrH.LR1Project[i].OwnNum;j++)
                {
                    StringBuffer SBOUT=new StringBuffer();
                    for(int k=0;k<lrH.LR1Project[i].OwnDetail[j].PreNum;k++)
                    {
                        SBOUT.append(lrH.LR1Project[i].OwnDetail[j].Pre[k]+" ");
                    }
                    System.out.println("<tr>\n<td>"+lrH.LR1Project[i].OwnDetail[j].Sen+"</td><td>"+ SBOUT.toString()+"</td></tr>");
                }
                System.out.println("</table></div>");
            }
            System.out.println("</table>\n</div>\n</div>\n\t\n" +
                    "<body>\n" +
                    "</body>\n" +
                    "</html>\n");
            System.setOut(old);
            System.out.println("这行语句重新定位到标准输出——屏幕");
            //System.out.println("asa"+lrH.ProjectSetNum);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }






    }

