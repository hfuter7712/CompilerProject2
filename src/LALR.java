import javax.swing.*;
import java.awt.desktop.SystemSleepEvent;
import java.util.*;

public class LALR {
    String test="";
    int[][] ActionInInteger=new int[100][100];
    int[][] GOTOInInteger=new int[100][100];  //定义以数组形式的GOTO表
    String[][] Action=new String[100][100];  //定义ACTION表
    String[][] GOTO=new String[100][100];   //定义GOTO表
    String[][] ProjectSet=new String[100][100];  //定义项目族集,行为项目族，其中的每个为成员d

    int ProjectSetNum=0;                            //项目族个数
    int[] ProjectSoloNum=new int[100];           //每个项目族中的成员个数
    String[][] ProjectSelect=new String[100][100];  //定义每个非终结符的可选项目
    int[] ProjectSelectNum=new int[100];
    String[] Project=new String[100];               //定义所有项目集
    int ProjectNum=0;                               //项目个数
    Map AbleMap=new HashMap();
    Map SentenceMap=new HashMap();                  //存放各文法句
    String[] SentenceArray=new String[100];
    int SentenceArrayNum=0;
    int AbleNum=1;

    String[][] Predicate=new String[100][100];  //定义往后预测一次的字符
    int[][] PredicateNum=new int[100][100];
    Map ActionMap=new HashMap();
    String[] ActionArray=new String[100];
    Map GotoMap=new HashMap();
    String[] GotoArray=new String[100];
    int ActionNum=0;
    int GotoNum=0;

    String[] member=new String[50];
    int[] FirstLength=new int[50];
    int[] FollowLength=new int[50];
    int indexM=0;                                   //非终结符个数
    String[][] first=new String[100][50];           //first集
    String[][] follow=new String[100][50];          //follow集
    Object[][] Output_total=new Object[100][100];   //用于存放输出信息的二维数组
    int OutputMessageNum=0;
    String[][] Split=new String[100][100];         //用于存放分割文法后的各元素
    int[] Split_Length=new int[100];              //对应每行产生式的个数

    int indexB=0;
    char[] extra={'①','②','③','④','⑤'};           //消除左递归时备选非终结符
    int indexEx=0;                                //备选非终结符下标
    int LeftTargetX=0;                           //左递归产生的行数
    int LeftTargetY=0;                           //左递归产生的列数
    String[][] AnalysisL= new String[100][100];  //备选分析数组

    String rowString=new String();    //竖向表头组成的字符串，用于索引
    String colString=new String();    //横向表头组成的字符串，用于索引

    String[] second=new String[100];    //用于存放分隔后的文法
    int Sentence_Num=0;     //存放行的个数（考虑到产生完成的Split数组行数是final属性的变量）
    int[] MemberFirstDone=new int[100];  //存放数值，数值为1时代表其对应的非终结符first集已完善
    int []MemberFollowDone=new int[100]; //存放数值，数值为1时代表其对应的非终结符follow集已经完善
    Map memMap=new HashMap();          //非终结符哈希Map，用于索引
    Map firstMap=new HashMap();       //终结符哈希Map，用于索引

    char[] row=new char[100];          //竖表头字符数组
    char[] col=new char[100];          //横表头字符数组


    class Own {
        String Sen;
        int PreNum=0;
        String[] Pre = new String[50];
    }

    class Sentence{
        Map OwnSet = new HashMap();
      String No;
      int OwnNum=0;
      Own[] OwnDetail= new Own[50];
    }

