package com.example.demo.ui.model.response;

// controllerのメソッドの結果のステータスを格納するクラス
public class OperationStatusModel {
    private String operationResult;
    private String operationName;

    public String getOperationResult() {
        return operationResult;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
