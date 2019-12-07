import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.Map.Entry;

import hw2.code2.Tile;

public class halma {
	
	static class Tile{
		int x;
		int y;
		char c='d';
		char area;
		
		public Tile(int x, int y) {
			this.x=x;
			this.y=y;			
		}
		
		public Tile(int x, int y, char c) {
			this.x=x;
			this.y=y;
			this.c=c;
			
			if((x==0 && y==0)|| (x==1 && y==0) || (x==2 && y==0) || (x==3 && y==0) || (x==4 && y==0) || (x==0 && y==1)|| (x==1 && y==1) || (x==2 && y==1) || (x==3 && y==1) || (x==4 && y==1) || (x==0 && y==2)|| (x==1 && y==2) || (x==2 && y==2) || (x==3 && y==2) || (x==0 && y==3)|| (x==1 && y==3) || (x==2 && y==3) || (x==0 && y==4)||(x==1 && y==4))
				this.area='B';
			
			else if((x==11 && y==14)|| (x==11 && y==15) || (x==12 && y==15) || (x==12 && y==14) || (x==12 && y==13) || (x==13 && y==15)|| (x==13 && y==14) || (x==13 && y==13) || (x==13 && y==12) || (x==14 && y==15) || (x==14 && y==14)|| (x==14 && y==13) || (x==14 && y==12) || (x==14 && y==11) || (x==15 && y==15)|| (x==15 && y==14) || (x==15 && y==13) || (x==15 && y==12)||(x==15 && y==11))
				this.area='W';
			
			else this.area='.';
		}
	}
	
	static class ans{
		Tile from= new Tile(-1,-1,'v');
		Tile to = new Tile(-1,-1,'v');
		double best_val;
		ArrayList<Tile> move;
		
		
		public ans(Tile from,Tile to, double best_val) {
			this.from=from;
			this.to=to;
			this.best_val=best_val;
		}
		public ans(Tile from,Tile to, double best_val,ArrayList<Tile> move) {
			this.from=from;
			this.to=to;
			this.best_val=best_val;
			this.move=move;
			
		}
	}
	
	
	static String color="";
	
	static ArrayList<Tile> black= new ArrayList<Tile>() {
		{
		add(new Tile(0,0));
		add(new Tile(0,1));
		add(new Tile(0,2));
		add(new Tile(0,3));
		add(new Tile(0,4));
		add(new Tile(1,1));
		add(new Tile(1,2));
		add(new Tile(1,3));
		add(new Tile(1,4));
		add(new Tile(1,0));
		add(new Tile(2,0));
		add(new Tile(2,1));
		add(new Tile(2,2));
		add(new Tile(2,3));
		add(new Tile(3,0));
		add(new Tile(3,1));
		add(new Tile(3,2));
		add(new Tile(4,0));
		add(new Tile(4,1));
		}
	};
	static ArrayList<Tile> white= new ArrayList<Tile>() {
		{
		add(new Tile(11,14));
		add(new Tile(11,15));
		add(new Tile(12,15));
		add(new Tile(12,14));
		add(new Tile(12,13));
		add(new Tile(13,15));
		add(new Tile(13,14));
		add(new Tile(13,13));
		add(new Tile(13,12));
		add(new Tile(14,15));
		add(new Tile(14,14));
		add(new Tile(14,13));
		add(new Tile(14,12));
		add(new Tile(14,11));
		add(new Tile(15,15));
		add(new Tile(15,14));
		add(new Tile(15,13));
		add(new Tile(15,12));
		add(new Tile(15,11));
		}
	};
	
	
	static ArrayList<Tile> blank= new ArrayList<Tile>();

