package com.dpool.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Properties;

import com.dpool.app.util.RSAUtils;

public class EncryptKey {
   private static String keypath="/home/dpool/dpl_app/keys/";
	public static void main(String[] args) throws Exception {
		System.out.println("欢迎使用秘钥加密命令!");
		System.out.println("按以下步骤进行操作:");
		
		BufferedReader buf=null;
		try {
			loadpath();
			System.out.println("输入NFC打币地址秘钥");
			buf= new BufferedReader(new InputStreamReader(System.in));
			String key = buf.readLine();
			while(key==null||key.length()==0) {
				key = buf.readLine();
			}
			System.out.println("步骤二 ： 输入加密公钥");
			String publickey=buf.readLine();
			String afterkey=RSAUtils. encryptedDataOnJava(key,publickey);
				 writeTofile(afterkey);
				 System.out.println("打币地址秘钥加密成功，并写入key文件");
			 
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(buf!=null)
			buf.close();
		}
	}
	/**
	 * 加载配置路径
	 * @return
	 */
	private static  String  loadpath() {
		try {
			Properties properties = new Properties();
			String path=EncryptKey.class.getClassLoader().getResource("").getPath();
			if(path.endsWith("libs/")) {
				path=path.replace("libs/", "config/");
			}
			  BufferedReader bufferedReader = new BufferedReader(new FileReader(path+"application-server.properties"));
			properties.load(bufferedReader);
			keypath=properties.getProperty("transfer.apply.bc.key.path","/home/dpool/dpl_app/keys/");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void writeTofile(String afterkey) throws Exception{
		File file =new File(keypath+"/key.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter wr=new FileWriter(file);
		try {
			wr.write(afterkey);
			wr.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			wr.close();
		}
		
	}

}
