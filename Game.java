package com.projects.mathquiz;


	import java.util.Random;
	import java.util.Scanner;

import com.mysql.jdbc.Driver;

import java.lang.Math;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

	public class Game 
	{
			
			public static void main(String[] args) throws InterruptedException, Throwable {
				Scanner sc  =new Scanner(System.in);
				System.out.println("Enter your name");
				String name=sc.next();
				System.out.println("Challange yourself by mentioning time in seconds");
				int sec=(10*1000);
				Score sr=new Score();
				sr.Score(name,sec);		
				Level1 l=new Level1();
				Level2 l2=new Level2();
				Level3 l3=new Level3();
			
			Runnable r1=()->
			{
					
					l.level1();
					l2.level2();
					l3.level3();
					
			};
			
			Thread t=new Thread(sr);
			Thread t2=new Thread(r1);
			t2.start();
			t.sleep(sec);
			t.start();
			t2.stop();	
			sc.close();
			
			
			}
			}


	class Divisors
	{
		int [] a=new int[9];
		public  Divisors()
		{
		 int num=10;
		 int num2=10;
		 a[0]=10;
			for (int i=1;i<a.length ;i++ ) {
				
				num=num+9*num2;
				a[i]=num;
				num2=num2*10;
			}
		}
		public void divisor(int siz1,int siz2,int val,int val2,int n)
		{
			int res=a[siz1-n];
			int res2=a[siz2-n];
			val=val/res;
			val2=val2/res2;
			Scanner sc=new Scanner(System.in);
			System.out.println(val+"*"+val2+"=");
			int userVal=sc.nextInt();
			if (val*val2==userVal) {
				System.out.println("correct");
				new Score().score(n);
			}
			else{
				System.out.println("wrong");
			}
		}
		public int divisor(int siz1,int n)
		{
			return a[siz1-n];
		}
		
	}
	class Score extends Thread
	{
		private static int scr;
		String name;
		int time;
		int highscore;
		public void Score(String name,int time)
		{
			this.name=name;
			this.time=time;
		}
		public void score(int n)
		{
			scr=scr+n;
		}
		@Override 
		 public void run()
		{
			System.out.println("---------------------------------------------------------------");
				System.out.println("          "+"Name :"+this.name);
				System.out.println("          "+"your Total Score :"+scr);
				System.out.println("          Time Take:"+this.time);
				
				
				Connection CONN=null;
				Statement STMT=null;
				ResultSet RES=null;
				try {
					Driver driverref=new Driver();
					DriverManager.registerDriver(driverref);
//								jdbc:mysql://localhost:3306/Bank_Application?user=root&password=root
					String dburl="jdbc:mysql://localhost:3306/score?user=root&password=root";
					CONN=DriverManager.getConnection(dburl);
					
					String query=" insert into score_details value("+scr+","+this.time+",\'"+this.name+"\') ";
					STMT=CONN.createStatement();
					
					STMT.executeUpdate(query);
					
					query=" select * from score_details where score=(select max(score) from score_details) ";
					
					
					RES=STMT.executeQuery(query);
					
					while(RES.next())
					{
						System.out.println();
						highscore=RES.getInt("score");
						System.out.println("Name :"+RES.getString("name"));
						System.out.println("score :"+highscore);
						System.out.println("duration :"+RES.getInt("time"));
					}
					System.out.println();
					if(highscore>scr)
					{
						System.out.println("you lost the game");
					}
					else if(highscore<scr)
					{
						System.out.println("you are the winner");
					}
					else {
						System.out.println("Draw");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					try {
					if(CONN!=null)
					{
						CONN.close();
						STMT.close();
						RES.close();
					}
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
		}
	}
	class Play extends Answer
	{
		Random rd=new Random();
			public  int plyGame1()
			{		
				return Math.abs(rd.nextInt());
			}
			public int plyGame2()
			{
				return Math.abs(rd.nextInt());
			}
	}
	class Level1 extends Answer
	{
		
		
		
		public  void level1()
		{
			System.out.println("---------------------------------------------------------------");
		System.out.println("Level 1");
		for (int i=0;i<=3; i++) {
		int rand1=0;
		int rand2=0;
		int j=1;
		while(true)
		{
			rand1=(int)(Math.random()*j);
			rand2=(int)(Math.random()*j);
			j++;
			if(rand1!=0&&rand2!=0)
			{
				break;
			}
			
		}
		
		solution (rand1,rand2,1);
		}
		}
		
	}
	
	class Answer
	{
		Score sr=new Score();
		public void solution(int rand1,int rand2,int score)
		{
			Scanner sc=new Scanner(System.in);
			System.out.println(rand1+"*"+rand2+"=");
			int ans=sc.nextInt();
			if(rand1*rand2==ans)
			{
				System.out.println("correct");
				sr.score(score);
			}
			else
			{
				System.out.println("wrong");
			}
		}
	}
	class Level2 extends Play
	{
		Divisors dv=new Divisors();
		Score sr=new Score();
		Scanner sc=new Scanner(System.in);
		public  void level2()
		{
			System.out.println("---------------------------------------------------------------");
			System.out.println("Level 2");
			for (int i=0;i<=3; i++) {
				int num=plyGame1();
				int num2=plyGame2();
			String str=Integer.toString(num);
			String str2=Integer.toString(num2);
			int val=str.length();
			int val2=str2.length();
			
			if (val>=3&&val2>=3) {
					dv.divisor(val,val2,num,num2,3);
			}
			else if(val>=3)
			{
					int res=dv.divisor(val,3);
					num=num/res;
					
					solution (num,num2,2);
			}
			else if(val2>=3)
			{
				int res=dv.divisor(val2,3);
					num2=num2/res;
					solution (num,num2,2);
			}
			else
			{
				solution (num,num2,2);

		}
		}
	}

	}
	