	public static void main(String[] args) throws IOException {
	    
	      File input =new File("input.txt");
	      File output =new File("output.txt");
	        
	        if (output.exists())
	          {
	              output.delete();
	          }
	    	
	        BufferedReader br=null;
	        FileWriter writer = new FileWriter(output,true); 

	        try{
	        br=new BufferedReader(new FileReader(input));
	        
	            String game= br.readLine();
	            //System.out.println(game);

	            long start_time=System.currentTimeMillis();
	            //System.out.println(start_time);
	            color=br.readLine();
	            //System.out.println(color);
	            
	            char maxPlayer='W';
	        	char minPlayer='B';
	            if(color.equals("BLACK")){
	              maxPlayer='B';
	              minPlayer='W';
	            }

	            float time=Float.parseFloat(br.readLine());
	            //System.out.println(time);

	            char[][] board= new char[16][16];

	            for(int i=0;i<=15;i++){
	              String line = br.readLine();
	              String[] strs = line.trim().split("\\s+");
	                for(int j=0;j<=15;j++){

	                  board[i][j]=line.charAt(j);
	                  //System.out.print(board[i][j]+" ");
	                  
	                  if(board[i][j]=='.') {
	                	  blank.add(new Tile(i,j));
	                  }
	                  

	                }
	                  //System.out.println();
	            }
	            br.close();
	            
	            ArrayList<Tile> comp1= new ArrayList<Tile>();
	            ArrayList<Tile> comp2= new ArrayList<Tile>();
	            if(maxPlayer=='B') {
	            	comp1=white;
	            	comp2=black;
	            }
	            else if(maxPlayer=='W') {
	            	comp1=black;
	            	comp2=white;
	            }
	            
	            int depth=3;
	            if(game.equals("SINGLE")) {
	            	depth =3;
	            }
	            if(game.equals("GAME")) {
	            	depth=3;
	            	if(time < 20.0 && time > 10.0)
	            		depth=2;
	            	if(time<= 10.0)
	            		depth=1;
	            	int count=0;
	            	
	            	for(int i=0;i<16;i++) {
	            		for(int j=0;j<16;j++) {
	            	Tile tile= new Tile(i,j,board[i][j]);
	            	if(tile.c!=maxPlayer)
	            		continue;
	            	if(maxPlayer=='B') {
	            	      	for(Tile t: white) {
	            	      		if(t.x==tile.x && t.y==tile.y)
	            	      			count++;
	            	      	}
	            		}
	            	if(maxPlayer=='W') {
            	      	for(Tile t: black) {
            	      		if(t.x==tile.x && t.y==tile.y)
            	      			count++;
            	      	}
            		}
	            	}
	            }
	            	if(count>=15 && time>=50.0)
	            		depth=5;
	            	
	            }
	 
	            ans res = minimax(depth,maxPlayer,minPlayer,start_time, time,Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true,board,comp1,comp2);

	            
	            
	            boolean adj=false;
	            System.out.println(res.from.y+" "+res.from.x+" "+res.to.y+" "+res.to.x+" "+ res.best_val);
	            
	            //Outputs the best move when there is no jump
	            for(int i=-1;i<=1;i++) {
	            	for(int j=-1;j<=1;j++) {
	            		if(res.from.x+i==res.to.x  && res.from.y+j==res.to.y){
	            			System.out.println("E "+res.from.y+","+res.from.x+" "+res.to.y+","+res.to.x);
	            			writer.write("E "+res.from.y+","+res.from.x+" "+res.to.y+","+res.to.x);
	            			adj=true;
	            		}
	            		
	            	}
	            }
	            
	            
	            
	            int[] row = { -1, 0, 1, 1, -1, -1, 1, 0 };
	        	int[] col = { 0, -1, -1, 1, -1, 1, 0, 1 };
	        	
	        	//Outputs best move if there is a jump
	            if(!adj) {
	            Queue<Tile> queue= new ArrayDeque<>();
	            Tile source = new Tile(res.from.x,res.from.y);
	            queue.add(source);
	            
	            boolean [][]visited= new boolean[16][16];
	            String[][] parent= new String[16][16]; 
	            visited[res.from.x][res.from.y]=true;
	            
	            ArrayList<String> ans= new ArrayList<>();
	            int x1=0, y1=0;
	            while(!queue.isEmpty()){
	            
	                Tile n= queue.poll();
	                int i=n.x;
	                int j=n.y;
	               
	                
	                if(i==res.to.x && j==res.to.y){
	                    ans.add(res.to.y+","+res.to.x);
	                    int c=0;
	                    while(!(i==res.from.x && j== res.from.y)) {
	                    	
	                    	System.out.println("In Loop: "+i+" "+j+" "+ c++);
	                    	ans.add(parent[i][j]);
	                    	String[] arrOfStr = parent[i][j].split(",");
	                    	j=Integer.parseInt(arrOfStr[0]);
	                    	i=Integer.parseInt(arrOfStr[1]);
	                    }
	                    for(String a: ans) {
	                    	System.out.print(a+" ");
	                    }
	                    System.out.println();
	                    break;
	                }
	                
	                for(int k=0;k<8;k++){
	                
	                    x1=i+row[k];
	                    y1=j+col[k];
	                    
	                    if(valid(x1,y1,16,16) && !visited[x1][y1] && board[x1][y1]!='.'){
	                    
	                    	x1=i+row[k]+row[k];
		                    y1=j+col[k]+col[k];
	                        if(valid(x1,y1,16,16) && !visited[x1][y1] && board[x1][y1]=='.'){
	                        	Tile path = new Tile(x1,y1);
	                        	queue.add(path);
	                        	visited[x1][y1]=true;
	                        	parent[x1][y1]=j+","+i;
	                        }
	                    
	                    }
	                
	                }
	            
	            }
	            
	            for(int i1=0;i1<16;i1++) {
	            	for(int j1=0;j1<16;j1++) {
	            		System.out.print(parent[i1][j1]+" ");
	            	}
	            	System.out.println();
	            }
	            Collections.reverse(ans);
	            for(int i=0;i<ans.size()-1;i++) {
	            	System.out.println("J "+ans.get(i)+" "+ans.get(i+1));
	            	writer.write("J "+ans.get(i)+" "+ans.get(i+1));
	            	writer.write("\n");
	            }
	            }
	            

	            writer.close();
	            System.out.println((System.currentTimeMillis()-start_time)/1000F);
	        }

	        catch(Exception e){
	            e.printStackTrace();
	        } 
	  }
	