    int LALRNum=0;
    Sentence[] LALRProject = new Sentence[50];
    int LR1Num = 0;
    Sentence[] LR1Project = new Sentence[50];
    void CLASSPRINT()
    {
System.out.println("--------------------------------------------------------------");
       for(int i=0;i<LALRNum;i++)
       {
           System.out.println("***第"+i+"个项目集族***");
           System.out.println("R"+LALRProject[i].No);
           System.out.println(LALRProject[i].OwnSet);
           for(int j=0;j<LALRProject[i].OwnNum;j++)
           {
               System.out.print(LALRProject[i].OwnDetail[j].Sen+" ");
               for(int k=0;k<LALRProject[i].OwnDetail[j].PreNum;k++)
                   System.out.print(LALRProject[i].OwnDetail[j].Pre[k]+" ");
               System.out.print("\n");
           }
           System.out.println("************");
       }
        System.out.println("--------------------------------------------------------------");
    }
    Map testaSA =new HashMap();
 void transform()
 {
     LALRNum=0;
    for(int i=0;i<ProjectSetNum;i++)
    {
        LALRNum++;
        System.out.println("i:"+i+"  TRANSLATELALRProject[i]:"+LALRProject[i]);
       LALRProject[i] = new Sentence();
       LALRProject[i].No=String.valueOf(i);
        for(int j=0;j<ProjectSoloNum[i];j++)
        {
            if(!LALRProject[i].OwnSet.containsKey(ProjectSet[i][j]))
            {
                LALRProject[i].OwnSet.put(ProjectSet[i][j],LALRProject[i].OwnNum);
                LALRProject[i].OwnDetail[LALRProject[i].OwnNum] = new Own();
                LALRProject[i].OwnDetail[LALRProject[i].OwnNum].Sen=ProjectSet[i][j];
                LALRProject[i].OwnDetail[LALRProject[i].OwnNum].Pre[LALRProject[i].OwnDetail[LALRProject[i].OwnNum].PreNum++]=Predicate[i][j];
                LALRProject[i].OwnNum++;
            }
            else{
                int Position = (int)LALRProject[i].OwnSet.get(ProjectSet[i][j]);
               LALRProject[i].OwnDetail[Position].Pre[LALRProject[i].OwnDetail[Position].PreNum++]=Predicate[i][j];
            }
            CLASSPRINT();
        }
    }
    LR1Num = LALRNum;
    LR1Project = LALRProject.clone();
 }
boolean EqualOrInvolve(int MAIN,int Com)
{
    System.out.println("MAIN:"+MAIN+" Com:"+Com+"  LALRProject[MAIN].OwnNum:"+LALRProject[MAIN].OwnNum+" LALRProject[Com].OwnNum:"+LALRProject[Com].OwnNum);
    if(LALRProject[MAIN].OwnNum==LALRProject[Com].OwnNum)
    {
        for(int i=0;i<LALRProject[Com].OwnNum;i++)
        {
            if(!LALRProject[MAIN].OwnSet.containsKey(LALRProject[Com].OwnDetail[i].Sen))
                return false;
        }
        return true;
    }
    else
    return false;
}


boolean NotIn(String[] TA,int TN,String Insert)
{
    for(int i=0;i<TN;i++)
    {
        if(TA[i].equals(Insert))
            return false;
    }
    return true;
}
int Mnum = 0;
 int[][] Mlist = new int[100][2];

 void Melt()
 {

    int SenIndex=0;
    while(SenIndex<LALRNum)
    {
        int ComIndex=SenIndex+1;
        while(ComIndex<LALRNum)
        {
            System.out.println("EqualOrInvolve(SenIndex,ComIndex):"+EqualOrInvolve(SenIndex,ComIndex));
            if(EqualOrInvolve(SenIndex,ComIndex))
            {
                Mlist[Mnum][0]=ComIndex;
                Mlist[Mnum++][1]=SenIndex;
                StringBuffer Alter=new StringBuffer("");
                Alter.append( LALRProject[SenIndex].No+"+"+ LALRProject[ComIndex].No);
                LALRProject[SenIndex].No=Alter.toString();
                for(int i=0;i<LALRProject[ComIndex].OwnNum;i++)
                {
                    int Pos = (int)LALRProject[ComIndex].OwnSet.get(LALRProject[ComIndex].OwnDetail[i].Sen);
                   for(int j=0;j<LALRProject[ComIndex].OwnDetail[i].PreNum;j++)
                   {
                       System.out.println("i:"+i+ "  j:"+j+"  LALRProject[SenIndex]:"+ LALRProject[SenIndex]);
                       if(
                               NotIn( LALRProject[SenIndex].OwnDetail[Pos].Pre,LALRProject[SenIndex].OwnDetail[Pos].PreNum,LALRProject[ComIndex].OwnDetail[i].Pre[j]))
                       LALRProject[SenIndex].OwnDetail[Pos].Pre[LALRProject[SenIndex].OwnDetail[Pos].PreNum++]=LALRProject[ComIndex].OwnDetail[i].Pre[j];
                   }
                }
                for(int i=ComIndex;i<LALRNum-1;i++)
                {
                    LALRProject[i]=LALRProject[i+1];
                }
                LALRNum--;
            }
            else{
                ComIndex++;
            }
        }
        SenIndex++;
    }
 }
    /*
    判断函数
    判断整体的first集是否完善(也就是说，所有的非终结符first集完善后方为完善)
     */
    boolean whetherFirstComplete(){
        for(int i=1;i<=indexM;i++)
        {
            System.out.println("FirstLength[i]:"+FirstLength[i]);
            //if(FirstLength[i]==0)
            //    return false;
                if(MemberFirstDone[i]==0)  //一旦first集中存在暂存的非终结符，即为不完善
                    return false;
        }
        return true;
    }


    /*
    判断函数
    判断特定下标index的first集是否完善
     */
    int whetherSoloFirstComplete(int index){
        //System.out.println(first[index].length);
        System.out.println(index+"            "+"FirstLength[index]:"+FirstLength[index]);
       if(FirstLength[index]==0)
           return 0;
        for(int k=0;k<FirstLength[index];k++)
        {

            //System.out.print("$:"+first[index][k]+" ");
            if(first[index][k].charAt(0)<='Z'&&first[index][k].charAt(0)>='A')
                return 0;
        }
        return 1;
    }



    /*
    输出函数
    对当前所有first集进行统一输出
     */
    void FirstPrint(){
        System.out.println("---------------------------------");
        for(int i=1;i<=indexM;i++)
        {
            for(int j=0;j<FirstLength[i];j++)
                System.out.print(first[i][j]+" ");
            System.out.println(" ");

        }
        System.out.println("---------------------------------");
    }


