
package com.homeclouddrive.controller;


import com.homeclouddrive.domain.Node;
import com.homeclouddrive.domain.User;
import com.jigy.api.Helpful;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/*
 * <pre>
 * Change History: 
 * Date       User              Description 
 * ---------- ----------------  --------------------------------------------------
 *
 * </pre>
 */
@Controller
public class FileBrowserController {  
    
    private Node root;
    private List flatList;
    private static Map<String, String[]> fileTypeMap = new HashMap();
    File driveHome = null;
    String context = null;
    static {
        fileTypeMap.put("other", new String[]{"blue", "fa-file"});
        fileTypeMap.put("xlsx", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xlsm", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xlsb", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xltx", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xltm", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xls", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xlt", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xla", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("xlw", new String[]{"green", "fa-file-excel-o"});
        fileTypeMap.put("pdf", new String[]{"red", "fa-file-pdf-o"});
        fileTypeMap.put("mp3", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("wav", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("aaf", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("aiff", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("flac", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("wma", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("m4a", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("m4p", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("m4b", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("m4c", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("raw", new String[]{"red", "fa-file-sound-o"});
        fileTypeMap.put("doc", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("docx", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("docm", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("dot", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("dotm", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("dotx", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("odt", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("rtf", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("wps", new String[]{"blue", "fa-file-word-o"});
        fileTypeMap.put("gz", new String[]{"gray", "fa-file-zip-o"});
        fileTypeMap.put("zip", new String[]{"gray", "fa-file-zip-o"});
        fileTypeMap.put("7z", new String[]{"gray", "fa-file-zip-o"});
        fileTypeMap.put("jpg", new String[]{"red", "fa-file-image-o"});
        fileTypeMap.put("jpeg", new String[]{"red", "fa-file-image-o"});
        fileTypeMap.put("gif", new String[]{"red", "fa-file-image-o"});
        fileTypeMap.put("png", new String[]{"red", "fa-file-image-o"});
        fileTypeMap.put("bmp", new String[]{"red", "fa-file-image-o"});
        fileTypeMap.put("tiff", new String[]{"red", "fa-file-image-o"});
        fileTypeMap.put("svg", new String[]{"red", "fa-file-image-o"});
        fileTypeMap.put("txt", new String[]{"blue", "fa-file-text"});
        fileTypeMap.put("mkv", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("flv", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("ogg", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("mp4", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("ogv", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("avi", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("mov", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("wmv", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("mpg", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("mpeg", new String[]{"black", "fa-file-movie-o"});
        fileTypeMap.put("ppa", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap.put("ppam", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap.put("pps", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap.put("ppsm", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap.put("ppsx", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap.put("ppt", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap.put("pptx", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap.put("pptm", new String[]{"red", "fa-file-powerpoint-o"});
        fileTypeMap = Collections.unmodifiableMap(fileTypeMap);
    }
    
    
    /**
     * Gets a list of folders
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     */
    @RequestMapping(value = "/getFolders.htm")
    public @ResponseBody Map<String, List<String[]>> getFolders(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, List<String[]>> fileMap = new HashMap();
        List<String[]> folderList = new ArrayList();
        List<String[]> parentList = new ArrayList();
        
        String userHome = System.getProperty("user.home");
        File home = new File(userHome);
        if(home.exists() && home.isDirectory()){
            File parent = home.getParentFile();
            parentList.add(new String[]{parent.getName(), parent.getAbsolutePath()});
            parentList.add(new String[]{home.getName(), home.getAbsolutePath()});
            for(File file : home.listFiles()){
                if(file.isDirectory() && !file.isHidden()){
                    folderList.add(new String[]{file.getName(), file.getAbsolutePath()});
                }
            }
        }
        
        fileMap.put("folderList", folderList);
        fileMap.put("parentFolder", parentList);
        return fileMap;
    }
    
    
    
    /**
     * Recurse into Folder to get it's list of folders
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     */
    @RequestMapping(value = "/diveIntoFolder.htm")
    public @ResponseBody List<String[]> diveIntoFolder(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List<String[]> folderList = new ArrayList();
        String folderPath = Helpful.getRequestParamStrSafe("folderPath", request);
        File folder = new File(folderPath);
        if(folder.exists() && folder.isDirectory()){
            for(File file : folder.listFiles()){
                if(file.isDirectory() && !file.isHidden()){
                    folderList.add(new String[]{file.getName(), file.getAbsolutePath()});
                }
            }
        }
        
        return folderList;
    }
    
    
    
    /**
     * Back out of the current folder to get it's parent's list of folders
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     */
    @RequestMapping(value = "/goBack.htm")
    public @ResponseBody Map<String, List<String[]>> goBack(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, List<String[]>> fileMap = new HashMap();
        List<String[]> folderList = new ArrayList();
        List<String[]> parentList = new ArrayList();
        
        String folderPath = Helpful.getRequestParamStrSafe("folderPath", request);
        File folder = new File(folderPath);
        if(folder.exists() && folder.isDirectory()){
            File parent = folder.getParentFile();
            if(parent.exists() && parent.isDirectory()){
                File parentOfParent = parent.getParentFile();
                if(parentOfParent.exists() && parentOfParent.isDirectory()){
                    parentList.add(new String[]{parentOfParent.getName(), parentOfParent.getAbsolutePath()});
                }
                for(File file : parent.listFiles()){
                    if(file.isDirectory() && !file.isHidden()){
                        folderList.add(new String[]{file.getName(), file.getAbsolutePath()});
                    }
                }
            }
        }
        
        fileMap.put("folderList", folderList);
        fileMap.put("parentFolder", parentList);
        return fileMap;
    }
    
    
    
    /**
     * This method renames a file
     * @param request the request object
     * @param response the response object
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/renameFile.html")
    public void renameFile(HttpServletRequest request, HttpServletResponse response) throws IOException{       
        String path = Helpful.getRequestParamStrSafe("path", request);
        String newPath = Helpful.getRequestParamStrSafe("newPath", request);
        String name = Helpful.getRequestParamStrSafe("name", request);
        String newName = Helpful.getRequestParamStrSafe("newName", request);
        driveHome = new File(Helpful.getProperty(request, "jdbc.properties", "drive.home"));
        context = Helpful.getProperty(request, "jdbc.properties", "context");
        String fileLocation = driveHome + File.separator + convertPath(path, context);
        String newFileLocation = driveHome + File.separator + convertPath(newPath, context);
        
        // change file name
        File file = new File(fileLocation);

        // File (or directory) with new name
        File file2 = new File(newFileLocation);

        if (file2.exists()){
           throw new java.io.IOException("file exists");
        }

        // Rename file (or directory)
        boolean success = file.renameTo(file2);

        if (!success) {
           // File was not successfully renamed
            throw new java.io.IOException("file not renamed successfully");
        }
    }
    
    
    
    /**
     * This method deletes a file or folder
     * @param request the request object
     * @param response the response object
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/deleteFile.html")
    public void deleteFile(HttpServletRequest request, HttpServletResponse response) throws IOException{       
        String path = Helpful.getRequestParamStrSafe("path", request);
        driveHome = new File(Helpful.getProperty(request, "jdbc.properties", "drive.home"));
        context = Helpful.getProperty(request, "jdbc.properties", "context");
        String fileLocation = driveHome + File.separator + convertPath(path, context);
        User user = (User) Helpful.getUser(request);
    }
    
    
    
    protected String convertPath(String path, String context){
        return path.replace(context, "").replace("/resource/", "").replace("/", "\\");
    }
    
    
    
    /**
     * Get the user's entire folder structure
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/getDirs.html")
    public ModelAndView getDirs(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{       
        driveHome = new File(Helpful.getProperty(request, "jdbc.properties", "drive.home"));
        context = Helpful.getProperty(request, "jdbc.properties", "context");
        
        StopWatch watch = new StopWatch();
        watch.start();

        root = createNode(driveHome, null);
        getFirstLevelNodes(driveHome.listFiles(), root);
        
        watch.stop();
        System.out.println("Total execution time only first level nodes: " + watch.getTotalTimeMillis());

        
        return new ModelAndView("dirs", "root", root);
    }
    
    
    /**
     * Get the user's entire folder structure
     * @param request the request object
     * @param response the response object
     * @return the Model And View object
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/getDirsJson.html")
    public @ResponseBody List getDirsJson(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{       
        driveHome = new File(Helpful.getProperty(request, "jdbc.properties", "drive.home"));
        context = Helpful.getProperty(request, "jdbc.properties", "context");
        List list = new ArrayList();
        flatList = new ArrayList();
        
        StopWatch watch = new StopWatch();
        watch.start();

        root = createNode(driveHome, null);
        createTree(driveHome.listFiles(), root);
        
        watch.stop();
        System.out.println("Total execution time getting all nodes: " + watch.getTotalTimeMillis());
        list.add(root);
        list.add(flatList);
        
        return list;
    }
    
    
    
    /**
     * This method converts a directory structure into
     * a tree of nodes where each file and directory represents
     * a node in the tree
     * @param files a list of files within a given node
     * @param parentNode the node that the files sit inside of
     */
    protected void createTree(File[] files, Node parentNode) throws IOException, ParseException{ // the first file sent to this method should be the home directory
        for(File file : files){
            if(file.isHidden() || file.getName().endsWith("_deleted")){
                continue;
            }
            Node child = createNode(file, parentNode);
            if(file.isDirectory()){
                createTree(file.listFiles(), child);
                parentNode.addDirectoryChild(child);
                flatList.add(child);
            } else {
                parentNode.addFileChild(child);
                flatList.add(child);
            }   
        }        
    }
    
    
    
    protected void getFirstLevelNodes(File[] files, Node parentNode) throws IOException, ParseException {
        for (File file : files) {
            if (file.isHidden() || file.getName().endsWith("_deleted")) {
                continue;
            }

            Node child = createNode(file, parentNode);
            if (file.isDirectory()) {
                parentNode.addDirectoryChild(child);
            } else {
                parentNode.addFileChild(child);
            }

        }
    }
    
    
    
    /**
     * This method converts a file into a node
     * @param file the file to convert into a node
     * @param parentNode the parent node of the node being created
     * @return the newly created node
     */
    protected Node createNode(File file, Node parentNode) throws IOException, ParseException{
        // get file attributes
        Path p = Paths.get(file.getAbsolutePath());
        BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
        
        // create node
        Node node = new Node();
        node.setParent(parentNode);
        node.setName(file.getName());
        node.setExtension(getFileExtension(node.getName())); 
        node.setPath(file.getAbsolutePath().replace(driveHome.getAbsolutePath(), context + "/resource").replace("\\", "/"));
        
        
        // set file type and file type icon
        if(file.isDirectory()){
            node.setIsDirectory(true);
            node.setIsFile(false);
            node.setExtensionIcon(new String[]{"white", "fa-folder"});
            node.setNumDirectories(file.listFiles(new DirectoryFileFilter()).length);
            node.setNumFiles(file.listFiles(new RegularFileFilter()).length);
        } else {
            node.setIsDirectory(false);
            node.setIsFile(true);
            node.setExtensionIcon(fileTypeMap.containsKey(node.getExtension()) ? fileTypeMap.get(node.getExtension()) : fileTypeMap.get("other"));
            node.setNumDirectories(0);
            node.setNumFiles(0);
        }
        
        // get creation and modification dates        
        String[] lastModified = cleanDate(view.lastAccessTime().toString());
        String[] dtCreated = cleanDate(view.creationTime().toString());
        node.setLastModified(lastModified[0] + " " + lastModified[1]);
        node.setDtCreated(dtCreated[0] + " " + dtCreated[1]);
        
        return node;
    }
    
    
    protected String[] cleanDate(String date){
        if(Helpful.isEmpty(date)){
            return null;
        }
        
        String[] dates = date.split("\\.")[0].split("T");
        String[] unformmattedDate = dates[0].split("-");
        return new String[]{unformmattedDate[1] + "-" + unformmattedDate[2] + "-" + unformmattedDate[0], dates[1]};
    }

    
    
    
    /**
     * This method returns the extension of a file
     * @param fileName the name of the file (including extension)
     * @return the extension of the file
     */
    private String getFileExtension(String fileName){
        if(fileName.contains(".")){
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
    
    
    public class DirectoryFileFilter implements FileFilter {
        
        @Override
        public boolean accept(File file) {
            return !file.isHidden() && file.isDirectory() && !file.getAbsolutePath().endsWith("_deleted");
        }
    }
    
    
    public class RegularFileFilter implements FileFilter {
        
        @Override
        public boolean accept(File file) {
            return !file.isHidden() && !file.isDirectory() && !file.getAbsolutePath().endsWith("_deleted");
        }
    }
    
}
