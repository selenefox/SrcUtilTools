package com.sevilinma.srcutiltools.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public List<File> searchFileBySuffix(File targetDir, String suffix){
        var dirBuffer = new ArrayList<File>();
        var fileBuffer = new ArrayList<File>();

        if(targetDir != null && targetDir.exists() && targetDir.isDirectory()){
            dirBuffer.add(targetDir);
        }
        while (!dirBuffer.isEmpty()){
            var searchDir = dirBuffer.get(0);
            dirBuffer.remove(0);
            each(searchDir, suffix, dirBuffer,fileBuffer);
        }

        return fileBuffer;
    }

    private void each(File dir, String suffix, List<File> dirBuffer,List<File> outBuffer){
        if(dir.exists() && dir.isDirectory()){
            var allfiles = dir.listFiles();
            if(allfiles!= null && allfiles.length > 0) {
                Arrays.stream(allfiles).forEach(f -> {
                    if (f.isDirectory()) {
                        dirBuffer.add(f);
                    } else if (f.isFile() && f.getAbsolutePath().endsWith(suffix)) {
                        outBuffer.add(f);
                    }
                });
            }
        }
    }

    public void fileReplace(File file, String srcStr, String replaceStr) throws IOException{

        var in = new FileReader(file);
        var bufIn = new BufferedReader(in);
        var tempStream = new CharArrayWriter(); // memory buffer

        String line = null;
        while ( (line = bufIn.readLine()) != null) {
            // Replace the string that matches the condition in each line
            line = line.replaceAll(srcStr, replaceStr);
            // and then write the line to memory buffer
            if(StringUtils.isNoneBlank(line)){
                tempStream.write(line);
                tempStream.append("\r\n");
            }
        }

        bufIn.close();

        // write all lines of memory buffer into the origin file
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.flush();
        out.close();
    }
}
