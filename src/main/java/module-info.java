module com.sevilinma.srcutiltools {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sevilinma.srcutiltools to javafx.fxml;
    exports com.sevilinma.srcutiltools;
}