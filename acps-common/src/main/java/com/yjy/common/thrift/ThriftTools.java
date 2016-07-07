package com.yjy.common.thrift;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/7/7.
 */
public class ThriftTools {


    public static void main(String[] args) throws Exception{
        createThriftJava("D:\\thrift-0.9.3.exe", "D:\\work\\code\\acps\\acps-web\\src\\test\\java", "D:\\lwktest2");
    }

    /**
     * 根据idl生产thrift的java文件
     * @param thriftExeFilePath
     * @param thriftRootFilePath
     * @param serviceIdlFileAbsolutePath
     * @throws Exception
     *
     * 如:createThriftJava("D:\\thrift-0.9.3.exe", "D:\\work\\code\\acps\\acps-web\\src\\test\\java", "D:\\lwktest2");
     */
    public static void createThriftJava(String thriftExeFilePath, String thriftRootFilePath, String serviceIdlFileAbsolutePath) throws Exception{
        File file=new File(serviceIdlFileAbsolutePath);
        File[] files = file.listFiles();
        for(File idlFile:files){
            String command = thriftExeFilePath + " -out " + thriftRootFilePath +" --gen java " +idlFile.getAbsolutePath();
            runOsCommand(command);
        }
    }

    public static void runOsCommand(String command) throws Exception{
        Process process = new ProcessBuilder(command.split(" ")).start();
        BufferedReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));
        printResult(process, result);

    }

    private static void printResult(Process process, BufferedReader result) throws Exception {
        System.out.println("打印命令返回值");
        String s ;
        while((s = result.readLine()) != null){
            System.out.println(s) ;
        }
        BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while((s = errors.readLine()) != null){
            System.err.println(s);
        }
    }




}
