package com.gedehua.dao;

import com.gedehua.POJO.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Servive {
    private Connection connection;


    public void insert(List<Student> list) throws SQLException, ClassNotFoundException {
        connection = jdbc.getConnection();
        boolean flag = true;
        for(int i = 0;i<list.size();i++){
            String sql = "insert into studentinfo values(?,?,?)";
            PreparedStatement preparedStatement ;
            if(flag) {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setObject(1,list.get(i).getName());
                preparedStatement.setObject(2,list.get(i).getNo());
                preparedStatement.setObject(3,list.get(i).getScore());
                Student select = select(list.get(i).getName());
                System.out.println(select.getName());
                System.out.println(list.get(i).getName());
                if(!select.getName().equals(list.get(i).getName())){
                    int n = preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
                else {
                    flag =false;
                }
            }

        }

        connection.close();
    }

    public Student select(String name) throws SQLException, ClassNotFoundException {
        connection = jdbc.getConnection();
        String sql = "select *from studentinfo where stuName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();
        Student student = new Student();
        while (resultSet.next()){
            String stNa = resultSet.getString(1);
            String stNo = resultSet.getString(2);
            String score = resultSet.getString(3);
            student = new Student(stNa,stNo,Float.parseFloat(score));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
        return student;
    }

    public List<Student> listall() throws SQLException, ClassNotFoundException {
        connection = jdbc.getConnection();
        String sql = "select *from studentinfo order by score desc";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Student> list = new ArrayList<>();
        while (resultSet.next()){
            String stNa = resultSet.getString(1);
            String stNo = resultSet.getString(2);
            String score = resultSet.getString(3);
            list.add(new Student(stNa,stNo,Float.parseFloat(score)));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return list;
    }
}