	//checks validity of a move
	private static boolean valid(int x, int y, int w, int h){  
	    return (x >= 0 && x < w) && (y >= 0 && y < h);   
	    }
	
	
	// checks if the move has reached a terminal state
	public static boolean isTerminal(char player, char[][] board) {
		
		if(player=='W') {
			boolean flag=false, flag1=true;
			for(Tile it:black) {
				if(board[it.x][it.y]=='W')
				flag=true;
			}
			for(Tile it:black) {
				if(board[it.x][it.y]=='.')
				flag1=false;
			}
			if(flag && flag1) {
				return true;
			}
		}
		
		if(player=='B') {
			boolean flag=false, flag1=true;
			for(Tile it:white) {
				if(board[it.x][it.y]=='B')
				flag=true;
			}
			for(Tile it:white) {
				if(board[it.x][it.y]=='.' )
				flag1=false;
			}
			if(flag && flag1) {
				return true;
			}
		}
		return false;
	}
	
	// the main function that is called recursively to apply minimax with alphabeta 
	public static ans minimax(int depth,char maxPlayer, char minPlayer,long start_time,float time, double a, double b, boolean maxing, char[][] board, ArrayList<Tile> comp1, ArrayList<Tile> comp2) {
		char player;
	
		long end_time=System.currentTimeMillis();
		float sec=(end_time-start_time)/1000F;
		
		if(depth==0 || isTerminal(maxPlayer,board) || sec > (time) || sec > 11.0) {
			
			Tile from= new Tile(-2,-2,'q');
			Tile to= new Tile(-2,-2,'q');
			ans ans3= new ans(from,to,distance(maxPlayer,minPlayer,board,comp1,comp2,depth));
			return ans3;
		}
		
		HashMap<Tile,ArrayList<Tile>> moves;
		double best_val;
		Tile from1= new Tile(-3,-3,'p');
		Tile to1= new Tile(-3,-3,'p');
		ArrayList<Tile> move1= new ArrayList<>();
		
		if(maxing) {
			best_val= Double.NEGATIVE_INFINITY;
			moves=getMoves(maxPlayer,board);
			player=maxPlayer;
		}
		else {
			best_val=Double.POSITIVE_INFINITY;
			moves=getMoves(minPlayer,board);
			player=minPlayer;
		}
		
		
		for(HashMap.Entry<Tile,ArrayList<Tile>> move:moves.entrySet()) {
			
			Tile k=move.getKey();
			
				for(Tile t: move.getValue()) {
					
					char piece=board[k.x][k.y];
					board[k.x][k.y]='.';
					board[t.x][t.y]=piece;
									
					ans ans2=minimax(depth-1,maxPlayer,minPlayer,start_time,time,a,b,!maxing,board,comp1,comp2);					
					
					double val=ans2.best_val;
					board[t.x][t.y]='.';
					board[k.x][k.y]=piece;					
					
					if(maxing && val > best_val ) {
						best_val=val;
						from1=k;
						to1=t;
						move1=move.getValue();
						
						a=Math.max(a, val);
					}
					
					if(!maxing && val<best_val) {
						best_val=val;
						from1=k;
						to1=t;
						move1=move.getValue();
						
						b=Math.min(b, val);
					}
					
					if(b<=a) {
						ans ans1= new ans(from1,to1,best_val, move.getValue());
						return ans1;
					}							
				}						
		}
		ans ans1= new ans(from1,to1,best_val,move1);
		return ans1;		
	}
	
