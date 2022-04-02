package user;

import com.me.gRPC.User;
import com.me.gRPC.userGrpc;
import io.grpc.stub.StreamObserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserService extends userGrpc.userImplBase {





    @Override
    public void login(User.LoginRequest request, StreamObserver<User.APIResponse> responseObserver) {
        System.out.println("Inside Login");
        HashMap<String ,String> mapI = new HashMap<>();
        mapI.put("sazzad","123");
        mapI.put("hossain","321");


        String username = request.getUsername();
        String password = request.getPassword();
        User.APIResponse.Builder response = User.APIResponse.newBuilder();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest", "root", "3141592654+sazzad@");
            Statement statement = connection.createStatement();
            //String sql = "select * from person where userName = \""+username+"\"";
            ResultSet resultSet = statement.executeQuery("select * from person where userName = \""+username+"\"");
//            String n = resultSet.getString("userName");
//            System.out.println(n);
            boolean b = false;
            while(resultSet.next()) {
                String n = resultSet.getString("userName");
                String p = resultSet.getString("pass");
                //System.out.println(resultSet.getString("userName"));
                b=true;
                if(username.equals(n) && password.equals(p)) {
                    response.setResponseCode(0).setResponseMessage("SUCCESS with db");
                }
                else{
                    response.setResponseCode(0).setResponseMessage("Invalid Password ");
                }
            }
            if(!b){
                response.setResponseCode(0).setResponseMessage("Invalid username");
            }

            System.out.println("successsssssss");
        }catch (Exception e){
            e.printStackTrace();
        }



//        boolean bool1 = mapI.containsKey(username);
//        String bool2 = mapI.get(username);
//        if(bool1 && bool2.equals(password)){
//
//            response.setResponseCode(0).setResponseMessage("SUCCESS");
//
//        }
//        else{
//
//            response.setResponseCode(100).setResponseMessage("Failure");
//
//        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void register(User.RegisterReq request, StreamObserver<User.APIResponse> responseObserver) {
        System.out.println("Inside Register : ");
        String username = request.getUsername();
        String password = request.getPassword();
        User.APIResponse.Builder response = User.APIResponse.newBuilder();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest", "root", "3141592654+sazzad@");
            Statement statement = connection.createStatement();
            //String sql = "select * from person where userName = \""+username+"\"";
            ResultSet resultSet = statement.executeQuery("select * from person where userName = \""+username+"\"");
//            String n = resultSet.getString("userName");
//            System.out.println(n);
            boolean b = false;
            while(resultSet.next()) {
                String n = resultSet.getString("userName");
                String p = resultSet.getString("pass");
                //System.out.println(resultSet.getString("userName"));
                b=true;

                    response.setResponseCode(0).setResponseMessage("Username already Exist");


            }
            if(!b){
                statement.executeUpdate("INSERT INTO person (userName, pass)\n" +
                        "VALUES (\""+username+"\",\""+password+"\")");
                response.setResponseCode(0).setResponseMessage("Register Successful");

            }

            System.out.println("successsssssss");
        }catch (Exception e){
            e.printStackTrace();
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void logout(User.EmptyMessage request, StreamObserver<User.APIResponse> responseObserver) {

    }


}
