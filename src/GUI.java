import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class GUI{
    LALR process=new LALR();
    JFrame f =new JFrame("编译原理课程设计2:LALR(1)文法分析小程序");     //构建主窗口JFrame
    JFrame Label=new JFrame("构造分析表");   //构造副窗口，用于显示构造分析表
    JLabel show=new JLabel("点击开始按钮进行语法分析");    //文本显示区域，用于提示以及文字信息输出

    JButton testButton=new JButton("文法分析");           //分析文法，通过输入框内的文法得到first集，follow集以及构造分析表
    JButton CleanButton=new JButton("清除");  //按钮，用于显示当前状态的follow集
    JButton ShowLR1SetButton=new JButton("显示LR1集族");
    JButton ShowSetButton=new JButton("显示LALR集族");
    JButton HTMLButton=new JButton("HTML显示集族(前后对比）");
    JLabel Title=new JLabel("LALR(1)文法小程序");                //标题
    JTextArea GrammarInput=new JTextArea("");                       //文法输入框，用于文法的输入
    JLabel GrammerIns=new JLabel("请在右侧方框内输入文法");          //用于提示在指定方框输入文法或者测试语句
    JScrollPane Input=new JScrollPane();                            //滚动面板，考虑到文法可能超出显示区域，设置可滚动面板
    // Object[][] OutputData= {
    //         {"步骤","分析栈","剩余输入串","所用产生式","动作"}
    // };
    Object []Columns={"步骤","状态栈","符号栈","剩余输入串","动作说明"};     //输出JTable表格的表头
    JTable output;                //表格，以表格的形式输出结果
    void MainFunction(){
        prepareGUI();
    }                                 //UI主函数，用于初始化UI
    /* public static void main(String[] args)
     {
     learn first=new learn();

     first.LRZero();

     }*/
    /*
    UI准备函数
    对GUI进行初始化，包括对按钮的事件机制绑定，中间容器添加组件，容器添加中间容器，设置样式等
     */
    private void prepareGUI(){
        Title.setFont(new Font("宋体",Font.BOLD,20));        //设置标题样式

        //设置各个按钮的活动命令
        testButton.setActionCommand("test");
        CleanButton.setActionCommand("Clean");
        ShowSetButton.setActionCommand("ShowSet");
        HTMLButton.setActionCommand("HTML");
        ShowLR1SetButton.setActionCommand("ShowLR1Set");

        //将各个按钮绑定事件响应机制
        testButton.addActionListener(new SelfButtonActionListener());
        CleanButton.addActionListener(new SelfButtonActionListener());
        ShowSetButton.addActionListener(new SelfButtonActionListener());
        HTMLButton.addActionListener(new SelfButtonActionListener());
        ShowLR1SetButton.addActionListener(new SelfButtonActionListener());

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           //设置主窗口默认关闭方式
        f.setSize(700,700);                         //设置主窗口大小
        f.setLayout(new BorderLayout(10,20));         //设置主窗口布局，为BorderLayout

        //设置各种面板，用于存储组件，后期一次性添加
        JPanel j6=new JPanel();
        JPanel j1=new JPanel();
        j1.add(show);
        JPanel j2=new JPanel();
        JPanel j5=new JPanel();
        Input.setViewportView(GrammarInput);             //将滚动面板的可视区域设为文法输入框，赋予其滚动能力
        //output.setGridColor(Color.GRAY);
        j2.setLayout(new GridLayout(2,2));
        j5.setLayout(new GridLayout(4,4));

        j5.add(Input);


        JPanel j3=new JPanel();

        j3.add(Title);


        JPanel buttonGroup=new JPanel();

        //按钮组添加各个按钮
        buttonGroup.add(testButton);
        buttonGroup.add(ShowLR1SetButton);
        buttonGroup.add(ShowSetButton);
        buttonGroup.add(CleanButton);
        buttonGroup.add(HTMLButton);



        //result.add(output);
        //将各个面板和组件依次添加到布局中的东南西北中中
        f.add(j3,BorderLayout.NORTH);
        f.add(show,BorderLayout.EAST);
        f.add(buttonGroup, BorderLayout.SOUTH);
        f.add(GrammerIns,BorderLayout.WEST);
        f.add(j5,BorderLayout.CENTER);
        f.setBackground(Color.BLUE);
        f.setVisible(true);
    }

    /*
    按钮相应类（继承事件响应类）
    用于响应点击各按钮的事件编写
     */
    class SelfButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String command=e.getActionCommand();
            //点击开始按钮，进入对测试句的文法分析
            if(command.equals("test"))
            {
                process.CreateClosure(GrammarInput.getText());
                process.transform();
                process.CLASSPRINT();

                process.Melt();
                if(process.Mnum==0)
                {

                }
                process.CLASSPRINT();
                process.ActionAndGotoPrint();
            }
                //点击first集显示按钮，对当前first集进行显示
            else if(command.equals("Actionshow"))
            {
                JFrame ActionWindow=new JFrame("Action表");
                JScrollPane ActionScroll=new JScrollPane();
                String[][] ActionOutput=new String[process.ProjectSetNum][process.ActionNum+1];
                String[] ActionColumns=new String[process.ActionNum+1];
                for(int i=0;i<process.ProjectSetNum;i++)
                {
                    ActionOutput[i][0]=String.valueOf(i);
                    for(int j=0;j<process.ActionNum;j++)
                    {
                        if(i==0)
                            ActionColumns[j+1]=process.ActionArray[j];
                        if(process.Action[i][j]==null)
                            ActionOutput[i][j+1]=" ";
                        else
                            ActionOutput[i][j+1]=process.Action[i][j];
                    }
                }
                ActionColumns[0]="状态";
                JTable ActionTable=new JTable(ActionOutput,ActionColumns);
                JLabel ActionTitle=new JLabel("Action表");
                JLabel ActionMessage=new JLabel("A代表10,B代表11,C代表12,以此类推");
                ActionScroll.setViewportView(ActionTable);
                ActionWindow.setLayout(new BorderLayout());
                ActionWindow.add(ActionScroll,BorderLayout.CENTER);
                ActionWindow.add(ActionTitle,BorderLayout.NORTH);
                ActionWindow.add(ActionMessage,BorderLayout.EAST);
                ActionWindow.setSize(1200,700);
                ActionWindow.setVisible(true);

            }
            else if(command.equals("ShowGoto"))
            {
                JFrame GotoWindow=new JFrame("Goto表");
                JScrollPane GotoScroll=new JScrollPane();
                String[][] GotoOutput=new String[process.ProjectSetNum][process.GotoNum+1];
                String[] GotoColumns=new String[process.GotoNum+1];
                for(int i=0;i<process.ProjectSetNum;i++)
                {
                    GotoOutput[i][0]=String.valueOf(i);
                    for(int j=0;j<process.GotoNum;j++)
                    {
                        if(i==0)
                            GotoColumns[j+1]=process.GotoArray[j];
                        GotoOutput[i][j+1]=String.valueOf(process.GOTOInInteger[i][j]);
                    }
                }
                GotoColumns[0]="状态";
                JTable GotoTable=new JTable(GotoOutput,GotoColumns);
                JLabel GotoTitle=new JLabel("Goto表");
                JLabel GotoMessage=new JLabel("0代表无Goto");
                GotoScroll.setViewportView(GotoTable);
                GotoWindow.setLayout(new BorderLayout());
                GotoWindow.add(GotoScroll,BorderLayout.CENTER);
                GotoWindow.add(GotoTitle,BorderLayout.NORTH);
                GotoWindow.add(GotoMessage,BorderLayout.EAST);
                GotoWindow.setSize(1200,700);
                GotoWindow.setVisible(true);
                //显示副窗口
            }
            else if (command.equals("ShowLR1Set"))
            {
                JFrame SetWindow=new JFrame("LR1项目集族显示窗口");
                JTextArea SetArea=new JTextArea();
                JScrollPane SetScroll=new JScrollPane();
                StringBuffer StringSet=new StringBuffer();

                for(int i=0;i<process.LR1Num;i++)
                {
                    StringSet.append("-------------");
                    StringSet.append(i+"\n");
                    for(int j=0;j<process.LR1Project[i].OwnNum;j++)
                    {
                        StringSet.append(process.LR1Project[i].OwnDetail[j].Sen+"\n");
                        for(int k =0;k<process.LR1Project[i].OwnDetail[j].PreNum;k++)
                            StringSet.append(process.LR1Project[i].OwnDetail[j].Pre[k]+"     ");
                        StringSet.append("\n");
                    }
                    StringSet.append("\n\n");
                }
                SetArea.setText(StringSet.toString());
                SetScroll.setViewportView(SetArea);
                SetWindow.add(SetScroll);
                SetWindow.setSize(600,600);
                SetWindow.setVisible(true);
            }
            else if(command.equals("ShowSet"))
            {
                JFrame SetWindow=new JFrame("LALR项目集族显示窗口");
                JTextArea SetArea=new JTextArea();
                JScrollPane SetScroll=new JScrollPane();
                StringBuffer StringSet=new StringBuffer();

                for(int i=0;i<process.LALRNum;i++)
                {
                    StringSet.append("-------------");
                    StringSet.append(i+"\n");
                    for(int j=0;j<process.LALRProject[i].OwnNum;j++)
                    {
                        StringSet.append(process.LALRProject[i].OwnDetail[j].Sen+"\n");
                        for(int k =0;k<process.LALRProject[i].OwnDetail[j].PreNum;k++)
                            StringSet.append(process.LALRProject[i].OwnDetail[j].Pre[k]+"     ");
                        StringSet.append("\n");
                    }
                    StringSet.append("\n\n");
                }
                SetArea.setText(StringSet.toString());
                SetScroll.setViewportView(SetArea);
                SetWindow.add(SetScroll);
                SetWindow.setSize(600,600);
                SetWindow.setVisible(true);
            }

            //对follow集进行输出，大体原理与上面first输出类似
            else if(command.equals("Clean"))
            {
                System.out.println("清除数据");
                 process = new LALR();
                output=new JTable(process.Output_total,Columns);
                GrammarInput.setText("");
            }else if(command.equals("HTML"))
            {
                HTML ht=new HTML();
                ht.HtmlWrite(process);

                try{
                    URI url=new URI("http://localhost:63342/LALR/LALRPrint.html");
                    java.awt.Desktop.getDesktop().browse(url);
                    URI url2=new URI("http://localhost:63342/LALR/LR1Print.html");
                    java.awt.Desktop.getDesktop().browse(url2);
                }catch (IOException err){
                    err.printStackTrace();

                }
                catch (URISyntaxException URIE)
                {
                    URIE.printStackTrace();
                }
            }
            //对当前预测分析表进行输出
        }
    }
}

// JOptionPane.showMessageDialog(null, "数据输入错误");
