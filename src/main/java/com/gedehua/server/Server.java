package com.gedehua.server;

import com.gedehua.POJO.Student;
import com.gedehua.Scanning;
import com.gedehua.dao.Servive;
import com.gedehua.utils.Init;
import com.gedehua.utils.inisys;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Scanning sc = new Scanning();
        List<String> list = sc.searchClass();
        List<Student> list1 = new ArrayList<>();
        for (String s : list){
            Class cls = Class.forName(s);
            if (cls.isAnnotationPresent(inisys.class)) {
                Init ins = (Init) cls.newInstance();
                Method init = cls.getMethod("init");
                list1 = (List<Student>)init.invoke(ins);
               // System.out.println(list1);
            }
        }
        Servive servive = new Servive();
        servive.insert(list1);
        ServerSocket ss = new ServerSocket(6666); // 监听指定端口
        System.out.println("com.gedehua.server is running...");
        for (;;) {
            Socket sock = ss.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            Thread t = new Handler(sock);
            t.start();
        }
    }
}

class Handler extends Thread {
    Socket sock;

    public Handler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try (InputStream input = this.sock.getInputStream()) {
            try (OutputStream output = this.sock.getOutputStream()) {
                handle(input, output);
            }
        } catch (Exception e) {
            try {
                this.sock.close();
            } catch (IOException ioe) {
            }
            System.out.println("com.gedehua.client disconnected.");
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException, SQLException, ClassNotFoundException {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        for (;;) {
            String s = reader.readLine();
            if (s.equals("bye")) {
                writer.write("bye\n");
                writer.flush();
                break;
            }
            else if(s.equals("listall")){
                //System.out.println("listall");
                Servive servive = new Servive();
                List<Student> listall = servive.listall();
                //FileWriter fileWriter = new FileWriter("src/stuinfo.txt");
                OutputStream outputStream = new FileOutputStream("D:\\idea_prj\\cs\\src\\stuinfo.txt");
                //BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                for(int i = 0;i<listall.size();i++){
                    System.out.println(listall.get(i).toString());
                    writer.write(listall.get(i).toString()+"\n");
                    outputStream.write((listall.get(i).toString()+"\n").getBytes(StandardCharsets.UTF_8));
                }
                outputStream.flush();
                writer.flush();
            }
            else{
                Servive servive = new Servive();
                Student select = servive.select(s);
                System.out.println(select);
                writer.write(select.toString()+"\n");
                writer.write("\n");
                writer.write("\n");
                writer.flush();
            }

        }
    }
}
