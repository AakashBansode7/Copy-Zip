package com.fedex.shipping1;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
public class Main {



    public static String ReadPropertiesFile(String KeyName) {
        Properties prop = new Properties();
        FileInputStream input = null;
        String KeyValue = null;

        try {
            input = new FileInputStream("E:\\Ashwini\\Aakash\\Copy Zip\\Input.properties");
            prop.load(input);

            KeyValue = prop.getProperty(KeyName);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return KeyValue;
    }



    public static String Path = ReadPropertiesFile("PathToCreateFolder").trim();
    public static String VMs = ReadPropertiesFile("VMsToCopyZip").trim();
    public static String name = ReadPropertiesFile("NameOfTheFolder").trim();
    public static String FolderNameToBeCopied = ReadPropertiesFile("FolderNameToBeCopied").trim();
    public static String path;
    static String[] VMarr=VMs.split(",");

    public static void main(String[] args) throws NoSuchMethodException, IOException {

        final JDialog dialog = new JDialog();
        //Making zip of the folder to be copied
        String[] myFiles = {"E:\\Ashwini\\Aakash\\Copy Zip\\"+FolderNameToBeCopied};
        String zipFile = "E:\\Ashwini\\Aakash\\Copy Zip\\"+FolderNameToBeCopied+".zip";
        ZipFile zipUtil = new ZipFile();
        try {
            zipUtil.zip(myFiles, zipFile);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
        System.out.println("Folder zipped successfully");


        createFolderDir();
        // Copying zipped file in dest path
        copyStreamToFile();
        System.out.println("Folder Copied successfully");

        JOptionPane.showMessageDialog(dialog,"Completed");
        System.exit(0);
    }


    public static void createFolderDir() {

        path = Path + name;

        //Instantiate the File class
        for (int i = 0; i < VMarr.length; i++) {
            File f1 = new File("\\\\" + VMarr[i] + path);
            //Creating a folder using mkdir() method
            boolean bool = f1.mkdir();
            if (bool) {
                System.out.println("Folder is created successfully at"+VMarr[i]);
            } else {
                System.out.println("Error Found!");
            }

        }
    }

    public static void copyStreamToFile() throws IOException {

       for (int i = 0; i < VMarr.length; i++) {
        String oldDir = "E:\\Ashwini\\Aakash\\Copy Zip\\"+FolderNameToBeCopied+".zip";

            String newDir = "\\\\" +VMarr[i]+Path+name+"\\"+FolderNameToBeCopied+".zip";  // name as the destination file name to be done
           File src= new File(oldDir);
           File dest=new File(newDir);
           Files.copy(src.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File has been copied to "+VMarr[i]+" VM");
            System.out.println(i);
        }


    }

   /* private static void unzipfile( String zipFilePath, String destDir ) {
        File dir = new File( destDir ) ;
        // creating an output directory if it doesn't exist already
        if( !dir.exists( ) ) dir.mkdirs( ) ;
        FileInputStream FiS ;
        // buffer to read and write data in the file
        byte[ ] buffer = new byte[ 1024 ] ;
        try {
            FiS = new FileInputStream( zipFilePath ) ;
            ZipInputStream ZiS = new ZipInputStream( FiS ) ;
            ZipEntry ZE = ZiS.getNextEntry( ) ;
            while( ZE != null ) {
                String fileName = ZE.getName( ) ;
                File newFile = new File( destDir + File.separator + fileName ) ;
                System.out.println( " Unzipping to " + newFile.getAbsolutePath( ) ) ;
                // create directories for sub directories in zip
                new File( newFile.getParent( ) ).mkdirs( ) ;
                FileOutputStream FoS = new FileOutputStream( newFile ) ;
                int len ;
                while ( ( len = ZiS.read( buffer ) )  > 0 ) {
                    FoS.write( buffer, 0, len ) ;
                }
                FoS.close( ) ;
                // close this ZipEntry
                ZiS.closeEntry( ) ;
                ZE = ZiS.getNextEntry( ) ;
            }
            // close last ZipEntry
            ZiS.closeEntry( ) ;
            ZiS.close( ) ;
            FiS.close( ) ;
        } catch ( IOException e ) {
            e.printStackTrace( ) ;
        }
    }*/

}