	//outputs hashmap of all possible moves from a given tile
	public static  HashMap<Tile,ArrayList<Tile>> getMoves(char player, char[][] board) {
		
		HashMap<Tile,ArrayList<Tile>> move= new HashMap<>();
		
		boolean flag = false;
		//Tile tile= new Tile(-1,-1);
		for(int i1=0;i1<16;i1++) {
		for(int j1=0;j1<16;j1++) {
			Tile current_tile1= new Tile(i1,j1,board[i1][j1]);
		if(player=='W') {
			for(Tile t:white) {
				if(t.x==current_tile1.x && t.y==current_tile1.y && board[t.x][t.y]=='W' /*&& (board[(current_tile1.x)-1][current_tile1.y]=='.'  || board[current_tile1.x][(current_tile1.y)-1]=='.' || board[(current_tile1.x)-1][(current_tile1.y)-1]=='.')*/) {
					flag=true;
					}
			}
		}
		if(player=='B') {
			for(Tile t:black) {
				if(t.x==current_tile1.x && t.y==current_tile1.y && board[t.x][t.y]=='B' /*&& (board[(current_tile1.x)+1][current_tile1.y]=='.'  || board[current_tile1.x][(current_tile1.y)+1]=='.' || board[(current_tile1.x)+1][(current_tile1.y)+1]=='.')*/) {
					flag=true;
				}
			}
		}
		}
		}

		
		boolean flag2=false;
		boolean flag_first=false;
		
		
		if(flag) {
			for(int i1=0;i1<16;i1++) {
				for(int j1=0;j1<16;j1++) {
						
					Tile current_tile1= new Tile(i1,j1,board[i1][j1]);
					if(current_tile1.c!=player)
						continue;
					
					boolean flag11=false;
					if(player=='W') {
						for(Tile t: white) {
							if(current_tile1.x==t.x && current_tile1.y==t.y && board[t.x][t.y]=='W' /*&& (board[(current_tile.x)-1][current_tile.y]=='.'  || board[current_tile.x][(current_tile.y)-1]=='.' || board[(current_tile.x)-1][(current_tile.y)-1]=='.')*/)
								flag11=true;
						}
					}
					if(player=='B') {
						for(Tile t: black) {
							if(current_tile1.x==t.x && current_tile1.y==t.y && board[t.x][t.y]=='B' /*&& (board[(current_tile.x)+1][current_tile.y]=='.'  || board[current_tile.x][(current_tile.y)+1]=='.' || board[(current_tile.x)+1][(current_tile.y)+1]=='.')*/)
								flag11=true;
						}
					}
					if(!flag11)
						continue;
					
					ArrayList<Tile> arr= new ArrayList<Tile>();
					
					arr=get_move_at_tile(current_tile1, player, arr,board,true,0,0);
					Tile first;
					if(arr.size()!=0)
						first = arr.get(0);
					else
						first= new Tile(-10,-10);
					boolean flag_check=true;
					
					boolean flag_t = true;
					ArrayList<Tile> first_option= new ArrayList<Tile>();
					
					
					if(player=='B') {
						ArrayList<Tile> arr_mod= new ArrayList<Tile>(arr);

						Tile store= new Tile(-10,-10);
					for(Tile fi: arr) {
						
						for(Tile t1:black) {
							if(t1.x==fi.x && t1.y==fi.y) {
								flag_t=false;
								break;
							}
						}
						if(flag_t){
							store.x=fi.x;
							store.y=fi.y;
							first_option.add(store);
							move.put(current_tile1, arr_mod);
							flag_first= true;
						}
						else {
							arr_mod.remove(fi);
						}
						
					}
					}
					
					if(player=='W') {
						ArrayList<Tile> arr_mod= new ArrayList<Tile>(arr);

						Tile store= new Tile(-10,-10);
					for(Tile fi: arr) {
						
						for(Tile t1:white) {
							if(t1.x==fi.x && t1.y==fi.y) {
								flag_t=false;
								
								break;
							}
						}
						if(flag_t){
							store.x=fi.x;
							store.y=fi.y;
							first_option.add(store);
							move.put(current_tile1, arr_mod);
							flag_first= true;
						}
						else {
							arr_mod.remove(fi);
						}
						
					}
					}

				}
			}
		}
		//implements additional rules so as to avoid spoiling (1. if moved out of home base, then cannot come back in)
		//(2. if moved into enemy base then cannot come out)
		if(flag && !flag_first) {
			for(int i1=0;i1<16;i1++) {
				for(int j1=0;j1<16;j1++) {
						
					Tile current_tile1= new Tile(i1,j1,board[i1][j1]);
					if(current_tile1.c!=player)
						continue;
					
					boolean flag11=false;
					if(player=='W') {
						for(Tile t: white) {
							if(current_tile1.x==t.x && current_tile1.y==t.y && board[t.x][t.y]=='W' /*&& (board[(current_tile.x)-1][current_tile.y]=='.'  || board[current_tile.x][(current_tile.y)-1]=='.' || board[(current_tile.x)-1][(current_tile.y)-1]=='.')*/)
								flag11=true;
						}
					}
					if(player=='B') {
						for(Tile t: black) {
							if(current_tile1.x==t.x && current_tile1.y==t.y && board[t.x][t.y]=='B' /*&& (board[(current_tile.x)+1][current_tile.y]=='.'  || board[current_tile.x][(current_tile.y)+1]=='.' || board[(current_tile.x)+1][(current_tile.y)+1]=='.')*/)
								flag11=true;
						}
					}
					if(!flag11)
						continue;
					
					ArrayList<Tile> arr= new ArrayList<Tile>();
					ArrayList<Tile> arr_mod= new ArrayList<Tile>();
					ArrayList<Tile> arr_mod1= new ArrayList<Tile>();
					//ArrayList<Tile> jump= new ArrayList<Tile>();
					arr=get_move_at_tile(current_tile1, player, arr,board,true,0,0);
					Tile first;
					if(arr.size()!=0)
						first = arr.get(0);
					else
						first= new Tile(-10,-10);
					boolean flag_check=true;				

					if(player=='B') {         
					
					for(Tile tile:black) {
												
						if(first.x <= tile.x && first.y <= tile.y)
							flag_check=false;
					}
					if(flag_check) {
					move.put(current_tile1,arr);
					flag2=true;
					}
					
					else
					for(Tile t:arr) {
						boolean flag_temp=false;
						for(Tile t1:black) {
							if(t1.x==t.x && t1.y==t.y)
								flag_temp=true;
						}
						if((t.x < current_tile1.x || t.y < current_tile1.y) && flag_temp){
							arr_mod1.add(t);
						}
						else {
							arr_mod.add(t);
						}
						}
					
					}
					if(player=='W') {
						
						for(Tile tile:white) {
							if(first.x >= tile.x && first.y >= tile.y)
								flag_check=false;
						}
						if(flag_check) {
						move.put(current_tile1,arr);
						flag2=true;
						}
						
						else
						for(Tile t:arr) {
							boolean flag_temp=false;
							for(Tile t1:white) {
								if(t1.x==t.x && t1.y==t.y)
									flag_temp=true;
							}

							if((t.x > current_tile1.x || t.y > current_tile1.y) && flag_temp){
								arr_mod1.add(t);
							}
							else {
								arr_mod.add(t);
							}
							}
						
						}
					
					
					if(!flag_check && arr_mod.size()!=0) {
						
						
						flag2=true;
						move.put(current_tile1,arr_mod);
					}

					
				}
			}
			
			}
		
		if(!((flag && flag2) || flag_first))
		for(int i=0;i<16;i++) {
			for(int j=0;j<16;j++) {
				
				Tile current_tile= new Tile(i,j,board[i][j]);
				
				
				if(current_tile.c!=player)
					continue;			
				
				if((flag && flag2) || flag_first)
					continue;
								
				ArrayList<Tile> arr= new ArrayList<Tile>();
				
				arr=get_move_at_tile(current_tile, player, arr,board,true,0,0);
				move.put(current_tile,arr);
			}			
		}
		return move;
	}
	
