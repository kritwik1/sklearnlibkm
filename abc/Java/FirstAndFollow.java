package doc;


import java.io.*;
import java.util.*;
public class FirstAndFollow {
	
	static char ntermnl[],termnl[];
	static int ntlen,tlen;
	static String grammar[][],fst[],flw[];
	public static void main(String args[]) throws IOException
	{
		String nt,t;
		int i,j,n;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter the no of Production:= ");
		int no = Integer.parseInt(br.readLine());
		System.out.println("Enter the Grammar :=");
		String[] production = new String[no];
		for(int x=0;x<no;x++) {
			production[x]=br.readLine();
		}
		Set<String> NT = new HashSet<String>();
		for(int x=0;x<no;x++) {
			NT.add(production[x].split("#",0)[0]);
		}
		grammar=new String[NT.size()][];
		int e=0;
		for (String nont:NT) {
			int f=0;
			Vector<String> pro= new Vector<String>();
			for(int x=0;x<no;x++) {
				if(production[x].split("#",0)[0].contains(nont)) {
					
					pro.add(production[x].split("#",0)[1]);
				}
			}
			
			grammar[e]=new String[pro.size()];
			for(String s:pro) {
				grammar[e][f++]=s;
			}
			e++;
		}
		ntlen=NT.size();
		ntermnl=new char[ntlen];
		e=0;
		for(String s:NT) {
			ntermnl[e++]= s.charAt(0);
		}
		
		Set<String> terminal = new HashSet<String>();
		for(int x=0;x<grammar.length;x++) {
			for(int y=0;y<grammar[x].length;y++) {
				for(int z=0;z<grammar[x][y].length();z++) {
					if(Character.isLowerCase(grammar[x][y].charAt(z)) || grammar[x][y].charAt(z)=='@') {
						terminal.add(Character.toString(grammar[x][y].charAt(z)));
					}
				}
			}
		}
		tlen = terminal.size();
		termnl = new char[tlen];
		int b=0;
		for(String non:terminal) {
			termnl[b++]=non.charAt(0);
		}
		fst=new String[ntlen];
		for(i=0;i<ntlen;i++)
			fst[i]=first(i);
		System.out.println("First Set");
		for(i=0;i<ntlen;i++)
			System.out.println(removeDuplicates(fst[i]));
		flw=new String[ntlen];
		for(i=0;i<ntlen;i++)
			flw[i]=follow(i);
		System.out.println("Follow Set");
		for(i=0;i<ntlen;i++)
			System.out.println(removeDuplicates(flw[i]));
	}
	static String first(int i)
	{
		int found=0;
		String temp="",str="";
		for(int j=0;j<grammar[i].length;j++) 
		{
			for(int k=0;k<grammar[i][j].length();k++,found=0) 
			{
				for(int l=0;l<ntlen;l++) 
				{
					if(grammar[i][j].charAt(k)==ntermnl[l]) 
					{
						str=first(l);
						if(!(str.length()==1 && str.charAt(0)=='@')) 
							temp=temp+str;
						found=1;
						break;
					}
				}
				if(found==1)
				{
					if(str.contains("@")) 
						continue;
				}
				else 
					temp=temp+grammar[i][j].charAt(k);
				break;
			}
		}
		return temp;	
	}
	static String follow(int i)
	{
		char pro[],chr[];
		String temp="";
		int j,k,l,m,n,found=0;
		if(i==0)
		temp="$";
		for(j=0;j<ntlen;j++)
		{
			for(k=0;k<grammar[j].length;k++) 
			{
				pro=new char[grammar[j][k].length()];
				pro=grammar[j][k].toCharArray();
				for(l=0;l<pro.length;l++) 
				{
					if(pro[l]==ntermnl[i]) 
					{
						if(l==pro.length-1) 
						{
							if(j<i)
								temp=temp+flw[j];
						}
						else
						{
							for(m=0;m<ntlen;m++)
							{
								if(pro[l+1]==ntermnl[m]) 
								{
									chr=new char[fst[m].length()];
									chr=fst[m].toCharArray();
									for(n=0;n<chr.length;n++)
									{
										if(chr[n]=='@') 
										{
											if(l+1==pro.length-1)
												temp=temp+follow(j); 
											else
												temp=temp+follow(m);
										}
										else
											temp=temp+chr[n]; 
									}
									found=1;
								}
							}
							if(found!=1)
								temp=temp+pro[l+1]; 
						}
					}
				}
			}
		}
		return temp;
	}
	static String removeDuplicates(String str)
	{
		int i;
		char ch;
		boolean seen[] = new boolean[256];
		StringBuilder sb = new StringBuilder(seen.length);
		for(i=0;i<str.length();i++)
		{
			ch=str.charAt(i);
			if (!seen[ch])
			{
				seen[ch] = true;
				sb.append(ch);
			}
		}
		return sb.toString();
	}

}
