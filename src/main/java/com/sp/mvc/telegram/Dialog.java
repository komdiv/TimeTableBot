/*
package com.sp.mvc.telegram;

import java.util.HashMap;

public class Dialog {
    static int dialogStepAll;
    static private HashMap<String, Dialog> varsANSWerMAP = new HashMap<String, Dialog>();

    public int dialogID=0;
    public String dialogInputString; //Тот диалог, который вызвал этот диалог

    public String dialogOutputString; // Строка, по которой можно найти новый диалог (следующий)
    public String textScreen;
    private String urlPicture;

    private boolean isUrlPicture;
    public String webAppHttp=null;
    public int dialogStep;

    //private Analysis.ObrOut obrOut;
    //public Stories stories;

    public Dialog dialogNext;
    private HashMap<Integer, String> varAnswerMap = new HashMap<Integer, String>();
    public Dialog dialogPrevious;


    public Dialog(String dialogInput, String textScreen) {
        this.dialogStepAll++; //для статистики
        //this.dialogID = 0;
        this.dialogID=this.dialogStepAll;
        this.dialogInputString = dialogInput;
        this.textScreen = textScreen;
        //this.message = message;
    }
//    public Dialog(String textScreen,Message message) {
//        //Это для диалогов внутри диалога
//        this.dialogStepAll++; //для статистики
//        this.dialogID = -1;
//        this.textScreen = textScreen;
//        //this.message = message;
//    }
    public Dialog(String inputString){
        //Это диалог создается до того как юзер нажмет старт
        //И потом тоже он создается. Это становится основой
        this.dialogStepAll++; //для статистики
        //this.dialogID = 0;
        this.dialogID=this.dialogStepAll;
        this.dialogInputString = inputString;
        //if(varsANSWerMAP.containsKey(varAnswerItog)) {throw new DialogExceptoin("Диалог с таким входным параметром("+varAnswerItog+") уже существует.")}
        varsANSWerMAP.put(inputString, this);
    }
    public Dialog(Dialog dialogOld){
        //по сути это копия dialogOld
        this.dialogStepAll++; //для статистики
        this.dialogID=this.dialogStepAll;
        this.dialogInputString = dialogOld.getDialogInputString();
        this.textScreen = dialogOld.getTextScreen();
        this.obrOut = dialogOld.getObrOut();
        this.getVarAnswerMap().putAll(dialogOld.getVarAnswerMap());
        this.setUrlPicture(dialogOld.getUrlPicture());
        if(dialogOld.hasWebAppHttp()){
            this.setWebAppHttp(dialogOld.getWebAppHttp());
        }
    }

    //public Dialog addVarAnswerMap(String varAnswer) throws DialogExceptoin {
    public Dialog addVarAnswerMap(String varAnswer){
        Integer integer = this.varAnswerMap.size();
        this.varAnswerMap.put(integer,varAnswer);
        //String varAnswerItog = this.dialogInputString + "=>" + varAnswer+"!!! ";
        String varAnswerItog = varAnswer;
        Dialog reply = new Dialog(varAnswerItog);
        //if(varsANSWerMAP.containsKey(varAnswerItog)) {throw new DialogExceptoin("Диалог с таким входным параметром("+varAnswerItog+") уже существует.")}
        //if (varsANSWerMAP.containsKey(varAnswerItog)) System.out.println("Заносится дубликат = " + varAnswerItog);
        varsANSWerMAP.put(varAnswerItog, reply);
        return reply;
    }
    //Этот вариант - когда нужно вернуть уже существующий диалог, а не новый
    public Dialog addVarAnswerMap(String varAnswer,Dialog dialog){
        Integer integer = this.varAnswerMap.size();
        this.varAnswerMap.put(integer,varAnswer);
        //String varAnswerItog = this.dialogInputString + "=>" + varAnswer+"!!! ";
        String varAnswerItog = varAnswer;
        Dialog reply = dialog;
        //if(varsANSWerMAP.containsKey(varAnswerItog)) {throw new DialogExceptoin("Диалог с таким входным параметром("+varAnswerItog+") уже существует.")}
        //if (varsANSWerMAP.containsKey(varAnswerItog)) System.out.println("Заносится дубликат = " + varAnswerItog);
        varsANSWerMAP.put(varAnswerItog, reply);
        return reply;
    }


    public Dialog next(){
        return this.dialogNext;
    }
    public boolean hasNext(){
        return (this.dialogNext!= null);
    }
    public void add(String message) {
        //!!!!!! не писалось еще тут ничего
        this.dialogNext = new Dialog("Привет из Dialog.add");
        //this.dialogNext.dialogID = this.dialogID+1;
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "dialogID=" + dialogID +
                ", dialogInputString='" + dialogInputString + '\'' +
                ", textScreen='" + textScreen + '\'' +
                ", Вариантов Ответа='" + varAnswerMap.size() + '\'' +
                '}' +"\n";
    }

    public static Dialog findDialog(String reply){  //По строке ищет в пуле всех диалогов нужный Dialog
        Dialog dialog = Dialog.getVarsANSWerMAP().get(reply);
        //Dialog dialogCopy = new Dialog(dialog.getDialogInputString());
        //Dialog dialogClone = new Dialog(dialog.getDialogInputString());
        //return dialogClone;
        return dialog;
    }

    //-----------------SET--------------------------------------------

    public void setDialogInputString(String dialogInputString) {
        this.dialogInputString = dialogInputString;
        varsANSWerMAP.remove(this.dialogInputString);
        varsANSWerMAP.put(dialogInputString, this);
    }

    public void setTextScreen(String textScreen) {
        this.textScreen = textScreen;
    }

    public boolean hasStories(){
        return this.stories!=null;
    }

    public void setObrOut(Analysis.ObrOut obrOut) {
        this.obrOut = obrOut;
    }
    public void setObrOut() {
        this.obrOut = null;
    }

    //=================SET====================
//----------------GET-----------------------

    public String getTextScreen() {
        return textScreen;
    }

    public static HashMap<String, Dialog> getVarsANSWerMAP() {
        return varsANSWerMAP;
    }

    public HashMap<Integer, String> getVarAnswerMap() {
        return varAnswerMap;
    }

    public Analysis.ObrOut getObrOut() {
        return obrOut;
    }

    public boolean hasObrOut(){
        return this.getObrOut()!=null;
    }

    public String getDialogInputString() {
        return dialogInputString;
    }

    public String getWebAppHttp() {
        return webAppHttp;
    }

    public void setWebAppHttp(String webAppHttp) {
        this.webAppHttp = webAppHttp;
    }

    public boolean hasWebAppHttp(){
        return getWebAppHttp() != null;
    }

    public String getUrlPicture() {
        return this.urlPicture;
    }
    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public boolean isUrlPicture() {
        if(getUrlPicture()==null || getUrlPicture().equals("")) return false;
        return true;
    }


    //================Get=======================

}


 */