	//given a tile find the best move possible at that tile by calling recursively
	public static ArrayList<Tile> get_move_at_tile(Tile tile, char player,ArrayList<Tile> moves, char[][] board, boolean adjacent,int number_jump, int maxjump) {
		
		int row= tile.x;
		int col= tile.y;

		
		ArrayList<Tile> valid= new ArrayList<Tile>();
		for(int i=0;i<16;i++) {
			for(int j=0;j<16;j++) {
				valid.add(new Tile(i,j));
			}
		}
		
		if(tile.area!=player) {
			
			if(player=='B') {
				valid.remove(65);
				valid.remove(64);
				valid.remove(50);
				valid.remove(49);
				valid.remove(48);
				valid.remove(35);
				valid.remove(34);
				valid.remove(33);
				valid.remove(32);
				valid.remove(20);
				valid.remove(19);
				valid.remove(18);
				valid.remove(17);
				valid.remove(16);
				valid.remove(4);
				valid.remove(3);
				valid.remove(2);
				valid.remove(1);
				valid.remove(0);
				
			}
			if(player=='W') {
				valid.remove(255);
				valid.remove(254);
				valid.remove(253);
				valid.remove(252);
				valid.remove(251);
				valid.remove(239);
				valid.remove(238);
				valid.remove(237);
				valid.remove(236);
				valid.remove(235);
				valid.remove(223);
				valid.remove(222);
				valid.remove(221);
				valid.remove(220);
				valid.remove(207);
				valid.remove(206);
				valid.remove(205);
				valid.remove(191);
				valid.remove(190);
				
				//System.out.println("i am here");
			}
		}
		if(tile.area!='.' && tile.area!=player) {
			int size=valid.size();
			if(player=='B') {
				for(int i=170;i>=0;i--) {
					valid.remove(i);
				}
				valid.remove(60);valid.remove(59);valid.remove(58);valid.remove(57);valid.remove(56);valid.remove(55);valid.remove(54);valid.remove(53);valid.remove(52);valid.remove(51);valid.remove(50);valid.remove(44);valid.remove(43);valid.remove(42);valid.remove(41);valid.remove(40);valid.remove(39);valid.remove(38);valid.remove(37);valid.remove(36);valid.remove(35);valid.remove(34);valid.remove(29);valid.remove(28);valid.remove(27);valid.remove(26);valid.remove(25);valid.remove(24);valid.remove(23);valid.remove(22);valid.remove(21);valid.remove(20);valid.remove(19);valid.remove(18);valid.remove(14);valid.remove(13);valid.remove(12);valid.remove(11);valid.remove(10);valid.remove(9);valid.remove(8);valid.remove(7);valid.remove(6);valid.remove(5);valid.remove(4);valid.remove(3);valid.remove(2);
			}
			
			if(player=='W') {
				for(int i=size-1;i>=66;i--) {
					valid.remove(i);
				}
				valid.remove(63);valid.remove(62);valid.remove(61);valid.remove(60);valid.remove(59);valid.remove(58);valid.remove(57);valid.remove(56);valid.remove(55);valid.remove(54);valid.remove(53);valid.remove(52);valid.remove(51);valid.remove(47);valid.remove(46);valid.remove(45);valid.remove(44);valid.remove(43);valid.remove(42);valid.remove(41);valid.remove(40);valid.remove(39);valid.remove(38);valid.remove(37);valid.remove(36);valid.remove(31);valid.remove(30);valid.remove(29);valid.remove(28);valid.remove(27);valid.remove(26);valid.remove(25);valid.remove(24);valid.remove(23);valid.remove(22);valid.remove(21);valid.remove(15);valid.remove(14);valid.remove(13);valid.remove(12);valid.remove(11);valid.remove(10);valid.remove(9);valid.remove(8);valid.remove(7);valid.remove(6);valid.remove(5);

			}
			
		}
		try {
		for(int i=-1;i<=1;i++) {
			for(int j=-1;j<=1;j++) {
				
				//c++;
				int row1=row+i;
				int col1=col+j;
				if((row1==row && col1==col) || row1<0 || col1<0 || row1>15 || col1>15)
					continue;
				
				
				Tile tile1 =new Tile(row1,col1,board[row1][col1]);

				boolean flag=false;
				
				for(Tile it:valid) {
					if(it.x==tile1.x && it.y==tile1.y)
					flag=true;
				}
				if(!flag)
					continue;
							
				if(tile1.c=='.') {
				if(adjacent)
					moves.add(tile1);
				continue;
				}

				row1=row1+i;
				col1=col1+j;
				
				if(row1<0 || col1<0 || row1>15 || col1>15)
					continue;

				Tile tile2 =new Tile(row1,col1,board[row1][col1]);
				
				boolean flag2=false;
				for(Tile it:valid) {
					if(it.x==tile2.x && it.y==tile2.y)
					flag2=true;
				}
				
				boolean flag3=false;
				for(Tile it:moves) {
					if(it.x==tile2.x && it.y==tile2.y)
						flag3=true;
				}
				
				if(flag3  || !flag2)
					continue;
				
				
				if(tile2.c=='.') {
					
					number_jump++;
					if(number_jump > maxjump) {
						maxjump=number_jump;
						moves.add(0,tile2);
						
					}
					else
						moves.add(1,tile2);
									
					get_move_at_tile(tile2,player,moves,board,false,number_jump,maxjump);
				}
				
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		

		return moves;
		
	}
	
	public static double point(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
	public static double distance(char player, char opponent, char[][] board, ArrayList<Tile> comp1,ArrayList<Tile> comp2,int depth) {
	
		double score=0.0;
		if(isTerminal(player,board)) {
			score+=100;
		}
		if(isTerminal(opponent,board)) {
			score-=100;
		}
		
		
		if(player=='B'){
		
		
	        for(int i=0;i<16;i++){
	          for(int j=0;j<16;j++){
	            if(board[i][j]=='B'){
	              double a=(i+j-25)/Math.sqrt(2);
	              double b=point(i,j,15,15);
	              if((i==15 && j==10) || (i==10 && j==15))
	            	  score=score-a-b;
	              else
	                  score=score+a-b;
	              
	            }
	            else if(board[i][j]=='W') {
	            	double a=(i+j-5)/Math.sqrt(2);
	            	double b=point(i,j,0,0);
	            	if((i==5 && j==0) || (i==0 && j==5))
	            		score=score-a+b;
	            	else
	            		score=score+a+b;
	            }
	          }
	        }
	      }

	      if(player=='W'){
	    	  
	    	  		
	        for(int i=0;i<16;i++){
	          for(int j=0;j<16;j++){
	            if(board[i][j]=='W'){
	              double a=(i+j-5)/Math.sqrt(2);
	              double b=point(i,j,0,0);
	              if((i==5 && j==0) || (i==0 && j==5))
	            	  score=score+a-b;
	              else
	                score=score-a-b;
	              
	            }
	            else if(board[i][j]=='B') {
	            	double a = (i+j-25)/Math.sqrt(2);
	            	double b=point(i,j,15,15);
	            	if((i==15 && j==10) || (i==10 && j==15))
	            		score=score+a+b;
	            	else
	            		score=score-a+b;
	            }
	          }
	        }
	      }
		
		return score;
	}
	
	
}
