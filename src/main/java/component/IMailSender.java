package component; 

/*********************************************
 * IMailSender.java
 * Author: xyk0058
 * Created on: Nov 29, 2016
 ********************************************/

public interface IMailSender {

    /**
     * send file from path
     * @param filePath the file path
     */
    void sendFrom(String filePath);

}
