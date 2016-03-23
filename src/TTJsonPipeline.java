import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class TTJsonPipeline extends FilePersistentBase implements Pipeline{
	private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * new JsonFilePageModelPipeline with default path "/data/webmagic/"
     */
    public TTJsonPipeline() {
        setPath("/data/webmagic");
    }

    public TTJsonPipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        try {
        	Global.num++;
        	String ID = resultItems.get("ID").toString();
        	
            
        	if(Global.map.get(ID)==null){
        		Global.map.put(ID, 1);
        	}
        	else{
        		int y = (int) Global.map.get(resultItems.get("ID").toString());
        		Global.map.put(ID, y+1);
        	}
        	
        	if(Global.num %100 ==0){
        		File fileD = getFile(path + "total.xls");
                PrintWriter printWriterD = new PrintWriter(new FileWriter(fileD));
        		Iterator<String> it=Global.map.keySet().iterator();
            	while(it.hasNext()){
            		String tID = it.next().toString();
            		printWriterD.println(tID+"\t:\t"+Global.map.get(tID).toString());
            	}
            	printWriterD.close();
            	logger.info("save data!");
        	}
        	
        	File file = getFile(path + ID + ".json");
            PrintWriter printWriter = new PrintWriter(new FileWriter(file,file.exists()));
            printWriter.println(JSON.toJSONString(resultItems.getAll()));
            printWriter.close();
        } catch (IOException e) {
            logger.warn("write file error", e);
        }
    }
}