    void ProjectSelectPrint(){
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        for(int i=0;i<Sentence_Num;i++)
        {
            for(int j=0;j<ProjectSelectNum[i];j++)
            {
                System.out.print(ProjectSelect[i][j]+" ");
            }
            System.out.println(" ");
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }

    void ProjectPrint(){
        System.out.println(".......................");
        for(int i=0;i<ProjectNum;i++)
            System.out.println(Project[i]);
        System.out.println(".......................");
    }

    void ProjectSetPrint(){
        System.out.println("////////////////////////////");
        for(int i=0;i<ProjectSetNum;i++)
        {
            System.out.print(i+":  ");
            for(int j=0;j<ProjectSoloNum[i];j++)
            {
                System.out.print(ProjectSet[i][j]+" ");
            }
            System.out.println(" ");
        }
        System.out.println("////////////////////////////");
    }

    void LR1ProjectSetPrint(){
        System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        for(int i=0;i<ProjectSetNum;i++)
        {
            System.out.println("I"+i+":");
            for(int j=0;j<ProjectSoloNum[i];j++)
            {
                System.out.println(ProjectSet[i][j]+" "+Predicate[i][j]);
            }
        }
        System.out.println(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
    }

    /*
    后台处理函数
    对文法输入框的内容进行分割处理
    example: A->*AV|￥|%
    转化为数组为  A *AV ￥ %
     */
    void SentenceSplit(String a)
    {
        int rowC=0;
        for(int i=0;i<FirstLength.length;i++)
        {
            FirstLength[i]=0;
            FollowLength[i]=0;
        }

        second=a.split("\n");
        System.out.println(Sentence_Num);
        for(int i=0;i<second.length;i++)
        {
            System.out.println("System.out.println(Sentence_Num);"+Sentence_Num+" second[i]:"+second[i]);
            var tempelate=second[i].split("->|\\|");   //正则表达式，通过"->"和"|"进行分割
            //System.out.println(" ");
            SplitPrint();
            if(i==0)
            {
                Sentence_Num++;
                for(int k=0;k<tempelate.length;k++)
                {
                    Split[rowC][Split_Length[rowC]]=tempelate[k];
                    Split_Length[rowC]++;
                }
                continue;
            }
            if(tempelate[0].equals(Split[rowC][0]))
            {
                for(int k=1;k<tempelate.length;k++)
                {
                    Split[rowC][Split_Length[rowC]]=tempelate[k];
                    Split_Length[rowC]++;
                }
            }
            else {
                Sentence_Num++;
                rowC++;
                for(int k=0;k<tempelate.length;k++)
                {
                    Split[rowC][Split_Length[rowC]]=tempelate[k];
                    Split_Length[rowC]++;
                }
            }
        }
    }

    /*
    输出函数
    对当前分割结果数组Split进行输出
     */
    void SplitPrint()
    {
        System.out.println("``````````````````````````````");
        for(int i=0;i<Sentence_Num;i++)
        {
            for(int j=0;j<Split_Length[i];j++)
            {
                System.out.print(Split[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.println("``````````````````````````````");
    }

    /*
    判断函数
    判断当前文法中是否有左递归
     */
    boolean whetherLeft()
    {

        for(int i=0;i<Sentence_Num;i++) {
            for (int j = 1; j < Split_Length[i]; j++) {
                System.out.println("Split[i][j].charAt(0):"+Split[i][j].charAt(0)+" Split[i][0].charAt(0):"+Split[i][0].charAt(0));
                if(Split[i][j].charAt(0)==Split[i][0].charAt(0))   //一旦发现产生式中出现第一个字符与非终结符相同，则有左递归
                {
                    LeftTargetX=i;     //左递归所处的行数
                    LeftTargetY=j;     //左递归所处的列数
                    return true;
                }
            }
        }
        LeftTargetX=-1;
        LeftTargetY=-1;
        return false;
    }




    void FirstGenerate()
    {
        //对输入文法进行分割
        System.out.println("是否有左递归:"+whetherLeft());

        for(int i=0;i<Sentence_Num;i++)
        {
            for(int j=1;j<Split_Length[i];j++)
            {
                StringBuffer SentenceAppend=new StringBuffer();
                SentenceAppend.append(Split[i][0]+"->"+Split[i][j]);
                SentenceMap.put(SentenceAppend.toString(),SentenceArrayNum);
                SentenceArray[SentenceArrayNum++]=SentenceAppend.toString();
            }

        }
        //如果需要搞回左递归，那就添加在这里
        int indexQ=0;
        for(int i=0;i<Sentence_Num;i++)          //将当前所有非终结符加入成员哈希Map用于后续索引
        {
            if(!Arrays.asList(member).contains(Split[i][0]))
                member[indexM++]=Split[i][0];
            memMap.put(Split[i][0],indexM);     //字典添加，方便后续非终结符下标查找
        }
        for(int i=0;i<Sentence_Num;i++)
        {
            System.out.println("iiii:"+Split[i][0]);
            System.out.println(memMap);
            indexQ=(int)memMap.get(Split[i][0]);    //获取每个产生式中的左侧非终结符的下标
            System.out.println("indexQ:"+Split_Length[i]);
            for(int j=1;j<Split_Length[i];j++){
                System.out.println("Split[i][j]:"+Split[i][j]);
                first[indexQ][FirstLength[indexQ]++]=String.valueOf(Split[i][j].charAt(0));
                System.out.println("FirstLength[indexQ]:"+FirstLength[indexQ]);
            }


            System.out.print("\n");
        }

        for(int i=1;i<=indexM;i++)
        {
            //System.out.println("whetherSoloFirstComplete(i)"+whetherSoloFirstComplete(i));
            MemberFirstDone[i]=whetherSoloFirstComplete(i);
        }
        for(int i=1;i<=indexM;i++)
        {
            System.out.print(MemberFirstDone[i]+" ");
        }
        System.out.println("cao:"+whetherFirstComplete());
        while(!whetherFirstComplete())      //在未所有非终结符的first集都完善的情况下
        {

            for(int i=1;i<=indexM;i++)
            {
                System.out.print(MemberFirstDone[i]+" ");
            }
            int indexG=0;

            for(int i=1;i<= indexM;i++)
            {
               // System.out.println("FirstLength[i]:"+FirstLength[i]);
                for(int j=0;j<FirstLength[i];j++)
                {
                    int indexKK=0;
                    System.out.println("i:"+i+" j:"+j+" first[i][j].charAt(0):"+first[i][j].charAt(0));
                    if(first[i][j].charAt(0)<='Z'&&first[i][j].charAt(0)>='A')   //如果产生式首字符为非终结符的话，进行处理
                    {
                        indexG=(int)memMap.get(first[i][j]);
                        System.out.println("indexG:"+indexG);
                        for(int u=j;u<FirstLength[i];u++)
                            first[i][u]=first[i][u+1];
                        FirstLength[i]--;
                        for(int u=indexKK;u<FirstLength[indexG];u++)
                        {
                            if(!Arrays.asList(first[i]).contains(first[indexG][u]))
                                first[i][FirstLength[i]++]=first[indexG][u];
                        }
                    }
                }
            }
            for(int i=1;i<=indexM;i++) {
                //每次进行完以后对first完善度进行一个更新{
                MemberFirstDone[i]=whetherSoloFirstComplete(i);
            }

            FirstPrint();
            break;
        }
        FirstPrint();
        System.out.println("first集是否完成:"+whetherFirstComplete());
    }

    /*
    后台功能函数
    first集与follow集的产生
     */

    int CreateSoloClosureSet(int currentSet,int currentIndex,int currentI)
    {
        while(currentIndex!=ProjectSoloNum[currentSet])
        {
            ProjectSetPrint();
            System.out.println("currentSet:"+currentSet+" currentIndex:"+currentIndex);
            System.out.println("ProjectSet[currentSet][currentIndex]:"+ProjectSet[currentSet][currentIndex]);
            int indexDot=ProjectSet[currentSet][currentIndex].indexOf("·");
            if(indexDot!=ProjectSet[currentSet][currentIndex].length()-1)
            {
                if(ProjectSet[currentSet][currentIndex].charAt(indexDot+1)>='A'&&ProjectSet[currentSet][currentIndex].charAt(indexDot+1)<='Z')
                {

                    int indexN=(int)memMap.get(String.valueOf(ProjectSet[currentSet][currentIndex].charAt(indexDot+1)))-1;

                    ProjectSetPrint();
                    if(indexDot+2==ProjectSet[currentSet][currentIndex].length())
                    {
                        for(int i=0;i<ProjectSelectNum[indexN];i++) {
                            ProjectSet[currentSet][ProjectSoloNum[currentSet]] = ProjectSelect[indexN][i];
                            Predicate[currentSet][ProjectSoloNum[currentSet]] = Predicate[currentSet][currentIndex];
                            if(!WhetherProjectInSolo(currentSet,ProjectSoloNum[currentSet]))
                                ProjectSoloNum[currentSet]++;
                        }
                    }
                    else{
                        char LaterKeySolo=ProjectSet[currentSet][currentIndex].charAt(indexDot+2);
                        if(LaterKeySolo>='A'&&LaterKeySolo<='Z')
                        {
                            int NonLocate=(int)memMap.get(String.valueOf(LaterKeySolo));
                            for(int i=0;i<ProjectSelectNum[indexN];i++) {
                                for(int j=0;j<FirstLength[NonLocate];j++)
                                {
                                    if(first[NonLocate][j].equals("ε"))
                                    {
                                        if(indexDot+3==ProjectSet[currentSet][currentIndex].length())
                                        {
                                            for(int h=0;h<ProjectSelectNum[indexN];h++) {
                                                ProjectSet[currentSet][ProjectSoloNum[currentSet]] = ProjectSelect[indexN][h];
                                                Predicate[currentSet][ProjectSoloNum[currentSet]] = Predicate[currentSet][currentIndex];
                                                if(!WhetherProjectInSolo(currentSet,ProjectSoloNum[currentSet]))
                                                    ProjectSoloNum[currentSet]++;
                                            }
                                        }
                                        else{
                                            if(ProjectSet[currentSet][currentIndex].charAt(indexDot+3)>'Z'||ProjectSet[currentSet][currentIndex].charAt(indexDot+3)<'A')
                                            {
                                                for(int h=0;h<ProjectSelectNum[indexN];h++) {
                                                    ProjectSet[currentSet][ProjectSoloNum[currentSet]] = ProjectSelect[indexN][h];
                                                    Predicate[currentSet][ProjectSoloNum[currentSet]] = String.valueOf(ProjectSet[currentSet][currentIndex].charAt(indexDot+3));
                                                    if(!WhetherProjectInSolo(currentSet,ProjectSoloNum[currentSet]))
                                                        ProjectSoloNum[currentSet]++;
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        ProjectSelectPrint();
                                        System.out.println("ProjectSelect[indexN][i]:" + ProjectSelect[indexN][i]);
                                        int a;  //消除波浪线
                                        ProjectSet[currentSet][ProjectSoloNum[currentSet]] = ProjectSelect[indexN][i];
                                        Predicate[currentSet][ProjectSoloNum[currentSet]] = first[NonLocate][j];
                                        if (!WhetherProjectInSolo(currentSet, ProjectSoloNum[currentSet]))
                                            ProjectSoloNum[currentSet]++;
                                    }
                                }
                            }
                        }
                        else{
                            for(int i=0;i<ProjectSelectNum[indexN];i++) {
                                ProjectSet[currentSet][ProjectSoloNum[currentSet]] = ProjectSelect[indexN][i];
                                Predicate[currentSet][ProjectSoloNum[currentSet]] = String.valueOf(LaterKeySolo);
                                if (!WhetherProjectInSolo(currentSet, ProjectSoloNum[currentSet]))
                                    ProjectSoloNum[currentSet]++;
                            }
                        }
                    }
                }
                //PredicatePrint();
                currentIndex++;
            }
            else if(indexDot==ProjectSet[currentSet][currentIndex].length()-1)
                currentIndex++;
        }
        return currentI;
    }


    int WhetherCacheInGroup(int currentSet,int currentIndex){
        for(int i=0;i<ProjectSetNum;i++)
        {
            if(ProjectSoloNum[currentSet]==ProjectSoloNum[i])        //首先要求可疑项与比较项长度相等
            {
                if(Arrays.equals(ProjectSet[currentSet],0,ProjectSoloNum[currentSet],ProjectSet[i],0,ProjectSoloNum[i])&&Arrays.equals(Predicate[currentSet],0,ProjectSoloNum[currentSet],Predicate[i],0,ProjectSoloNum[i]))   //其次要求可疑项元素（排序后）与比较项元素完全一致
                    return i;
            }
        }
        return -1;
    }
    void SetSpecialSort(int currentSet,int from,int to)
    {
        System.out.println("Set排序*1");
        for(int i=from;i<to-1;i++)
        {
            for(int j=i;j<to;j++)
            {
                if(ProjectSet[currentSet][i].compareTo(ProjectSet[currentSet][j])==1)
                {
                    String change=new String();
                    int changeN=0;
                    String changeA=new String();
                    change=ProjectSet[currentSet][i];
                    ProjectSet[currentSet][i]=ProjectSet[currentSet][j];
                    ProjectSet[currentSet][j]=change;

                    changeA=Predicate[currentSet][i];
                    Predicate[currentSet][i]= Predicate[currentSet][j];
                    Predicate[currentSet][j]=changeA;

                    changeN=PredicateNum[currentSet][i];
                    PredicateNum[currentSet][i]=PredicateNum[currentSet][j];
                    PredicateNum[currentSet][j]=changeN;
                }
            }
        }
    }

    void PredicatePrint()
    {

        System.out.println("+++++++++++++++++++++++++");
        for(int i=0;i<ProjectSetNum;i++)
        {
            System.out.print(i+": ");
            for(int j=0;j<ProjectSoloNum[i];j++)
            {
                System.out.print(Predicate[i][j]+" ");
            }
            System.out.println(" ");
        }
        System.out.println("+++++++++++++++++++++++++");
    }

    void ActionAndGotoPrint()
    {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.print(" :");
        for(int i=0;i<ActionNum;i++)
            System.out.print(ActionArray[i]+" ");
        System.out.print("|");
        for(int j=0;j<GotoNum;j++)
            System.out.print(GotoArray[j]+" ");
        System.out.print("\n");
        for(int i=0;i<ProjectSetNum;i++)
        {
            System.out.print(i+":");
            for(int j=0;j<ActionNum;j++)
                System.out.print(ActionInInteger[i][j]+" ");
            System.out.print("|");
            for(int j=0;j<GotoNum;j++)
                System.out.print(GOTOInInteger[i][j]+" ");
            System.out.print("\n");
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }


    void ActionAndGotoLabelPrint()
    {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.print(" :");
        for(int i=0;i<ActionNum;i++)
            System.out.print(ActionArray[i]+" ");
        System.out.print("|");
        for(int j=0;j<GotoNum;j++)
            System.out.print(GotoArray[j]+" ");
        System.out.print("\n");
        for(int i=0;i<ProjectSetNum;i++)
        {
            System.out.print(i+":");
            for(int j=0;j<ActionNum;j++)
            {
                if(Action[i][j]==null)
                    System.out.print("EM"+" ");
                else System.out.print(Action[i][j]+" ");
            }
            System.out.print("|");
            for(int j=0;j<GotoNum;j++)
                System.out.print(GOTOInInteger[i][j]+" ");
            System.out.print("\n");
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    String transfor(Stack d)
    {
        Stack c=(Stack)d.clone();
        StringBuffer result=new StringBuffer();
        while(!c.empty())
        {
            result.append(c.peek());
            c.pop();
        }
        return result.toString();
    }
    String transforInt(Stack d)
    {
        Stack c=(Stack)d.clone();
        StringBuffer result=new StringBuffer();
        Object[] StackC=c.toArray();
        for(int i=0;i<StackC.length;i++)
            result.append(StackC[i]);
        return result.toString();
    }

    int InsertInActionOrGoto(char FirstKey){
        if(FirstKey<='Z'&&FirstKey>='A')
        {
            if(!GotoMap.containsKey(String.valueOf(FirstKey)))
            {
                GotoMap.put(String.valueOf(FirstKey),GotoNum);
                GotoArray[GotoNum++]=String.valueOf(FirstKey);
                System.out.println("FirstKey:"+FirstKey+" 是否存在:"+GotoMap.containsKey(String.valueOf(FirstKey))+" GotoNum:"+GotoNum);
            }
            return 1;
        }
        else{
            if(!ActionMap.containsKey(String.valueOf(FirstKey)))
            {
                ActionMap.put(String.valueOf(FirstKey),ActionNum);
                ActionArray[ActionNum++]=String.valueOf(FirstKey);
                System.out.println("FirstKey:"+FirstKey+" 是否存在:"+ActionMap.containsKey(String.valueOf(FirstKey))+" ActionNum:"+ActionNum);
            }
            return 2;
        }
    }

    void LR0LabelCreate(){
        for(int i=0;i<ProjectSetNum;i++)
        {
            for(int j=0;j<ProjectSoloNum[i];j++)
            {
                int dotCheckVice=ProjectSet[i][j].indexOf("·");
                if(dotCheckVice==ProjectSet[i][j].length()-1&&ProjectSet[i][j].length()!=4)
                {
                    StringBuffer SentenceSearch=new StringBuffer(ProjectSet[i][j]);
                    SentenceSearch.deleteCharAt(SentenceSearch.length()-1);
                    System.out.println(SentenceMap);
                    System.out.println("SentenceSearch.toString():"+SentenceSearch.toString());
                    int SentenceLocate=(int)SentenceMap.get(SentenceSearch.toString());
                    int backLocate=(int)ActionMap.get(Predicate[i][j]);
                    StringBuffer ActionAppend=new StringBuffer();
                    if(SentenceLocate>=10)
                        ActionAppend.append("R"+(char)(SentenceLocate-10)+'A');
                    else
                        ActionAppend.append("R"+SentenceLocate);
                    Action[i][backLocate]=ActionAppend.toString();
                }
            }
            Action[1][ActionNum-1]="acc";
            for(int j=0;j<ActionNum;j++)
            {
                if(ActionInInteger[i][j]!=0)
                {
                    StringBuffer ActionAppend=new StringBuffer();
                    if(ActionInInteger[i][j]>=10)
                        ActionAppend.append("S"+(char)(ActionInInteger[i][j]-10+'A'));
                    else
                        ActionAppend.append("S"+ActionInInteger[i][j]);
                    Action[i][j]=ActionAppend.toString();
                }
            }
        }
    }
    boolean WhetherProjectInSolo(int currentSet,int currentIndex)
    {
        for(int i=0;i<currentIndex;i++)
        {
            if(ProjectSet[currentSet][i].equals(ProjectSet[currentSet][currentIndex]))
            {
                if(Predicate[currentSet][i].equals(Predicate[currentSet][currentIndex]))
                    return true;
            }
        }
        return false;
    }

    void CreateClosure(String a) {
        SentenceSplit(a);
        FirstGenerate();
        SplitPrint();
        for(int i=0;i<Sentence_Num;i++)          //将当前所有非终结符加入成员哈希Map用于后续索引
        {
            if(!Arrays.asList(member).contains(Split[i][0]))
                member[indexM++]=Split[i][0];
        }
        for (int i = 0; i < Sentence_Num; i++) {
            for (int j = 1; j < Split_Length[i]; j++) {
                for (int k = 0; k <= Split[i][j].length(); k++) {
                    if (k != Split[i][j].length()) {
                        if (!AbleMap.containsKey(String.valueOf(Split[i][j].charAt(k))))
                            InsertInActionOrGoto(Split[i][j].charAt(k));
                    }
                    StringBuffer Pro = new StringBuffer();
                    if(Split[i][j].equals("ε"))
                        Pro.append(Split[i][0] + "->·" );
                    else
                    {
                        Pro.append(Split[i][0] + "->" + Split[i][j]);
                        Pro.insert(3 + k, "·");
                    }
                    if(!Arrays.asList(Project).contains(Pro.toString()))
                        Project[ProjectNum++] = Pro.toString();
                }
                StringBuffer Append = new StringBuffer();
                if(Split[i][j].equals("ε"))
                {
                    Append.append(Split[i][0] + "->·");
                    if(!Arrays.asList(ProjectSelect[i]).contains(Append.toString()))
                        ProjectSelect[i][ProjectSelectNum[i]++] = Append.toString();
                }
                else {
                    Append.append(Split[i][0] + "->·" + Split[i][j]);
                    if(!Arrays.asList(ProjectSelect[i]).contains(Append.toString()))
                        ProjectSelect[i][ProjectSelectNum[i]++] = Append.toString();
                    System.out.println(ProjectSetNum+"And"+ProjectSelect[2][1]);
                }
            }
        }
        ActionMap.put("#",ActionNum);
        ActionArray[ActionNum++]="#";
        //项目族初始化
        int currentIndex = 0;
        int currentSet = 0;
        int preSet = -1;
        ProjectPrint();
        ProjectSet[currentSet][0] = Project[0];
        Predicate[0][0]="#";
        PredicatePrint();
        FirstPrint();
        ProjectSoloNum[0]=1;
        ProjectSetNum = 1;
        CreateSoloClosureSet(currentSet, currentIndex,0);
        currentSet++;
        SetSpecialSort(currentSet, 0, ProjectSoloNum[currentSet]);

        int indexIN = 0;

        while (indexIN != ProjectSetNum) {
            int i=0;
            while(i < ProjectSoloNum[indexIN]) {
                currentIndex = 0;
                int indexX=indexIN;
                int DotCheck=ProjectSet[indexIN][i].indexOf("·");
                if(DotCheck==ProjectSet[indexIN][i].length()-1)
                {
                    i++;
                    continue;
                }
                char FirstKey=ProjectSet[indexIN][i].charAt(DotCheck+1);
                int whetherActionOrGoto=-1;
                whetherActionOrGoto =InsertInActionOrGoto(FirstKey);
                char target=ProjectSet[indexIN][i].charAt(DotCheck+1);
                for(int valueI=0;valueI<ProjectSoloNum[indexIN];valueI++)
                {
                    int indexY=valueI;
                    int DotCompare=ProjectSet[indexIN][valueI].indexOf("·");
                    if(DotCompare!=ProjectSet[indexIN][valueI].length()-1)
                    {
                        if(target==ProjectSet[indexIN][valueI].charAt(DotCompare+1))
                        {
                            int indexC = Arrays.asList(Project).indexOf(ProjectSet[indexIN][valueI]) + 1;
                            if(Project[indexC]==null)
                                break;
                            Predicate[currentSet][ProjectSoloNum[currentSet]]=Predicate[indexIN][valueI];
                            ProjectSet[currentSet][ProjectSoloNum[currentSet]]=Project[indexC];

                            if(!WhetherProjectInSolo(currentSet,ProjectSoloNum[currentSet]))
                                ProjectSoloNum[currentSet]++;
                        }
                    }
                }

                i=CreateSoloClosureSet(currentSet, currentIndex,i);
                ProjectSetPrint();
                if(WhetherCacheInGroup(currentSet,currentIndex)==-1)
                {
                    ProjectSetNum++;
                    currentSet++;
                    if(whetherActionOrGoto==1)
                    {
                        int GotoColNum=(int)GotoMap.get(String.valueOf(FirstKey));
                        GOTOInInteger[indexIN][GotoColNum]=currentSet-1;
                    }
                    else if(whetherActionOrGoto==2)
                    {
                        int ActionColNum=(int)ActionMap.get(String.valueOf(FirstKey));
                        ActionInInteger[indexIN][ActionColNum]=currentSet-1;
                    }
                }
                else {
                    int TheSame=WhetherCacheInGroup(currentSet,currentIndex);
                    if(whetherActionOrGoto ==1)
                    {
                        int GotoColNum=(int)GotoMap.get(String.valueOf(FirstKey));
                        GOTOInInteger[indexIN][GotoColNum]=TheSame;
                        int Shit;   //消除讨厌的波浪线
                    }
                    else if(whetherActionOrGoto==2)
                    {
                        int ActionColNum=(int)ActionMap.get(String.valueOf(FirstKey));
                        ActionInInteger[indexIN][ActionColNum]=TheSame;
                    }
                    ProjectSoloNum[currentSet]=0;
                    for(int r=0;r<100;r++)
                        ProjectSet[currentSet][r]="";
                }
                SetSpecialSort(currentSet, 0, ProjectSoloNum[currentSet]);
                i++;
            }
            indexIN++;
        }
        LR0LabelCreate();
        /*ProjectSetPrint();
        ProjectSelectPrint();
        ProjectPrint();
        PredicatePrint();
        ActionAndGotoPrint();

        ActionAndGotoPrint();
        ActionAndGotoLabelPrint();
        LR1ProjectSetPrint();
        System.out.println(SentenceMap);
         */
    }
    void MainProcess()
    {
        int WRONG=-1;
        Stack left=new Stack();
        Stack right=new Stack();
        Stack Condition=new Stack();
        left.push('#');
        right.push('#');
        Condition.push(0);
        String leftString=new String();
        String rightString=new String();
        String ConditionString=new String();

        for(int i=test.length()-1;i>=0;i--)
            right.push(test.charAt(i));
        if(!ActionMap.containsKey(String.valueOf(right.peek())))
        {
            System.out.println("wrongAtFirst");
            WRONG=1;
        }
        else{
            int rightAction=(int)ActionMap.get(String.valueOf(right.peek()));
            while(Action[(int)Condition.peek()][rightAction]!="acc")
            {
                StringBuffer GreatMessage=new StringBuffer();
                Output_total[OutputMessageNum][0]=OutputMessageNum;
                System.out.println("表循环!");
                if(!ActionMap.containsKey(String.valueOf(right.peek())))
                {
                    System.out.println("wrongAtAmong");
                    WRONG=2;
                    break;
                }
                rightAction=(int)ActionMap.get(String.valueOf(right.peek()));
                leftString=transfor(left);
                StringBuffer leftBuffer=new StringBuffer(leftString);
                leftString=leftBuffer.reverse().toString();
                rightString=transfor(right);
                ConditionString=transforInt(Condition);
                Output_total[OutputMessageNum][1]=ConditionString;
                Output_total[OutputMessageNum][2]=leftString;
                Output_total[OutputMessageNum][3]=rightString;
                System.out.println("left:"+leftString);
                System.out.println("right:"+rightString);
                System.out.println("Condition:"+ConditionString);
                if(Action[(int)Condition.peek()][rightAction]==null)
                {
                    WRONG=3;
                    System.out.println("wrong!");
                    break;
                }
                if(Action[(int)Condition.peek()][rightAction].charAt(0)=='S')
                {
                    GreatMessage.append("ACTION["+(int)Condition.peek()+","+rightAction+"]=");
                    GreatMessage.append(Action[(int)Condition.peek()][rightAction]);
                    if(Action[(int)Condition.peek()][rightAction].charAt(1)>='A'&&Action[(int)Condition.peek()][rightAction].charAt(1)<='Z')
                        Condition.push((int)(Action[(int)Condition.peek()][rightAction].charAt(1)-'A'+10));
                    else
                        Condition.push((int)(Action[(int)Condition.peek()][rightAction].charAt(1)-'0'));

                    Output_total[OutputMessageNum++][4]=GreatMessage.toString();
                    left.push(right.peek());
                    right.pop();
                }
                else if(Action[(int)Condition.peek()][rightAction].charAt(0)=='R')
                {
                    String target=new String();
                    if(Action[(int)Condition.peek()][rightAction].charAt(1)>='A'&&Action[(int)Condition.peek()][rightAction].charAt(1)<='Z')
                        target=SentenceArray[(int)(Action[(int)Condition.peek()][rightAction].charAt(1)-'A'+10)];
                    else
                        target=SentenceArray[(int)(Action[(int)Condition.peek()][rightAction].charAt(1)-'0')];
                    GreatMessage.append(Action[(int)Condition.peek()][rightAction]);

                    for(int i=0;i<target.length()-3;i++)
                    {
                        left.pop();
                        Condition.pop();
                    }
                    left.push(target.charAt(0));
                    int GotoN=(int)GotoMap.get(String.valueOf(target.charAt(0)));
                    GreatMessage.append(":依照"+target+"进行规约,GOTO("+(int)Condition.peek()+","+target.charAt(0)+")="+GOTOInInteger[(int)Condition.peek()][GotoN]+"入栈");
                    Output_total[OutputMessageNum++][4]=GreatMessage.toString();
                    Condition.push(GOTOInInteger[(int)Condition.peek()][GotoN]);
                }
            }
        }
        System.out.println("完成你大爷!");
        leftString=transfor(left);
        StringBuffer leftBuffer=new StringBuffer(leftString);
        leftString=leftBuffer.reverse().toString();
        rightString=transfor(right);
        ConditionString=transforInt(Condition);

        System.out.println("Condition:"+ ConditionString);
        System.out.println("left:"+leftString);
        System.out.println("right:"+rightString);
        Output_total[OutputMessageNum][0]=OutputMessageNum;
        Output_total[OutputMessageNum][1]=ConditionString;
        Output_total[OutputMessageNum][2]=leftString;
        Output_total[OutputMessageNum][3]=rightString;
        if(WRONG==1)
            Output_total[OutputMessageNum++][4]="Error01,出错：未识别首终结符符，规约失败";
        else if(WRONG==2)
            Output_total[OutputMessageNum++][4]="Error02,出错：未识别终结符，规约失败";
        else if(WRONG==3)
            Output_total[OutputMessageNum++][4]="Error03,出错：对应的Action分析表为空，规约失败";
        else
            Output_total[OutputMessageNum++][4]="Acc:分析成功";
    }
    public static void main(String[] args){
        LALR play=new LALR();
        GUI ui=new GUI();
        ui.MainFunction();


    }
}
