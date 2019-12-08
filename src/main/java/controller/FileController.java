package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.File;
import service.FileService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    private File file;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/saveFile",
            produces = "text/plan")
    public @ResponseBody
    String callSave(InputStream data) throws HibernateException {
        try {
            file = fileService.save(new ObjectMapper().readValue(data, File.class));
            return "File with id: "
                    + file.getId()
                    + " was saved";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/getFile",
            produces = "text/plan")
    public @ResponseBody
    String callFind(InputStream data) throws HibernateException {
        try {
            file = fileService.findById(new ObjectMapper().readValue(data, File.class).getId());
            return "File with id: "
                    + file.getId()
                    + " was getting";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.PUT,
            value = "/updateFile",
            produces = "text/plan")
    public @ResponseBody
    String callUpdate(InputStream data) throws HibernateException {
        try {
            return "File with id: "
                    + fileService.update(new ObjectMapper().readValue(data, File.class)).getId()
                    + " was updated";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,
            value = "/deleteFile",
            produces = "text/plan")
    public @ResponseBody
    String callDelete(InputStream data) throws HibernateException {
        try {
            fileService.delete(new ObjectMapper().readValue(data, File.class).getId());
            return "File was deleting";
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
