package com.sevilinma.srcutiltools;

import com.sevilinma.srcutiltools.utils.FileUtils;
import com.sevilinma.srcutiltools.utils.StringUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SrcUtilToolsController implements Initializable,Runnable {
    @FXML
    private Label messageLabel;
    @FXML
    private TextField srcPathText;
    @FXML
    private TextField originText;
    @FXML
    private TextField targetText;
    @FXML
    private ComboBox<String> fileTypeChooseComboBox;
    @FXML
    private Button processButton;
    @FXML
    private ProgressBar progressBar;
    private List<File> processTaskList = new ArrayList<>();
    private String originString;
    private String targetString;

    private final FileUtils fileUtils = new FileUtils();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void onProcessButtonClick() {
        if(StringUtils.isNoneBlank(srcPathText.getText()) &&
                StringUtils.isNoneBlank(originText.getText()) &&
                StringUtils.isNoneBlank(targetText.getText())
        ){
            processTaskList.clear();
            var list = fileUtils.searchFileBySuffix(new File(srcPathText.getText()), fileTypeChooseComboBox.getValue());
            processTaskList.addAll(list);
            messageLabel.setText("0/"+ list.size());
            progressBar.setProgress(0);

            originString = originText.getText();
            targetString = targetText.getText();
            // todo start thread and process file task list
            var thread = new Thread(this);
            thread.start();
            processButton.setDisable(true);
        }else{
            messageLabel.setText("please complete all required fields!");
        }
    }
    @FXML
    protected void onDirChooseButtonClick(){
        var fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Choose your resource directory");
        var re = fileChooser.showDialog(targetText.getScene().getWindow());
        if(re != null && re.isDirectory()){
            srcPathText.setText(re.getAbsolutePath());
        }
    }

    @Override
    public void run() {
        File file;
        for(int i=0;i<processTaskList.size();i++){
            try {
                file = processTaskList.get(i);
                fileUtils.fileReplace(file, originString, targetString);
                setProgressMessage(i+ "/"+ processTaskList.size(), (double) i/processTaskList.size());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Platform.runLater(() -> {
            progressBar.setProgress(1d);
            processButton.setDisable(false);
            messageLabel.setText("Success! file count:"+ processTaskList.size());
        });
    }

    private void setProgressMessage(String msg, double progress){
        Platform.runLater(() -> {
            messageLabel.setText(msg);
            progressBar.setProgress(progress);
        });
    }
}