package com.microsoft;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.io.FileInputStream;

public class Main implements WatchingInputStream.ProgressListener {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.upload();
    }

    public void upload() throws Exception
    {
        CloudStorageAccount account = CloudStorageAccount.parse("DefaultEndpointsProtocol=https;AccountName=blobstg8;AccountKey=7IskvJlTj/QSlzNA6IR4wlcuoPR8ZhPBFLymZhyhE52xiOPTRmaCuOoWroLs4C+v3E250kApwy4b3hxsXctseg==;EndpointSuffix=core.windows.net");
        CloudBlobClient client = account.createCloudBlobClient();
        
        CloudBlobContainer container = client.getContainerReference("testblob");
      //  CloudBlockBlob blob = container.getBlockBlobReference("newplot.png");
	   //File sourceFile = new File("C:/Users/v-bayana/Downloads/newplot.png");
        
        CloudBlockBlob blob = container.getBlockBlobReference("dash-sample-apps.rar");
	    File sourceFile = new File("C:/Users/v-bayana/Downloads/dash-sample-apps.rar");
        FileInputStream inputStream = new FileInputStream(sourceFile);
        WatchingInputStream watchingInputStream = new WatchingInputStream(inputStream, this);

        blob.upload(watchingInputStream, sourceFile.length());
    }

    @Override
    public void onAdvance(long at, long length) {
        double percentage = (double)at / (double)length;
        System.out.println(percentage);
    }
}