package util;
import object.User;

import java.io.*;
import java.util.StringTokenizer;

public class UserIO {
    public static User getUser(String email, String path){
        User user = new User();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while((line = br.readLine()) != null){
                StringTokenizer st = new StringTokenizer(line, "\t");
                String fileEmail = st.nextToken();
                if(fileEmail.equals(email)){
                    user.setEmail(fileEmail);
                    user.setFirstName(st.nextToken());
                    user.setLastName(st.nextToken());
                    break;
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return user;
    }

    public static void add(User user, String path){
        try(PrintWriter pw = new PrintWriter(new FileWriter(path, true))){
            pw.println(user.getEmail() + "\t" + user.getFirstName() + "\t" + user.getLastName());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
