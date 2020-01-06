import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class sender {
    public static void main(String[] args) throws IOException {
        int number =4;
        DataInputStream inputStream ;
        DataOutputStream outputStream;
        String divisor = new String();
        divisor = "1001";
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket socket = serverSocket.accept();
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        
        File f=new File("sender.txt");     //Creation of File Descriptor for input file
        FileReader fr=new FileReader(f);   //Creation of File Reader object
        BufferedReader br=new BufferedReader(fr);  //Creation of BufferedReader object
        int c = 0;
        int q = 0;
        String[] string = new String[4];

        for (int p = 0;p<4;p++) {


                while( q < 4 && (c = br.read()) != -1)         //Read char by Char
                {
                    string[q] = Integer.toBinaryString(c);
                    q++;
                }
                q=0;

                String total = new String();
                for(int k=0;k<4;k++){
                    total = total + string[k];
                }
                total = total + "000";
                int[] d= new int[4*7+4];
                int m,z[],r[],msb,i,j,k;
                int[] g = new int[4];
                int n = 28;
                m=4;
                for(i=0;i<n+m-1;i++) {
                    d[i] = Character.getNumericValue(total.charAt(i));
                }
                for(j=0;j<m;j++) {
                    g[j]=Character.getNumericValue(divisor.charAt(j));
                }
                r=new int[m+n];
                for(i=0;i<m;i++)
                    r[i]=d[i];
                z=new int[m];
                for(i=0;i<m;i++)
                    z[i]=0;
                for(i=0;i<n;i++)
                {
                    k=0;
                    msb=r[i];
                    for(j=i;j<m+i;j++)
                    {
                        if(msb==0)
                            r[j]=xor(r[j],z[k]);
                        else
                            r[j]=xor(r[j],g[k]);
                        k++;
                    }
                    r[m+i]=d[m+i];
                }
                System.out.print("\nThe code bits added are : ");
                for(i=n;i<n+m-1;i++)
                {
                    d[i]=r[i];
                    System.out.print(d[i]);
                }
                System.out.print("\nThe code data is : ");
                for(i=0;i<n+m-1;i++)
                {
                    System.out.print(d[i]);
                }
                //after making of frame


                Random generate = new Random();
                int repeat = 1;
            while (repeat==1) {
                int random = generate.nextInt(1000);
                int decision = random%2;
                if(decision == 0){
                    String tosend = new String();
                    tosend = "";
                    for(i=0;i<n+m-1;i++)
                    {
                        tosend = tosend + d[i];
                    }
                    outputStream.writeUTF(tosend);
                    String input = new String();
                    input = inputStream.readUTF();
                    if(input.equals("OK")){
                        repeat = 0;
                    }
                }
                else{
                    int decidebit = generate.nextInt(1000);
                    decidebit = decidebit%(n+m);

                    if(d[decidebit] == 0){
                        d[decidebit] = 1;
                    }
                    else{
                        d[decidebit] = 0;
                    }
                    String tosend = new String();
                    tosend = "";
                    for(i=0;i<n+m-1;i++)
                    {
                        tosend = tosend + d[i];
                    }
                    outputStream.writeUTF(tosend);
					 if(d[decidebit] == 0){
                        d[decidebit] = 1;
                    }
                    else{
                        d[decidebit] = 0;
                    }
                    String input = new String();
                    input = inputStream.readUTF();
                    if(input.equals("NAK")){
                        repeat = 1;
                    }

                }
            }


        }
        }
    public static int xor(int x,int y)
    {
        if(x==y)
            return(0);
        else
            return(1);
    }

    }


