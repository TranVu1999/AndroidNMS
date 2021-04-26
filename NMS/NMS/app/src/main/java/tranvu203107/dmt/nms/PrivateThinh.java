package tranvu203107.dmt.nms;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PrivateThinh {
    /**
     *

    String DATABASE_NAME="myDB.db";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database=null;

    private void processCopy(){
        try {
            File dbFile=getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                copyDatabaseFromAssets();
                Toast.makeText(MainActivity.this,"Sao chép thành công",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(MainActivity.this,ex.toString(),
                    Toast.LENGTH_LONG).show();
            Log.e("LOI",ex.toString());
        }
    }

    private String getDatabasePath(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }

    private void copyDatabaseFromAssets() {
        try{
            InputStream myInput=getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f=new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream myOutput=new FileOutputStream(outFileName);
            byte []buffer=new byte[1024];
            int length;
            while ((length=myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception ex){
            Log.e("LOI",ex.toString());
        }
    }
     */
}
